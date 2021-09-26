package com.service;

import org.springframework.web.servlet.view.RedirectView;

import com.model.User;
import com.model.UserUpdate;

public interface UserService {
	
	String createUsersTable();
	
	String dropUsersTable();

	User findByEmail(String email);
	
	String registerUser(User user);
	
	boolean userExistCheck(String email);
	
	RedirectView userActivation(String key);
	
	String updateUser(UserUpdate user);
	
	String mfaCheck(String email);
}
