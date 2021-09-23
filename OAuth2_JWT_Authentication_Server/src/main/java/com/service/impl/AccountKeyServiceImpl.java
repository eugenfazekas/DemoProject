package com.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exception.CreateAccountKeyException;
import com.exception.NoUserActivationKeyException;
import com.model.AccountKey;
import com.repository.AccountKeyRepository;
import com.service.AccountKeyService;

@Service
public class AccountKeyServiceImpl implements AccountKeyService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private AccountKeyRepository accountKeyRepository;

	public AccountKeyServiceImpl(AccountKeyRepository accountKeyRepository) {
		this.accountKeyRepository = accountKeyRepository;
	}

	@Override
	public String createAccountKeyTable() {	
		accountKeyRepository.createAccountKeyTable();
		return "AccountKey table created";
	}

	@Override
	public String dropAccountKeyTable() {		
		accountKeyRepository.dropAccountKeyTable();
		return "AccountKey table dropped";
	}

	@Override
	public String createAccountKey(AccountKey account) {
		
		String accountKeyRepositoryResponse = null;
		
		if(account.getAccountType() ==  null || account.getKey() == null || account.getEmail() == null) {
			throw new CreateAccountKeyException(
			"Authentication_Server.AccountKeyService.createAccountKey --> accountkey, email, usertype cannot be null!");
			}
				
		accountKeyRepositoryResponse = accountKeyRepository.createAccountKey(account);
		
		if(accountKeyRepositoryResponse != null) {
			return "New AccountKey Created";
		}
		
		return "AccountKey has not been created";
	}

	@Override
	public boolean keyCheck(String key) {
	
		if(key == null || key == "") {
			throw new NoUserActivationKeyException(
			"Authentication_Server.AccountKeyService.keyCheck --> key cannot be null or empty String!");
			}
		
		boolean response = accountKeyRepository.keyCheck(key)  > 0 ? true : false;
		log.debug("AccountKeyCheck "+ key+ " keyExist = " + response);
		
		return response;
	}

	@Override
	public String removeKey(String key) {
		
		String accountKeyRepositoryResponse = null;
		
		if(key == null || key == "") {
			throw new NoUserActivationKeyException(
			"Authentication_Server.AccountKeyService.removeKey --> key cannot be null or empty String!");
			}
		
		accountKeyRepositoryResponse = accountKeyRepository.removeKey(key);
		
		if(accountKeyRepositoryResponse != null) {
			return "AccountKey Successfully removed";
		}
			
		return "AccountKey has not been removed";
	}

	@Override
	public AccountKey findAccountKey(String key) {
		
		AccountKey accountKeyRepositoryResponse = null;
		
		if(key == null || key == "") {
			throw new NoUserActivationKeyException(
			"Authentication_Server.AccountKeyService.findAccountKey --> key cannot be null or empty String!");
			}
		
		accountKeyRepositoryResponse = accountKeyRepository.findAccountKey(key);
		
		if(accountKeyRepositoryResponse != null ) {
			return accountKeyRepositoryResponse;
		}
		
		return null;
	}

}
