package com.service.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	 
	public OneTimePasswordServiceImpl(OneTimePasswordRepository oneTimePasswordRepository, UserService userService) {
		this.oneTimePasswordRepository = oneTimePasswordRepository;
		this.userService = userService;
		this.passwordEncoder =  new BCryptPasswordEncoder();
	}

	@Override
	public String createOneTimePasswordTable() {
		oneTimePasswordRepository.createOneTimePasswordTable();
		
		return "OneTimePassword Table Created";
	}

	@Override
	public String dropOneTimePasswordTable() {
		oneTimePasswordRepository.dropOneTimePasswordTable();
		
		return "OneTimePassword Table Deleted";
	}

	@Override
	public String createOneTimePassword(String username, String password) {

		String randomPassword = getRandomNumberString();

		if(username ==  "" || password== "" || username ==  null || password== null ) {
			throw new RuntimeException(
			"Authentication_Server.OneTimePasswordService.createOneTimePassword --> accountkey, email, usertype cannot be null!");
			}

		User user = userService.findByEmail(username);
		Integer oneTimePasswords = oneTimePasswordRepository.OneTimePasswordCheck(username);

		if(oneTimePasswords > 0) {
			oneTimePasswordRepository.removeOneTimePassword(username);
			log.debug("OneTimepassword removed "+username);
		}

		if(user != null && passwordEncoder.matches(password, user.getPassword()) && user.isActive() == true && user.isMfa()) {
			
			OneTimePassword oneTimePassword = new OneTimePassword();
			oneTimePassword.setId(user.getId());
			oneTimePassword.setEmail(username);
			oneTimePassword.setPassword(new BCryptPasswordEncoder().encode(randomPassword));
			oneTimePasswordRepository.createOneTimePassword(oneTimePassword);
			log.debug(randomPassword);
			return "One time password send";
		}	
		 return "Invalid username or password";
	}

	@Override
	public OneTimePassword findOneTimePassword(String email) {
		
		OneTimePassword oneTimePasswordRepositoryFindOTPResponse = null;
		
		if(email ==  "" || email == null) {
			throw new RuntimeException(
			"Authentication_Server.OneTimePasswordService.findOneTimePassword -->  email cannot be null or empty String!");
			}
		
		oneTimePasswordRepositoryFindOTPResponse = oneTimePasswordRepository.findOneTimePassword(email);
		
		if(oneTimePasswordRepositoryFindOTPResponse != null) {
			return oneTimePasswordRepositoryFindOTPResponse;
		}
		 
		return null;
		 
	}

	@Override
	public String removeOneTimePassword(String email) {
		
		String removeOneTimePassword_removeOneTimePassword_response = null;
		
		if(email ==  "" || email == null) {
			throw new RuntimeException(
			"Authentication_Server.OneTimePasswordService.removeOneTimePassword -->  email cannot be null or empty String!");
			}
		removeOneTimePassword_removeOneTimePassword_response = oneTimePasswordRepository.removeOneTimePassword(email);
		
		if(removeOneTimePassword_removeOneTimePassword_response != null ) {
			return removeOneTimePassword_removeOneTimePassword_response;
		}
		
		return "OneTimePassword has not been deleted";
	}

	@Override
	public String getRandomNumberString() {

	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}
}
