package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OneTimePasswordTests {
	
	private OneTimePassword oneTimePassword1;
	private OneTimePassword oneTimePassword2;
	private DummyTestModel dummyTestModel;
	
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePassword getter-setter tests")
	void getterSetterTest() {

		oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setPassword("myPassword");
		oneTimePassword1.setEmail("eu@fa.hu");
		
		assertAll(		
	    		 () -> assertEquals("id", oneTimePassword1.getId()),
	    		 () -> assertEquals("myPassword",oneTimePassword1.getPassword()),
	    		 () -> assertEquals("eu@fa.hu",oneTimePassword1.getEmail())
	    		);		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePassword equals function with cloned object")
	void equalsTest1() {
		
		oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setPassword("myPassword");
		oneTimePassword1.setEmail("eu@fa.hu");
		
		oneTimePassword2 = new OneTimePassword();
		oneTimePassword2 = oneTimePassword1;
		
		assertEquals(true, oneTimePassword1.equals(oneTimePassword2));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePassword equals function with null object")
	void equalsTest2() {
		
		oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setPassword("myPassword");
		oneTimePassword1.setEmail("eu@fa.hu");
		
		oneTimePassword2 = new OneTimePassword();
		
		assertEquals(false, oneTimePassword1.equals(oneTimePassword2));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePassword equals function with other class object")
	void equalsTest3() {
		
		oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setPassword("myPassword");
		oneTimePassword1.setEmail("eu@fa.hu");
		
		dummyTestModel = new DummyTestModel();
		dummyTestModel.setId("id");
		dummyTestModel.setPassword("myPassword");
		dummyTestModel.setEmail("eu@fa.hu");	
		
		assertEquals(false, oneTimePassword1.equals(dummyTestModel));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePassword equals function with same class but other instance")
	void equalsTest4() {
		
		oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setPassword("myPassword");
		oneTimePassword1.setEmail("eu@fa.hu");
		
		oneTimePassword2 = new OneTimePassword();
		oneTimePassword2.setId("id");
		oneTimePassword2.setPassword("myPassword");
		oneTimePassword2.setEmail("eu@fa.hu");
		
		assertEquals(true, oneTimePassword1.equals(oneTimePassword2));
	}

	@Test
	@DisplayName("Testing Authentication_Service OneTimePassword toString function")
	void toStringTest() {
		
		oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setPassword("myPassword");
		oneTimePassword1.setEmail("eu@fa.hu");
		assertEquals("OneTimePassword [id=" + oneTimePassword1.getId() + ", email=" + oneTimePassword1.getEmail() + ", password=" + oneTimePassword1.getPassword() + "]",oneTimePassword1.toString());
	}
}
