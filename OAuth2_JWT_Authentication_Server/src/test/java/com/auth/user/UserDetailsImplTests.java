package com.auth.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.DummyTestModel;
import com.model.User;

@SpringBootTest
public class UserDetailsImplTests {
	
	private UserDetailsImpl userDetailsImpl1;
	private UserDetailsImpl userDetailsImpl2;
	private User user1;
	private User user2;
	private DummyTestModel dummyTestModel;
	
	@Test
	void UserDetailsImplTest() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
	    user1 = new User();
	    user1.setId("id");
	    user1.setEmail("eu@fa.hu");
	    user1.setPassword("pass");
	    user1.setActive(true);
	    user1.setMfa(false);
	    user1.setAuthorities(authorities);
		
		userDetailsImpl1 = new UserDetailsImpl(user1);
		
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
		
		User user = new User();
		user.setId("id");
		
		userDetailsImpl1 = new UserDetailsImpl(user);
		
		userDetailsImpl2 = new UserDetailsImpl();
		userDetailsImpl2 = userDetailsImpl1;
		
		assertEquals(true, userDetailsImpl1.equals(userDetailsImpl2));
	}
	
	@Test
	void equalsTest2() {
		
		user1 = new User();
		user1.setId("id");

		userDetailsImpl1 = new UserDetailsImpl(user1);
		
		userDetailsImpl2 = null;

		assertEquals(false, userDetailsImpl1.equals(userDetailsImpl2));
	}
	
	@Test
	void equalsTest3() {
		
		user1 = new User();
		user1.setId("id");
	
		userDetailsImpl1 = new UserDetailsImpl(user1);
		
		dummyTestModel = new DummyTestModel();
		dummyTestModel.setId("id");

		assertEquals(false, userDetailsImpl1.equals(dummyTestModel));
	}
	
	@Test
	void equalsTest4() {

		user1 = new User();
		user1.setId("id");
		
		userDetailsImpl1 = new UserDetailsImpl(user1);
		
		user2 = new User();
		user2.setId("id");
	
		userDetailsImpl2 = new UserDetailsImpl(user2);
				
		assertEquals(true, userDetailsImpl1.equals(userDetailsImpl2));		
	}
}
