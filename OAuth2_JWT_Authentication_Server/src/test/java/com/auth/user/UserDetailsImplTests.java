package com.auth.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.User;

@SpringBootTest
public class UserDetailsImplTests {
	
	private UserDetailsImpl userDetailsImpl1;
	private UserDetailsImpl userDetailsImpl2;
	
	@Test
	void UserDetailsImplTest() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setPassword("pass");
		user.setActive(true);
		user.setMfa(false);
		user.setAuthorities(authorities);
		
		userDetailsImpl1 = new UserDetailsImpl(user);
		
		assertAll(		
	    		 () -> assertEquals("eu@fa.hu", userDetailsImpl1.getUsername()),
	    		 () -> assertEquals("pass", userDetailsImpl1.getPassword()),
	    		 () -> assertEquals(true, userDetailsImpl1.isAccountNonExpired()),
	    		 () -> assertEquals(true, userDetailsImpl1.isAccountNonLocked()),
	    		 () -> assertEquals(true, userDetailsImpl1.isCredentialsNonExpired()),
	    		 () -> assertEquals(true, userDetailsImpl1.isEnabled()),
	    		 () -> assertEquals(false, userDetailsImpl1.isMfa()),
	    		 () -> assertEquals("id", userDetailsImpl1.getId())	    		 
	    		);
	}
	
	@Test
	void equalsTest1() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setPassword("pass");

		user.setAuthorities(authorities);
		
		userDetailsImpl1 = new UserDetailsImpl(user);
		
		User user2 = new User();
		user2.setId("id");
		user2.setEmail("eu@fa.hu");
		user2.setPassword("pass");

		user2.setAuthorities(authorities);
		
		userDetailsImpl2 = new UserDetailsImpl(user2);
				
		assertEquals(true, userDetailsImpl1.equals(userDetailsImpl2));
		
	}

}
