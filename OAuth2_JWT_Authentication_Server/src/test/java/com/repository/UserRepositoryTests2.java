package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import com.model.User;
import com.util.Util_JdbcTemplateUser;

@SpringBootTest
public class UserRepositoryTests2 {
	
	@Autowired 
	private UserRepository userRepository;
	
	@MockBean
	private Util_JdbcTemplateUser jdbc;
	
	private User user;
	

	@Test
	@DisplayName("Testing Authentication_Service UserRepository dropAccountKeyTable function when dropping DataAccessException")
	void a1() {

		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).dropUsersTable();

		assertEquals(null, userRepository.dropUsersTable());	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository createAccountKeyTable function when dropping DataAccessException")
	void a2() {
			
		 doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).createUsersTable();

		assertEquals(null, userRepository.createUsersTable());	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository findById function when dropping DataAccessException")
	void a3() {

		
		user = new User();
		user.setId("a3");

		when(jdbc.findById(user.getId())).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });
		
		assertEquals(null, userRepository.findById(user.getId())); 	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository findByEmail function when dropping DataAccessException")
	void a4() {

		
		user = new User();
		user.setEmail("a4");

		//doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).queryForObject(sql, mapper, user.getEmail());
		when(jdbc.findByEmail(user.getEmail())).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });
		
		assertEquals(null, userRepository.findByEmail(user.getEmail())); 			
	}

	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository registerUser function when dropping DataAccessException")
	void a5() {
		
		List<String> authorities = new ArrayList<>();
		authorities.add("a5");
		user = new User();
		user.setId("a5");
		user.setEmail("a5");
		user.setPassword("a5");
		user.setActive(false);
		user.setMfa(false);
		user.setAuthorities(authorities);
		
		when(jdbc.registerUser(user,"a5")).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });
		
		assertEquals(null, userRepository.registerUser(user, "a5")); 		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository userExistCheck function when dropping DataAccessException")
	void a6() {

		String email = "a6";
		
		when(jdbc.userExistCheck(email)).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }); 		
		assertEquals(null, userRepository.userExistCheck(email));		
	}

	@Test
	@DisplayName("Testing Authentication_Service UserRepository setActiveUser function when dropping DataAccessException")
	void a7() {

		String email = "eu1@fa.hu";
		
		when(jdbc.userExistCheck(email)).thenReturn(1);
		
		when(jdbc.setActiveUser(email)).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });
		
		assertEquals("User have not been Activated!", userRepository.setActiveUser(email));		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository updateUser function when dropping DataAccessException")
	void a8() {
		
		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c88");
		user.setEmail("eu1@fa.hu");
		user.setPassword("a8");
		user.setActive(false);
		user.setMfa(true);
		
		when(jdbc.findById(user.getId())).thenReturn(user);
		
		when(jdbc.updateUser(user)).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });
		
		assertEquals("User not updated!", userRepository.updateUser(user));	
	}		
}
