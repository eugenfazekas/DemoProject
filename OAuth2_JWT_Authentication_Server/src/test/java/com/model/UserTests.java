package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTests {
	
	private User user;
	List<String> authorities = new ArrayList<String>();
	
	@Test
	void getterSetterTest() {
		
		authorities.add("user");
		user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setPassword("password");
		user.setActive(true);
		user.setMfa(true);
		user.setAuthorities(authorities);
		
		assertAll(		
	    		 () -> assertEquals("id", user.getId()),
	    		 () -> assertEquals("eu@fa.hu",user.getEmail()),
	    		 () -> assertEquals("password",user.getPassword()),
	    		 () -> assertEquals(true,user.isActive()),
	    		 () -> assertEquals(true, user.isMfa()),
	    		 () -> assertEquals(authorities.get(0), user.getAuthorities().get(0))
	    		);		
	}	
	
	@Test
	void equals1() {
		
		user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		
		User user2 = new User();
		user2.setId("id");
		user2.setEmail("eu@fa.hu");
		assertEquals(true,user.equals(user2));
	}
	
	@Test
	void equals2() {
		
		user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		
		User user2 = new User();
		user2.setId("id2");
		user2.setEmail("eu@fa.hu");
		assertEquals(false,user.equals(user2));
	}

	@Test
	void toStringTest() {

		authorities.add("user");
		user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setPassword("password");
		user.setActive(true);
		user.setMfa(true);
		user.setAuthorities(authorities);
		
		assertEquals("User [id=" + user.getId() + ", email=" + user.getEmail() + ", password=" + user.getPassword() + ", active=" + user.isActive() + ", mfa=" + user.isMfa()
				+ ", authorities=" + user.getAuthorities() + "]", user.toString());
	}
}
