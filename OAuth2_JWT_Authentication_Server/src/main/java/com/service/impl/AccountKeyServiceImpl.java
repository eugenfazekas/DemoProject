package com.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	public void createAccountKeyTable() {	
		accountKeyRepository.createAccountKeyTable();
	}

	@Override
	public void dropAccountKeyTable() {		
		accountKeyRepository.dropAccountKeyTable();
	}

	@Override
	public void createAccountKey(AccountKey account) {
		accountKeyRepository.createAccountKey(account);
	}

	@Override
	public boolean keyCheck(String key) {
	
		boolean response = accountKeyRepository.keyCheck(key)  > 0 ? true : false;
		log.debug("AccountKeyCheck "+ key+ " keyExist = " + response);
		
		return response;
	}

	@Override
	public void removeKey(String key) {
		accountKeyRepository.removeKey(key);
	}

	@Override
	public AccountKey accountKey(String key) {
		return accountKeyRepository.accountKey(key);
	}

}
