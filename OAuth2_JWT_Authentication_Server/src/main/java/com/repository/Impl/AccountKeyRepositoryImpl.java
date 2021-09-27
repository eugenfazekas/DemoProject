package com.repository.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.AccountKey;
import com.repository.AccountKeyRepository;


@Repository
public class AccountKeyRepositoryImpl  implements AccountKeyRepository{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JdbcTemplate jdbc;
			
	final RowMapper<AccountKey> mapper = new RowMapper<AccountKey>() {

		public AccountKey mapRow(ResultSet rs ,int rowNum) throws SQLException {
			
			AccountKey n = new AccountKey();
			
			n.setAccountType(rs.getString("accountType"));
			n.setEmail(rs.getString("email"));
			n.setKey(rs.getString("accountKey"));
		
			return n;
		}
	};
	
	@Override
	public String createAccountKeyTable() {

		String repositoryResponse = null;
		final String  sql = "CREATE TABLE IF NOT EXISTS ACCOUNTKEYS (accountKey VARCHAR(36) NOT NULL, accountType VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email));";
		
		try {
			jdbc.execute(sql);
			repositoryResponse = "AccountKey table created";
			log.debug("ACCOUNTKEYS Table Created");
		
		} catch (DataAccessException e)  {
			log.debug("ACCOUNTKEYS Table has not been created " +e);
		}
		
		return repositoryResponse ;
	}
	
	@Override
	public String dropAccountKeyTable() {
		
		String repositoryResponse = null;
		final String  sql = "DROP TABLE IF EXISTS ACCOUNTKEYS";
		
		try {
			jdbc.execute(sql);	
			repositoryResponse = "AccountKey table dropped";
			log.debug("ACCOUNTKEYS Table Deleted");
		
		}  catch (DataAccessException e)  {
			log.debug("ACCOUNTKEYS Table has not been dropped " +e);
		}
		
		return repositoryResponse;
	}

	@Override
	public String createAccountKey(AccountKey account) {
		
		String repositoryResponse = null;
		final String sql = "INSERT INTO ACCOUNTKEYS (accountKey,accountType,email) VALUES (?,?,?)";
		
		try {
			jdbc.update(sql,account.getKey(),account.getAccountType(),account.getEmail());
			repositoryResponse = "New AccountKey Created";
			log.debug("New ACCOUNTKEY:  "+account.toString());
		
		}  catch (DataAccessException e)  {
		   log.debug("New AccountKey has not been created " +e);
		}		
			return repositoryResponse;
	}

	@Override
	public Integer keyCheck(String key) {
		
		Integer repositoryResponse = null;
		final String  sql = "SELECT COUNT(*) FROM ACCOUNTKEYS WHERE accountKey = ?";
		
		try {
			 repositoryResponse = jdbc.queryForObject(sql, new Object[] {key}, Integer.class); 
			if(repositoryResponse > 0) { log.debug("AccountKey Found "+ key); }
		
		} catch (DataAccessException e)  {
			   log.debug("AccountKey not found " +e);
		}	
			return repositoryResponse;
	}
	
	@Override
	public AccountKey findAccountKey(String key) {
		
		AccountKey accountKey = null;
		final String  sql ="SELECT * FROM ACCOUNTKEYS WHERE accountKey = ?";
		
		try {
			accountKey = jdbc.queryForObject(sql, mapper, key);
			log.debug("AccountKey: "+key);
			
		} catch (DataAccessException e)  {
			   log.debug("New AccountKey has not been created " +e);
		}	
		
		return accountKey;
	}

	@Override
	public String removeKey(String key) {
		
		String repositoryResponse = null;
		final String sql = "DELETE FROM ACCOUNTKEYS WHERE accountKey = ?";
		
		try {
			jdbc.update(sql,key);
			repositoryResponse = "New AccountKey Deleted";
			log.debug("AccountKey Deleted: "+key);
			
		} catch (DataAccessException e)  {
			   log.debug("New AccountKey has not been created " +e);
			} 
		
		return repositoryResponse;
	}
}

