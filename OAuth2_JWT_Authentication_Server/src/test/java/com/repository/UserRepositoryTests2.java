package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.model.User;

@SpringBootTest
public class UserRepositoryTests2 {
	
	@Autowired 
	private UserRepository userRepository;
	
	@MockBean
	private JdbcTemplate jdbc;
	
	private User user;
	

	@Test
	@DisplayName("Testing Authentication_Service UserRepository dropAccountKeyTable function when dropping DataAccessException")
	void a1() {

		final String  sql = "DROP TABLE IF EXISTS USERS";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).execute(sql);

		assertEquals(null, userRepository.dropUsersTable());	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository createAccountKeyTable function when dropping DataAccessException")
	void a2() {
		
		final String  sql = "CREATE TABLE IF NOT EXISTS USERS (id VARCHAR(36) PRIMARY KEY, email VARCHAR(64) NOT NULL, UNIQUE  (email), password VARCHAR(64) NOT NULL, active BOOLEAN, mfa BOOLEAN, authorities VARCHAR(64) NOT NULL);";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).execute(sql);

		assertEquals(null, userRepository.createUsersTable());	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository findById function when dropping DataAccessException")
	void a3() {

		final String  sql = "SELECT * FROM users WHERE id = ?";
		
		RowMapper<User> mapper = new RowMapper<User>() {

			public User mapRow(ResultSet rs ,int rowNum) throws SQLException {
				
				User n = new User();
				
				n.setId(rs.getString("id"));
				n.setEmail(rs.getString("email"));
				n.setPassword(rs.getString("password"));
				n.setActive(rs.getBoolean("active"));
				n.setMfa(rs.getBoolean("mfa"));
				n.setAuthorities(Arrays.asList(rs.getString("authorities").split(" ")));
						
				return n;
			}
		};
		
		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");

		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).queryForObject(sql, mapper, user.getId());
		
		assertEquals(null, userRepository.findById(user.getId())); 	
	}
	
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository registerUser function when dropping DataAccessException")
	void a5() {

		final String  sql = "INSERT INTO USERS (id,email,password,active,mfa,authorities) VALUES (?,?,?,?,?,?)";
		
		List<String> authorities = new ArrayList<>();
		authorities.add("user");
		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		user.setEmail("eu1@fa.hu");
		user.setPassword("myPassword");
		user.setActive(false);
		user.setMfa(false);
		user.setAuthorities(authorities);
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).update(sql,user.getId(),user.getEmail(),user.getPassword(),user.isActive(),user.isMfa(),"user");
		
		assertEquals(null, userRepository.registerUser(user, "user")); 		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository userExistCheck function when dropping DataAccessException")
	void a6() {
		
		final String  sql = "SELECT COUNT(*)  FROM USERS WHERE email = ?";
		String email = "eu2@fa.hu";
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).queryForObject(sql, new Object[] {email}, Integer.class); 

		assertEquals(null, userRepository.userExistCheck(email));		
	}


	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository updateUser function when dropping DataAccessException")
	void a8() {
		
		final String  sql ="UPDATE users SET email = ?, password = ?, mfa = ?  where id = ?";
		
		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		user.setEmail("eu1@fa.hu");
		user.setPassword("myPassword");
		user.setActive(false);
		user.setMfa(false);
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).update(sql, user.getEmail(), user.getPassword(), user.isMfa(), user.getId() );
		assertEquals("User not updated!", userRepository.updateUser(user));
	}	
	
}
