package com.repository.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.model.OneTimePassword;
import com.repository.OneTimePasswordRepository;
import com.util.Util_JdbcTemplateOneTimePassword;

@Repository
public class OneTimePasswordRepositoryImpl implements OneTimePasswordRepository {
	
private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    private Util_JdbcTemplateOneTimePassword jdbcTemplateOneTimePassword;

	public OneTimePasswordRepositoryImpl(Util_JdbcTemplateOneTimePassword jdbcTemplateOneTimePassword) {
		this.jdbcTemplateOneTimePassword = jdbcTemplateOneTimePassword;
	}
	
	@Override
	public String createOneTimePasswordTable() {

		String repositoryResponse = null;
			
		try {
			repositoryResponse = jdbcTemplateOneTimePassword.createOneTimePasswordTable(); 
			log.debug("ONETIMEPASSWORD Table Created");
			
		} catch (DataAccessException e) {
			log.debug("ONETIMEPASSWORD Table has not been created");
		}
			return repositoryResponse;
	}

	@Override
	public String dropOneTimePasswordTable() {
		
		String repositoryResponse = null;
		
		try {
			repositoryResponse = jdbcTemplateOneTimePassword.dropOneTimePasswordTable();	
			log.debug("ONETIMEPASSWORD Table Deleted");	
			
		} catch (DataAccessException e) {
			log.debug("ONETIMEPASSWORD Table has not been deleted");
		}
			return repositoryResponse;
	}

	@Override
	public OneTimePassword createOneTimePassword(OneTimePassword oneTimePassword) {
		
		OneTimePassword repositoryResponse = null;
		
		try {
			repositoryResponse = jdbcTemplateOneTimePassword.createOneTimePassword(oneTimePassword);
			log.debug("New ONETIMEPASSWORD:  " + oneTimePassword.toString());	
			
		} catch (DataAccessException e) {
			log.debug("New ONETIMEPASSWORD has not been created: " + oneTimePassword.toString());
		}
		
		return repositoryResponse;	
	}

	@Override
	public Integer OneTimePasswordCheck(String email) {
	
		Integer repositoryResponse = null;
		
		try {
			repositoryResponse = jdbcTemplateOneTimePassword.OneTimePasswordCheck(email); 
			if(repositoryResponse == 1) {			
				log.debug("ONETIMEPASSWORD Found "+ email); }
		} catch (DataAccessException e)  {
			   log.debug("ONETIMEPASSWORD has not found" +e);
		}		
			return repositoryResponse;
	}

	@Override
	public OneTimePassword findOneTimePassword(String email) {

		OneTimePassword oneTimePassword = null;
		
		try {
			oneTimePassword = jdbcTemplateOneTimePassword.findOneTimePassword(email);
			log.debug("ONETIMEPASSWORD: "+email);
		} catch (DataAccessException e)  {
			log.debug("ONETIMEPASSWORD: "+email+" Has Not Found!");
		}	
			return oneTimePassword;
	}
	
	@Override
	public String removeOneTimePassword(String email) {	
		
		String repositoryResponse = null;
		
		try {
			Integer jdbcResponse = jdbcTemplateOneTimePassword.removeOneTimePassword(email);
			if (jdbcResponse == 1)
				repositoryResponse = "OneTimePassword Deleted";
				log.debug("ONETIMEPASSWORD Deleted: "+email);		
		} catch (DataAccessException e)  {
			log.debug("ONETIMEPASSWORD has not been deleted: "+email);
		}		
			return repositoryResponse;
	}
}
