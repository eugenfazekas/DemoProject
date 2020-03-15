package com.demoproject.service;

import java.util.Locale;

import com.demoproject.entity.User;

public interface UserService {

	public User findByEmail(String email);
	
	public User findByActivation(String activation);
	
	public String registerUser(User user,Locale locale);
	
	public String userActivation(String code);
	
	public String adminInit();
	
	public String registerAdmin(User adminToregister);
	
}
