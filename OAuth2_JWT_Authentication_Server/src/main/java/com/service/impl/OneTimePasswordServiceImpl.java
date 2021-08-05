package com.service.impl;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.model.OneTimePassword;
import com.model.User;
import com.repository.OneTimePasswordRepository;
import com.service.OneTimePasswordService;
import com.service.UserService;


@Service
public class OneTimePasswordServiceImpl implements OneTimePasswordService {

	private OneTimePasswordRepository oneTimePasswordRepository;
	private UserService userService;
	private BCryptPasswordEncoder passwordEncoder;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HttpServletRequest request;
	 
	public OneTimePasswordServiceImpl(OneTimePasswordRepository oneTimePasswordRepository, UserService userService) {
		this.oneTimePasswordRepository = oneTimePasswordRepository;
		this.userService = userService;
		this.passwordEncoder =  new BCryptPasswordEncoder();
	}

	@Override
	public void createOneTimePasswordTable() {
		oneTimePasswordRepository.createOneTimePasswordTable();
		
	}

	@Override
	public void dropOneTimePasswordTable() {
		oneTimePasswordRepository.dropOneTimePasswordTable();
		
	}

	@Override
	public String createOneTimePassword() {
		
		String userName = request.getHeader("username");
		String password = request.getHeader("password");
		String randomPassword = getRandomNumberString();
		
		User user = userService.findByEmail(userName);
		Integer oneTimePasswords = oneTimePasswordRepository.OneTimePasswordCheck(userName);

		if(oneTimePasswords > 0) {
			oneTimePasswordRepository.removeOneTimePassword(userName);
			log.debug("OneTimepassword removed "+userName);
		}
		
		if(user != null && passwordEncoder.matches(password, user.getPassword()) && user.isActive() == true && user.isMfa()) {
			
			OneTimePassword oneTimePassword = new OneTimePassword();
			oneTimePassword.setId(user.getId());
			oneTimePassword.setEmail(userName);
			oneTimePassword.setPassword(new BCryptPasswordEncoder().encode(randomPassword));
			oneTimePasswordRepository.createOneTimePassword(oneTimePassword);
			log.debug(randomPassword);
			return "One time password send";
		}	
		 return "Invalid username or password";
	}

	@Override
	public OneTimePassword findOneTimePassword(String email) {
		return oneTimePasswordRepository.findOneTimePassword(email);
	}

	@Override
	public void removeOneTimePassword(String email) {
		oneTimePasswordRepository.removeOneTimePassword(email);
	}

	private String getRandomNumberString() {

	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}
}
