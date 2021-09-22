package com.service;

import com.model.User;
import com.model.UserUpdate;

public interface UserService {
	
	String createUsersTable();
	
	String dropUsersTable();

	User findByEmail(String email);
	
	String registerUser(User user);
	
	boolean userExistCheck(String email);
	
	String userActivation(String key);
	
	String updateUser(UserUpdate user);
	
	String mfaCheck(String email);
}
