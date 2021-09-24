package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OneTimePasswordTests {
	
	private OneTimePassword oneTimePassword1;
	private OneTimePassword oneTimePassword2;
	private DummyTestModel dummyTestModel;
	
	
	@Test
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
	void equalsTest2() {
		
		oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setPassword("myPassword");
		oneTimePassword1.setEmail("eu@fa.hu");
		
		oneTimePassword2 = new OneTimePassword();
		
		assertEquals(false, oneTimePassword1.equals(oneTimePassword2));
	}
	
	@Test
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
	void toStringTest() {
		
		oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setPassword("myPassword");
		oneTimePassword1.setEmail("eu@fa.hu");
		assertEquals("OneTimePassword [id=" + oneTimePassword1.getId() + ", email=" + oneTimePassword1.getEmail() + ", password=" + oneTimePassword1.getPassword() + "]",oneTimePassword1.toString());
	}
}
