package com.repository;

import com.model.User;

public interface UserRepository {

	void createUsersTable();
	
	void dropUsersTable();
	
	User findById(String id);
	
	User findByEmail(String email);
	
	String registerUser(User user, String authorities);
	
	public Integer userExistCheck(String email);
	
	void setActiveUser(String email);
	
	String updateUser(User user);
}
