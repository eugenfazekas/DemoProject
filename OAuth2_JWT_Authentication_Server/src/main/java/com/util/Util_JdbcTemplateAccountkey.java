package com.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.model.AccountKey;

@Component
public class Util_JdbcTemplateAccountkey {

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

	public String createAccountKeyTable() throws DataAccessException {

		jdbc.execute(Util_SqlQueryList.ACCOUNTKEY_CREATE_ACCOUNTKEYS_TABLE);
	
			return "AccountKey table created";
	}
	
	public String dropAccountKeyTable() throws DataAccessException {

		jdbc.execute(Util_SqlQueryList.ACCOUNTKEY_DROP_ACCOUNTKEYS_TABLE);	
	
			return "AccountKey table dropped";
	}

	public Integer createAccountKey(AccountKey account) throws DataAccessException {

		return jdbc.update(Util_SqlQueryList.ACCOUNTKEY_CREATE_ACCOUNT_KEY,account.getKey(),account.getAccountType(),account.getEmail());
	}

	public Integer keyCheck(String key) throws DataAccessException {

		return jdbc.queryForObject(Util_SqlQueryList.ACCOUNTKEY_KEY_CHECK, new Object[] {key}, Integer.class); 
	}
		
	public AccountKey findAccountKey(String key) throws DataAccessException {

		return jdbc.queryForObject(Util_SqlQueryList.ACCOUNTKEY_FIND_ACCOUNT_KEY, mapper, key);
	}

	public Integer removeKey(String key) throws DataAccessException {
		
		return jdbc.update(Util_SqlQueryList.ACCOUNTKEY_REMOVE_KEY,key);
	}	
}
