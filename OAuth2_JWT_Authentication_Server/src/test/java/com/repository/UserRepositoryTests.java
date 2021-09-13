package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.User;

@SpringBootTest
public class UserRepositoryTests {
	
	@Autowired 
	private UserRepository userRepository;
	
	private User user;
	
	@Test
	void a1() {

		assertEquals("Users Table Dropped!", userRepository.dropUsersTable());
	}
	
	@Test
	void a2() {
		
		System.out.println("test 2");
		assertEquals("Users Table Created!", userRepository.createUsersTable()); 
	}
	
	@Test
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
	void a5() {

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
	void a6() {

		assertEquals(1, userRepository.userExistCheck("eu1@fa.hu"));
		
	}
	
	@Test
	void a7() {

		assertEquals(0, userRepository.userExistCheck("eu2@fa.hu"));
	}	

	
	@Test
	void a8() {
		
		assertEquals("User have been Activated!", userRepository.setActiveUser("eu1@fa.hu")); 	
		
	}
	
	@Test
	void a9() {
		
		assertEquals("User have not been Activated!", userRepository.setActiveUser("eu212@fa.hu")); 			
	}
	
	@Test
	void a10() {
		
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
	void a11() {

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
