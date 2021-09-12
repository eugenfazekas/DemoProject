package com.service;

import com.model.OneTimePassword;

public interface OneTimePasswordService {

	String createOneTimePasswordTable();
	
	String dropOneTimePasswordTable();

	String createOneTimePassword();
	
	String getRandomNumberString();	
	
	OneTimePassword findOneTimePassword(String email);	
	
	String removeOneTimePassword(String email);
}
