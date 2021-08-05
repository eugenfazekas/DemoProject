package com.repository;

import com.model.AccountKey;

public interface AccountKeyRepository {
	
	void createAccountKeyTable();
	
	void dropAccountKeyTable();

	void createAccountKey(AccountKey account);
	
	Integer keyCheck(String key);
	
	AccountKey accountKey(String key);
	
	void removeKey(String key);
}
