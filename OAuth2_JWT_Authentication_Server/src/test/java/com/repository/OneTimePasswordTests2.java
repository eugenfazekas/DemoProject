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

import com.model.OneTimePassword;

@SpringBootTest
public class OneTimePasswordTests2 {
	
	@Autowired
	private OneTimePasswordRepository oneTimePasswordRepository;
	
	@MockBean
	private JdbcTemplate jdbc;
	
	private OneTimePassword oneTimePassword;
	
	@Test
	@DisplayName("Testing Authentication_Service oneTimePasswordRepository dropOneTimePasswordTable function")
	void a1 () {
	
		final String  sql = "DROP TABLE IF EXISTS ONETIMEPASSWORD";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).execute(sql);

		assertEquals(null, oneTimePasswordRepository.dropOneTimePasswordTable());	
	}

	@Test
	@DisplayName("Testing Authentication_Service oneTimePasswordRepository createOneTimePasswordTable function")
	void a2 () {
		
		final String  sql = "CREATE TABLE IF NOT EXISTS ONETIMEPASSWORD (id VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email), password VARCHAR(64) NOT NULL)";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).execute(sql);

		assertEquals(null, oneTimePasswordRepository.createOneTimePasswordTable());	
	}

	@Test
	@DisplayName("Testing Authentication_Service oneTimePasswordRepository createOneTimePassword function when dropping DataAccessException")
	void a3() {
		
		final String  sql = "INSERT INTO ONETIMEPASSWORD (id,email,password) VALUES (?,?,?)";
			
		oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		oneTimePassword.setEmail("eu1@fa.hu");
		oneTimePassword.setPassword("012345");
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).update(sql,oneTimePassword.getId(),oneTimePassword.getEmail(),oneTimePassword.getPassword());;

		assertEquals(null, oneTimePasswordRepository.createOneTimePassword(oneTimePassword));	
	}

	@Test
	@DisplayName("Testing Authentication_Service oneTimePasswordRepository OneTimePasswordCheck function when dropping DataAccessException")
	void a4() {
		
		String email = "eu2@fa.hu";
		final String  sql = "SELECT COUNT(*) FROM ONETIMEPASSWORD WHERE email = ?";
			
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).queryForObject(sql, new Object[] {email}, Integer.class);
		
		assertEquals(null, oneTimePasswordRepository.OneTimePasswordCheck(email));		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service oneTimePasswordRepository findOneTimePassword function when dropping DataAccessException")
	void a5() {
		
		String email = "eu2@fa.hu";
		final String  sql = "SELECT * FROM ONETIMEPASSWORD WHERE email = ?";
		
		RowMapper<OneTimePassword> mapper = new RowMapper<OneTimePassword>() {

			public OneTimePassword mapRow(ResultSet rs ,int rowNum) throws SQLException {
				
				OneTimePassword n = new OneTimePassword();
				n.setId(rs.getString("id"));
				n.setEmail(rs.getString("email"));
				n.setPassword(rs.getString("password"));
			
				return n;
			}
		};
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).queryForObject(sql, mapper, email);

		assertEquals(null, oneTimePasswordRepository.findOneTimePassword(email));
	}

	@Test
	@DisplayName("Testing Authentication_Service oneTimePasswordRepository removeOneTimePassword function when dropping DataAccessException")
	void a6() {

		String email = "eu2@fa.hu";
		final String  sql = "DELETE FROM ONETIMEPASSWORD WHERE email = ?";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).update(sql,email);
		
		assertEquals(null, oneTimePasswordRepository.removeOneTimePassword(email));		
	}
}
