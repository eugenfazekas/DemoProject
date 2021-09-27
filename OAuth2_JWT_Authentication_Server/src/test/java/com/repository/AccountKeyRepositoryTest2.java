package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.model.AccountKey;

@SpringBootTest
public class AccountKeyRepositoryTest2 {
	
	@Autowired
	private AccountKeyRepository accountKeyRepository;

	@MockBean
	private JdbcTemplate jdbc;
	
	private AccountKey accountKey;
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository dropAccountKeyTable function when dropping DataAccessException")
	void a1() {
		
		final String  sql = "DROP TABLE IF EXISTS ACCOUNTKEYS";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).execute(sql);

		assertEquals(null, accountKeyRepository.dropAccountKeyTable());	
	}

	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository createAccountKeyTable function when dropping DataAccessException")
	void a2() {
		
		String  sql = "CREATE TABLE IF NOT EXISTS ACCOUNTKEYS (accountKey VARCHAR(36) NOT NULL, accountType VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email));";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).execute(sql);

		assertEquals(null, accountKeyRepository.createAccountKeyTable());
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository createAccountKey function when dropping DataAccessException")
	void a3() {

		accountKey = new AccountKey();
		accountKey.setKey("key1");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		final String sql = "INSERT INTO ACCOUNTKEYS (accountKey,accountType,email) VALUES (?,?,?)";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).update(sql,accountKey.getKey(),accountKey.getAccountType(),accountKey.getEmail());

		assertEquals(null, accountKeyRepository.createAccountKey(accountKey));		
	}

	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository keyCheck function when dropping DataAccessException")
	void a4() {
		
		String  sql = "SELECT COUNT(*) FROM ACCOUNTKEYS WHERE accountKey = ?";
		String  key = "key";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).queryForObject(sql, new Object[] {key}, Integer.class);;
		
		assertEquals(null, accountKeyRepository.keyCheck(key));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository findAccountKey function when dropping DataAccessException")
	void a5() {
			
		RowMapper<AccountKey> mapper = new RowMapper<AccountKey>() {

			public AccountKey mapRow(ResultSet rs ,int rowNum) throws SQLException {
				
				AccountKey n = new AccountKey();
				
				n.setAccountType(rs.getString("accountType"));
				n.setEmail(rs.getString("email"));
				n.setKey(rs.getString("accountKey"));
			
				return n;
			}
		};
		
		String  sql = "SELECT * FROM ACCOUNTKEYS WHERE accountKey = ?";
		String  key = "key3";
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).queryForObject(sql, mapper, key);

		assertEquals(null, accountKeyRepository.findAccountKey(key));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository removeKey function when dropping DataAccessException")
	void a6() {
		
		String  sql = "DELETE FROM ACCOUNTKEYS WHERE accountKey = ?";
		String  key = "key3";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).update(sql,key);
		
		assertEquals(null, accountKeyRepository.removeKey(key));
		}	
}
