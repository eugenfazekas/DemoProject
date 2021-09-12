package com.repository;

import com.model.OneTimePassword;

public interface OneTimePasswordRepository {

	String createOneTimePasswordTable();
	
	String dropOneTimePasswordTable();

	OneTimePassword createOneTimePassword(OneTimePassword oneTimePassword);
	
	Integer OneTimePasswordCheck(String email);	
	
	OneTimePassword findOneTimePassword(String email);	
	
	String removeOneTimePassword(String email);
}
