package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OneTimePasswordTests {
	
	private OneTimePassword oneTimePassword;
	
	@Test
	void getterSetterTest() {

		oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("id");
		oneTimePassword.setPassword("myPassword");
		oneTimePassword.setEmail("eu@fa.hu");
		
		assertAll(		
	    		 () -> assertEquals("id", oneTimePassword.getId()),
	    		 () -> assertEquals("myPassword",oneTimePassword.getPassword()),
	    		 () -> assertEquals("eu@fa.hu",oneTimePassword.getEmail())
	    		);		
	}
	
	@Test
	void equalsTest() {
		
		oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("id");
		oneTimePassword.setPassword("myPassword");
		oneTimePassword.setEmail("eu@fa.hu");
		
		OneTimePassword oneTimePassword2 = new OneTimePassword();
		oneTimePassword2.setId("id");
		
		assertEquals(false, oneTimePassword.equals(oneTimePassword2));
		
		oneTimePassword2.setEmail("eu@fa.hu");
		
		assertEquals(true, oneTimePassword.equals(oneTimePassword2));
	}

	@Test
	void toStringTest() {
		
		oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("id");
		oneTimePassword.setPassword("myPassword");
		oneTimePassword.setEmail("eu@fa.hu");
		assertEquals("OneTimePassword [id=" + oneTimePassword.getId() + ", email=" + oneTimePassword.getEmail() + ", password=" + oneTimePassword.getPassword() + "]",oneTimePassword.toString());
	}
}
