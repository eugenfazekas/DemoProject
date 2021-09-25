package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTests {
	
	private User user1;
	private User user2;
	private DummyTestModel dummyTestModel;
	
	List<String> authorities = new ArrayList<String>();
	
	@Test
	void getterSetterTest() {
		
		authorities.add("user");
		user1 = new User();
		user1.setId("id");
		user1.setEmail("eu@fa.hu");
		user1.setPassword("password");
		user1.setActive(true);
		user1.setMfa(true);
		user1.setAuthorities(authorities);
		
		assertAll(		
	    		 () -> assertEquals("id", user1.getId()),
	    		 () -> assertEquals("eu@fa.hu",user1.getEmail()),
	    		 () -> assertEquals("password",user1.getPassword()),
	    		 () -> assertEquals(true,user1.isActive()),
	    		 () -> assertEquals(true, user1.isMfa()),
	    		 () -> assertEquals(authorities.get(0), user1.getAuthorities().get(0))
	    		);		
	}	
	
	@Test
	void equalsTest1() {
		
		user1 = new User();
		user1.setId("id");
		user1.setEmail("eu@fa.hu");
		
	    user2 = new User();
		user2 = user1;
		
		assertEquals(true,user1.equals(user2));
	}
	
	@Test
	void equalsTest2() {
		
		user1 = new User();
		user1.setId("id");
		user1.setEmail("eu@fa.hu");
		
	    user2 = new User();

		assertEquals(false,user1.equals(user2));
	}
	
	@Test
	void equalsTest3() {
		
		user1 = new User();
		user1.setId("id");
		user1.setEmail("eu@fa.hu");
		
		dummyTestModel = new DummyTestModel();
		dummyTestModel.setId("id");
		dummyTestModel.setEmail("eu@fa.hu");

		assertEquals(false,user1.equals(dummyTestModel));
	}
	
	@Test
	void equalsTest4() {
		
		user1 = new User();
		user1.setId("id");
		user1.setEmail("eu@fa.hu");
		
	    user2 = new User();
		user2.setId("id");
		user2.setEmail("eu@fa.hu");
		assertEquals(true,user1.equals(user2));
	}

	@Test
	void toStringTest() {

		authorities.add("user");
		user1 = new User();
		user1.setId("id");
		user1.setEmail("eu@fa.hu");
		user1.setPassword("password");
		user1.setActive(true);
		user1.setMfa(true);
		user1.setAuthorities(authorities);
		
		assertEquals("User [id=" + user1.getId() + ", email=" + user1.getEmail() + ", password=" + user1.getPassword() + ", active=" + user1.isActive() + ", mfa=" + user1.isMfa()
				+ ", authorities=" + user1.getAuthorities() + "]", user1.toString());
	}
}
