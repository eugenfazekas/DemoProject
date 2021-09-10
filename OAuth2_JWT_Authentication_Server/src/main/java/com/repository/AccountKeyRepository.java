package com.repository;

import com.model.AccountKey;

public interface AccountKeyRepository {
	
	String createAccountKeyTable();
	
	String dropAccountKeyTable();

	String createAccountKey(AccountKey account);
	
	Integer keyCheck(String key);
	
	AccountKey findAccountKey(String key);
	
	String removeKey(String key);
}
