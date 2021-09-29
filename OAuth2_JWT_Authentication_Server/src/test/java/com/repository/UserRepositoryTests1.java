package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.User;

@SpringBootTest
public class UserRepositoryTests1 {
	
	@Autowired 
	private UserRepository userRepository;
	
	private User user;
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository dropUsersTable function")
	void a1() {

		assertEquals("Users Table Dropped!", userRepository.dropUsersTable());
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository createUsersTable function")
	void a2() {
		
		assertEquals("Users Table Created!", userRepository.createUsersTable()); 
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository registerUser function")
	void a3() {

		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		user.setEmail("eu1@fa.hu");
		user.setPassword("myPassword");
		user.setActive(false);
		user.setMfa(false);
		
		String authorities = "user";
		
		assertEquals("User Registered", userRepository.registerUser(user, authorities)); 	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository findById function with valid user")
	void a4() {
		
		List<String> authorities = new ArrayList<>();
		authorities.add("user");

		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		user.setEmail("eu1@fa.hu");
		user.setPassword("myPassword");
		user.setActive(false);
		user.setMfa(false);
		user.setAuthorities(authorities);

		assertEquals(user, userRepository.findById("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e")); 		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository findById function with invalid user")
	void a5() {
		
	assertEquals(null, userRepository.findById("bbf7f3f5-3d94-4ca9-9515-aafff26f9c88")); 		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository findByEmail function with valid user")
	void a6() {

		List<String> authorities = new ArrayList<>();
		authorities.add("user");
		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		user.setEmail("eu1@fa.hu");
		user.setPassword("myPassword");
		user.setActive(false);
		user.setMfa(false);
		user.setAuthorities(authorities);
		assertEquals(user, userRepository.findByEmail("eu1@fa.hu")); 
		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository findByEmail function with invalid user")
	void a7() {

		assertEquals(null, userRepository.findByEmail("eu2@fa.hu"));
		
	}

	@Test
	@DisplayName("Testing Authentication_Service UserRepository userExistCheck function with valid user")
	void a8() {

		assertEquals(1, userRepository.userExistCheck("eu1@fa.hu"));
		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository userExistCheck function with invalid user")
	void a9() {

		assertEquals(0, userRepository.userExistCheck("eu2@fa.hu"));
	}	

	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository setActiveUser function with valid user")
	void a10() {
		
		assertEquals("User have been Activated!", userRepository.setActiveUser("eu1@fa.hu")); 	
		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository setActiveUser function with invalid user")
	void a11() {
		
		assertEquals("User have not been Activated!", userRepository.setActiveUser("eu212@fa.hu")); 			
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository updateUser function with valid user")
	void a12() {
		
		List<String> authorities = new ArrayList<>();
		authorities.add("user");
		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		user.setEmail("eu1@fa.hu");
		user.setPassword("myPassword");
		user.setActive(false);
		user.setMfa(false);
		user.setAuthorities(authorities);

		assertEquals("User have been updated!", userRepository.updateUser(user)); 			
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserRepository updateUser function with invalid user")
	void a13() {

		List<String> authorities = new ArrayList<>();
		authorities.add("user");
		user = new User();
		user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8f");
		user.setEmail("eu1@fa.hu");
		user.setPassword("myPassword");
		user.setActive(false);
		user.setMfa(false);
		user.setAuthorities(authorities);
		assertEquals("User not updated!", userRepository.updateUser(user)); 			
	}
}
