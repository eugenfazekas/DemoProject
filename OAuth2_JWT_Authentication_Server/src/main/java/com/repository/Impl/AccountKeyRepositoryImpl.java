package com.repository.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void createAccountKeyTable() {

		final String  sql = "CREATE TABLE IF NOT EXISTS ACCOUNTKEYS (accountKey VARCHAR(36) NOT NULL, accountType VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email));";
		jdbc.execute(sql);
		log.debug("ACCOUNTKEYS Table Created");
	}
	
	@Override
	public void dropAccountKeyTable() {
		
		final String  sql = "DROP TABLE IF EXISTS ACCOUNTKEYS";
		jdbc.execute(sql);	
		log.debug("ACCOUNTKEYS Table Deleted");
		
	}

	@Override
	public void createAccountKey(AccountKey account) {
		
		final String sql = "INSERT INTO ACCOUNTKEYS (accountKey,accountType,email) VALUES (?,?,?)";
		jdbc.update(sql,account.getKey(),account.getAccountType(),account.getEmail());
		log.debug("New ACCOUNTKEY:  "+account.toString());

	}

	@Override
	public Integer keyCheck(String key) {
		
		final String  sql = "SELECT COUNT(*) FROM ACCOUNTKEYS WHERE accountKey = ?";
		int keysFound = jdbc.queryForObject(sql, new Object[] {key}, Integer.class); 
		if(keysFound > 0) { log.debug("AccountKey Found "+ key); }
		return keysFound;
	}
	
	@Override
	public AccountKey accountKey(String key) {
		
		AccountKey accountKey = null;
		final String  sql ="SELECT * FROM ACCOUNTKEYS WHERE accountKey = ?";
		try {
			accountKey = jdbc.queryForObject(sql, mapper, key);
			log.debug("AccountKey: "+key);
		}catch(EmptyResultDataAccessException e) {
			log.debug("AccountKey: "+key+" Not Fonud!");
			}
		return accountKey;
		}

	@Override
	public void removeKey(String key) {
		final String sql = "DELETE FROM ACCOUNTKEYS WHERE accountKey = ? ";
		jdbc.update(sql,key);
		log.debug("AccountKey Deleted: "+key);
	}
}

