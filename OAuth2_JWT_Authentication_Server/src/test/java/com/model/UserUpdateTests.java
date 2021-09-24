package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserUpdateTests {
	
	private UserUpdate userUpdate1;
	private UserUpdate userUpdate2;
	private DummyTestModel dummyTestModel;
	
	@Test
	void getterSetterTest() {
		
		userUpdate1 = new UserUpdate();
		userUpdate1.setId("id");
		userUpdate1.setEmail("eu@fa.hu");
		userUpdate1.setOldPassword("test");
		userUpdate1.setPassword("test2");
		userUpdate1.setMfa(true);
						
		assertAll(		
	    		 () -> assertEquals("id", userUpdate1.getId()),
	    		 () -> assertEquals("eu@fa.hu",userUpdate1.getEmail()),
	    		 () -> assertEquals("test",userUpdate1.getOldPassword()),
	    		 () -> assertEquals("test2",userUpdate1.getPassword()),
	    		 () -> assertEquals(true, userUpdate1.isMfa())
	    		);		
	}
	
	@Test
	void equalsTest1() {
		
		userUpdate1 = new UserUpdate();
		userUpdate1.setId("id");
		userUpdate1.setEmail("eu@fa.hu");
		userUpdate1.setOldPassword("test");
		userUpdate1.setPassword("test2");
		userUpdate1.setMfa(true);
		
		UserUpdate userUpdate2 = new UserUpdate();
		userUpdate2 = userUpdate1;
		
		assertEquals(true,userUpdate1.equals(userUpdate2));
	}
	
	@Test
	void equalsTest2() {
		
		userUpdate1 = new UserUpdate();
		userUpdate1.setId("id");
		userUpdate1.setEmail("eu@fa.hu");
		
		UserUpdate userUpdate2 = new UserUpdate();
		
		assertEquals(false ,userUpdate1.equals(userUpdate2));
	}
	
	@Test
	void equalsTest3() {
		
		userUpdate1 = new UserUpdate();
		userUpdate1.setId("id");
		userUpdate1.setEmail("eu@fa.hu");
		
		dummyTestModel = new DummyTestModel();
		dummyTestModel.setId("id");
		dummyTestModel.setEmail("eu@fa.hu");
		
		assertEquals(false ,userUpdate1.equals(dummyTestModel));
	}
	
	@Test
	void equalsTest4() {
		
		userUpdate1 = new UserUpdate();
		userUpdate1.setId("id");
		userUpdate1.setEmail("eu@fa.hu");

		UserUpdate userUpdate2 = new UserUpdate();
		userUpdate2.setId("id");
		userUpdate2.setEmail("eu@fa.hu");
		
		assertEquals(true ,userUpdate1.equals(userUpdate2));
	}
	
	@Test
	void toStringTest() {

		userUpdate1 = new UserUpdate();
		userUpdate1.setId("id");
		userUpdate1.setEmail("eu@fa.hu");
		userUpdate1.setOldPassword("test");
		userUpdate1.setPassword("test2");
		userUpdate1.setMfa(true);
		
		assertEquals("UserUpdate [id=" + userUpdate1.getId() + ", email=" + userUpdate1.getEmail() + ", oldPassword=" + userUpdate1.getOldPassword() + ", password=" + userUpdate1.getPassword()
		+ ", mfa=" + userUpdate1.isMfa() + "]",userUpdate1.toString());
	}
}
