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
import com.util.Util_ServletRequest;


@Service
public class OneTimePasswordServiceImpl implements OneTimePasswordService {

	private OneTimePasswordRepository oneTimePasswordRepository;
	private UserService userService;
	private BCryptPasswordEncoder passwordEncoder;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Util_ServletRequest util_ServletRequest;
	 
	public OneTimePasswordServiceImpl(OneTimePasswordRepository oneTimePasswordRepository, UserService userService, Util_ServletRequest util_ServletRequest) {
		this.oneTimePasswordRepository = oneTimePasswordRepository;
		this.userService = userService;
		this.passwordEncoder =  new BCryptPasswordEncoder();
		this.util_ServletRequest = util_ServletRequest;
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
	public String createOneTimePassword() {

		String userName = util_ServletRequest.getUsernameHeader();
		String password = util_ServletRequest.getPasswordHeader();
		String randomPassword = getRandomNumberString();

		if(userName ==  "" || password== "" || userName ==  null || password== null ) {
			throw new RuntimeException(
			"Authentication_Server.OneTimePasswordService.createOneTimePassword --> accountkey, email, usertype cannot be null!");
			}

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
