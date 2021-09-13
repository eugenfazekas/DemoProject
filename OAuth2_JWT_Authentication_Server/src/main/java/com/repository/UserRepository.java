package com.repository;

import com.model.User;

public interface UserRepository {

	String createUsersTable();
	
	String dropUsersTable();
	
	User findById(String id);
	
	User findByEmail(String email);
	
	String registerUser(User user, String authorities);
	
    Integer userExistCheck(String email);
	
	String setActiveUser(String email);
	
	String updateUser(User user);
}
