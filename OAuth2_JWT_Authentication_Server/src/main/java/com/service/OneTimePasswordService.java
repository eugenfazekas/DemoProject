package com.service;

import com.model.OneTimePassword;

public interface OneTimePasswordService {

	void createOneTimePasswordTable();
	
	void dropOneTimePasswordTable();

	String createOneTimePassword();
	
	//Integer OneTimePasswordCheck(String email);	
	
	OneTimePassword findOneTimePassword(String email);	
	
	void removeOneTimePassword(String email);
}
