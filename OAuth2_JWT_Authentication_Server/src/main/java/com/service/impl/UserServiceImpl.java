package com.service.impl;

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
import com.util.Util;

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
	private Util util;

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


	public String createUsersTable() {
		userRepository.createUsersTable();
		return "Users Table Created!";
	}

	public String dropUsersTable() {
		userRepository.dropUsersTable();
		return "Users Table Dropped!";	
	}

	public User findByEmail(String email) {
		
		User repositoryResponse = null;
		
		if(email == "" || email == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.findByEmail --> email cannot be null or empty string!");
			}
		
		repositoryResponse = userRepository.findByEmail(email);
		
		if(repositoryResponse != null) {
			return repositoryResponse;
		}
		
		return null;
	}

	@CircuitBreaker(name = "registerService")
	@Bulkhead(name = "bulkheadregisterService")
	public String registerUser(User user)  {
		
		String userRepositoryResponse = null;
		String accountKeyServiceResponse = null;
		String emailServiceResponse = null;
		
		ScopedSpan newSpan = tracer.startScopedSpan("registerring User");
		
		if(user.getEmail() == "" || user.getEmail() == null || user.getPassword() == "" || user.getPassword() == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.registerUser --> email or password cannot be null or empty string!");
			}

		String uuid = util.UUID_generator();
		user.setId(uuid);
	    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	    user.setActive(false);
	    
	    userRepositoryResponse = userRepository.registerUser(user,"user");
	    
	    accountKeyServiceResponse = accountKeyService.createAccountKey(new AccountKey(user.getId(),"user",user.getEmail()));

		emailServiceResponse = emailService.sendMessageen(user.getEmail(), "User! ", user.getId());		

		newSpan.tag("Authentication-service UserServiceImpl registerUser():", "User regisration");
		newSpan.annotate("User Registered finished");
		newSpan.finish();
	
		if(userRepositoryResponse != null && accountKeyServiceResponse != null && emailServiceResponse != null)  {
						
						log.debug("New User registered "+user.toString());
						return "New User registered!";
						}
		
			return null;
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
		
		AccountKey accountKeyServiceResponse = null;
		String userRepositorySetActiveResponse = null;
		String userRepositoryRemoveKeyResponse = null;
		User userRepositoryFindByEmailyResponse  = null;
		
		ScopedSpan newSpan = tracer.startScopedSpan("userActivation");
	
		if(key == "" || key == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.userActivation --> key cannot be null or empty string!");
			}
		
		boolean userExist = accountKeyService.keyCheck(key);
		String activated = userExist == true ? "userActivated" : "notActivated";
		if(activated == "userActivated") {
			accountKeyServiceResponse = accountKeyService.findAccountKey(key);
			userRepositorySetActiveResponse = userRepository.setActiveUser(accountKeyServiceResponse.getEmail());
			userRepositoryRemoveKeyResponse = accountKeyService.removeKey(key);
		    userRepositoryFindByEmailyResponse = userRepository.findByEmail(accountKeyServiceResponse.getEmail());
			proxyServer.sendNewUserId(userRepositoryFindByEmailyResponse.getId());
					
			newSpan.tag("Authentication-service UserServiceImpl userActivation():", "User userActivation");
			newSpan.annotate("UserActivation finished");
			newSpan.finish();
			log.debug("User with email: "+accountKeyServiceResponse.getEmail() +" "+activated);
			
			if(accountKeyServiceResponse != null &&
				userRepositorySetActiveResponse != null && 
				userRepositoryRemoveKeyResponse != null && 
				userRepositoryFindByEmailyResponse != null)
			return "User Successfully activated!";
		}
		return "User have not been activated!";
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
		
		if(servletRequest.getUsernameHeader() == "" || servletRequest.getUsernameHeader()  == null) {
			throw new RuntimeException(
			"Authentication_Server.UserService.mfaCheck --> header email cannot be null or empty string!");
			}
		
		String email = servletRequest.getUsernameHeader();
		
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
