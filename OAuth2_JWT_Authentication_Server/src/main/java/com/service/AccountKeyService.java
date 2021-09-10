package com.service;

import com.model.AccountKey;

public interface AccountKeyService {
	
	String createAccountKeyTable();
	
	String dropAccountKeyTable();

	String createAccountKey(AccountKey account);
	
	boolean keyCheck(String key);
	
	String removeKey(String key);
	
	AccountKey findAccountKey(String key);
}
