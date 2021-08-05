package com.service;

import com.model.AccountKey;

public interface AccountKeyService {
	
	void createAccountKeyTable();
	
	void dropAccountKeyTable();

	void createAccountKey(AccountKey account);
	
	boolean keyCheck(String key);
	
	void removeKey(String key);
	
	AccountKey accountKey(String key);
}
