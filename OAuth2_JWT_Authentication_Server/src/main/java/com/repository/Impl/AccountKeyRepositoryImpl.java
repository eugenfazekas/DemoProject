package com.repository.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.model.AccountKey;
import com.repository.AccountKeyRepository;
import com.util.Util_JdbcTemplateAccountkey;


@Repository
public class AccountKeyRepositoryImpl  implements AccountKeyRepository{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    private Util_JdbcTemplateAccountkey jdbcTemplateAccountkey;
			
	public AccountKeyRepositoryImpl(Util_JdbcTemplateAccountkey jdbcTemplateAccountkey) {
		this.jdbcTemplateAccountkey = jdbcTemplateAccountkey;
	}
	
	@Override
	public String createAccountKeyTable() {

		String repositoryResponse = null;
			
		try {
			repositoryResponse = jdbcTemplateAccountkey.createAccountKeyTable();
			log.debug("ACCOUNTKEYS Table Created");
		
		} catch (DataAccessException e)  {
			log.debug("ACCOUNTKEYS Table has not been created " +e);
		}
		
		return repositoryResponse ;
	}
	
	@Override
	public String dropAccountKeyTable() {
		
		String repositoryResponse = null;
			
		try {
			repositoryResponse = jdbcTemplateAccountkey.dropAccountKeyTable();	
			log.debug("ACCOUNTKEYS Table Deleted");
		
		}  catch (DataAccessException e)  {
			log.debug("ACCOUNTKEYS Table has not been dropped " +e);
		}
		
		return repositoryResponse;
	}

	@Override
	public String createAccountKey(AccountKey account) {
		
		String repositoryResponse = null;
		
		try {
			Integer jdbcResponse = jdbcTemplateAccountkey.createAccountKey(account);
			if( jdbcResponse == 1 ) {
				repositoryResponse = "New AccountKey Created";
				log.debug("New ACCOUNTKEY:  "+account.toString());
			}
		}  catch (DataAccessException e)  {
		   log.debug("New AccountKey has not been created " +e);
		}		
			return repositoryResponse;
	}

	@Override
	public Integer keyCheck(String key) {
		
		Integer repositoryResponse = null;
		
		try {
			repositoryResponse =  jdbcTemplateAccountkey.keyCheck(key); 
			if(repositoryResponse == 1) { log.debug("AccountKey Found "+ key); }
		} catch (DataAccessException e)  {
			   log.debug("AccountKey not found " +e);
		}	
			return repositoryResponse;
	}
	
	@Override
	public AccountKey findAccountKey(String key) {
		
		AccountKey accountKey = null;
		
		try {
			accountKey = jdbcTemplateAccountkey.findAccountKey(key);
			log.debug("AccountKey: "+key);
			
		} catch (DataAccessException e)  {
			   log.debug("New AccountKey has not been created " +e);
		}			
			return accountKey;
	}

	@Override
	public String removeKey(String key) {
		
		String repositoryResponse = null;	
		
		try {
			Integer jdbcResponse = jdbcTemplateAccountkey.removeKey(key);
			if( jdbcResponse == 1 ) {
				repositoryResponse = "AccountKey Deleted";
				log.debug("AccountKey Deleted: "+key);
			}
		} catch (DataAccessException e)  {
			   log.debug("New AccountKey has not been created " +e);
			} 
		
		return repositoryResponse;
	}
}

