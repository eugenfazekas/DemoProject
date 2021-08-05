package com.service;

import com.model.User;
import com.model.UserUpdate;

public interface UserService {
	
	void createUsersTable();
	
	void dropUsersTable();

	User findById(String id);
	
	User findByEmail(String email);
	
	String registerUser(User user);
	
	boolean userExistCheck(String email);
	
	void userActivation(String key);
	
	String updateUser(UserUpdate user);
	
	String mfaCheck();
}
