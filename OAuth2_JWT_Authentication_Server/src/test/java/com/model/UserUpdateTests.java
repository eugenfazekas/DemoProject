package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserUpdateTests {
	
	private UserUpdate userUpdate;

	
	@Test
	void getterSetterTest() {
		
		userUpdate = new UserUpdate();
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");
		userUpdate.setOldPassword("test");
		userUpdate.setPassword("test2");
		userUpdate.setMfa(true);
		
		
		
		assertAll(		
	    		 () -> assertEquals("id", userUpdate.getId()),
	    		 () -> assertEquals("eu@fa.hu",userUpdate.getEmail()),
	    		 () -> assertEquals("test",userUpdate.getOldPassword()),
	    		 () -> assertEquals("test2",userUpdate.getPassword()),
	    		 () -> assertEquals(true, userUpdate.isMfa())
	    		);		
	}
	
	@Test
	void equalsTest1() {
		
		userUpdate = new UserUpdate();
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");
		userUpdate.setOldPassword("test");
		userUpdate.setPassword("test2");
		userUpdate.setMfa(true);
		
		UserUpdate userUpdate2 = new UserUpdate();
		userUpdate2.setId("id");
		userUpdate2.setEmail("eu@fa.hu");
		userUpdate2.setOldPassword("test");
		userUpdate2.setPassword("test2");
		userUpdate2.setMfa(true);
		
		assertEquals(true,userUpdate.equals(userUpdate2));

	}
	
	@Test
	void equalsTest2() {
		
		userUpdate = new UserUpdate();
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");
		
		UserUpdate userUpdate2 = new UserUpdate();
		assertEquals(false ,userUpdate.equals(userUpdate2));
		
		userUpdate2.setId("id");
		assertEquals(false ,userUpdate.equals(userUpdate2));
		
		userUpdate2.setEmail("eu@fa.hu");
		assertEquals(true ,userUpdate.equals(userUpdate2));
	}
	
	@Test
	void equalsTest3() {
		
		userUpdate = new UserUpdate();
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");

		UserUpdate userUpdate2 = new UserUpdate();
		
		userUpdate2.setEmail("eu@fa.hu");
		assertEquals(false ,userUpdate.equals(userUpdate2));
		
		userUpdate2.setId("id");
		assertEquals(true ,userUpdate.equals(userUpdate2));

	}
	
	@Test
	void toStringTest() {

		userUpdate = new UserUpdate();
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");
		userUpdate.setOldPassword("test");
		userUpdate.setPassword("test2");
		userUpdate.setMfa(true);
		
		assertEquals("UserUpdate [id=" + userUpdate.getId() + ", email=" + userUpdate.getEmail() + ", oldPassword=" + userUpdate.getOldPassword() + ", password=" + userUpdate.getPassword()
		+ ", mfa=" + userUpdate.isMfa() + "]",userUpdate.toString());
	}

}
