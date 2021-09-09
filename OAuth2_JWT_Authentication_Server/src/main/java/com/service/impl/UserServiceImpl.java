package com.service.impl;

import java.io.IOException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.events.source.SimpleSourceBean;
import com.model.AccountKey;
import com.model.User;
import com.model.UserUpdate;
import com.repository.UserRepository;
import com.service.AccountKeyService;
import com.service.UserService;
import com.util.EmailService;
import com.util.ProxyServer;
import com.util.ServletRequest;

import brave.ScopedSpan;
import brave.Tracer;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserServiceImpl implements UserService{
	
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private AccountKeyService accountKeyService;
	private UserRepository userRepository;
	private EmailService emailService;
	private ProxyServer proxyServer;
	private SimpleSourceBean simpleSourceBean;
	private ServletRequest servletRequest;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	Tracer tracer;

	public UserServiceImpl(AccountKeyService accountKeyService, UserRepository userRepository, EmailService emailService,
			ProxyServer proxyServer, SimpleSourceBean simpleSourceBean, ServletRequest servletRequest ) {
		this.accountKeyService = accountKeyService;
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.proxyServer = proxyServer;
		this.simpleSourceBean = simpleSourceBean;
		this.servletRequest = servletRequest; 
	}


	public void createUsersTable() {
		userRepository.createUsersTable();
	}

	public void dropUsersTable() {
		userRepository.dropUsersTable();
		
	}

	public User findByEmail(String email) {
		
		if(email == "" || email == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.findByEmail --> email cannot be null or empty string!");
			}
		
		return userRepository.findByEmail(email);
	}

	@CircuitBreaker(name = "registerService")
	@Bulkhead(name = "bulkheadregisterService")
	public String registerUser(User user)  {
		
		ScopedSpan newSpan = tracer.startScopedSpan("registerring User");
		
		if(user.getEmail() == "" || user.getEmail() == null || user.getPassword() == "" || user.getPassword() == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.registerUser --> email or password cannot be null or empty string!");
			}
		
		UUID uuid = UUID.randomUUID();	
		user.setId(uuid.toString());
	    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	    user.setActive(false);
		userRepository.registerUser(user,"user");
		accountKeyService.createAccountKey(new AccountKey(uuid.toString(),"user",user.getEmail()));
		
		try {
			emailService.sendMessageen(user.getEmail(), "User! ", uuid.toString());
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			newSpan.tag("Authentication-service UserServiceImpl registerUser():", "User regisration");
			newSpan.annotate("User Registered finished");
			newSpan.finish();
		}
		
		log.debug("New User registered "+user.toString());
		
		return "New User registered!";
	}
	
	@Override
	public boolean userExistCheck(String email) {
		
		ScopedSpan newSpan = tracer.startScopedSpan("userExistCheck");
		
		if(email == "" || email == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.userExistCheck --> email cannot be null or empty string!");
			}
		
		boolean response = userRepository.userExistCheck(email)  > 0 ? true : false;
		
		newSpan.tag("Authentication-service UserServiceImpl userExistCheck():", "User userExistCheck");
		newSpan.annotate("UserExistCheck finished");
		newSpan.finish();
		log.debug("UserUserExistCheck "+ email+ " exist = " + response);
		
			return response;
	}

	@Override
	public String userActivation(String key) {
		
		ScopedSpan newSpan = tracer.startScopedSpan("userActivation");
	
		if(key == "" || key == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.userActivation --> key cannot be null or empty string!");
			}
		
		boolean userExist = accountKeyService.keyCheck(key);
		String activated = userExist == true ? "userActivated" : "notActivated";
		if(activated == "userActivated") {
			AccountKey account = accountKeyService.accountKey(key);
			userRepository.setActiveUser(account.getEmail());
			accountKeyService.removeKey(key);
			proxyServer.sendNewUserId(userRepository.findByEmail(account.getEmail()).getId());
			
			newSpan.tag("Authentication-service UserServiceImpl userActivation():", "User userActivation");
			newSpan.annotate("UserActivation finished");
			newSpan.finish();
			log.debug("User with email: "+account.getEmail() +" "+activated);
		}
			return "User Successfully activated!";
	}

	@Override
	public String updateUser(UserUpdate userUpdate) {
		
		ScopedSpan newSpan = tracer.startScopedSpan("updateUser");
		
		if(userUpdate.getId() == "" || userUpdate.getId()  == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.updateUser --> updateUser.id cannot be null or empty string!");
			}
		
		User user = userRepository.findById(userUpdate.getId());
		if(passwordEncoder.matches(userUpdate.getOldPassword(), user.getPassword())) {
			if(userUpdate.getPassword() != null && userUpdate.getPassword() != "") {
				user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
			}
			if(userUpdate.getEmail() != null && userUpdate.getEmail() != "") {
				user.setEmail(userUpdate.getEmail());
			}
			user.setMfa(userUpdate.isMfa());
			return userRepository.updateUser(user);
		}
		
		newSpan.tag("Authentication-service UserServiceImpl updateUser():", "User updateUser");
		newSpan.annotate("UpdateUser finished");
		newSpan.finish();
		return "Update have not been executed!";
	}

	@Override
	public String mfaCheck() {
		
		ScopedSpan newSpan = tracer.startScopedSpan("mfaCheck");
		
		if(servletRequest.getEmailHeader() == "" || servletRequest.getEmailHeader()  == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.mfaCheck --> header email cannot be null or empty string!");
			}
		
		String email = servletRequest.getEmailHeader();
		
		User user =	null;
		user = userRepository.findByEmail(email);	
		
		if(user != null && user.isMfa()) {
			simpleSourceBean.publisUserAuthenticationId(user.getId());
			log.debug("UserServiceImpl mfa(): Authentication started for user " + user.getId() );
			return "true";
		}
		
		else if(user != null && !user.isMfa()) {
			simpleSourceBean.publisUserAuthenticationId(user.getId());
			
			newSpan.tag("Authentication-service UserServiceImpl mfaCheck():", "User mfaCheck");
			newSpan.annotate("mfaCheck check finished");
			newSpan.finish();
			log.debug("UserServiceImpl mfa(): Authentication started for user " + user.getId() );
			return "false";
		}	
		
		return 	"User Not Exist!";
	}
}
