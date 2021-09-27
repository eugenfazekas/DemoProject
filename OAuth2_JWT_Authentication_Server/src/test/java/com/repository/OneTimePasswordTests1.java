package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.OneTimePassword;

@SpringBootTest
public class OneTimePasswordTests1 {
	
	@Autowired
	private OneTimePasswordRepository oneTimePasswordRepository;
	
	private OneTimePassword oneTimePassword;
	
	@Test
	void a1 () {
		
		//createOneTimePasswordTable
				assertEquals("OneTimePassword Table Deleted", oneTimePasswordRepository.dropOneTimePasswordTable());
	}

	@Test
	void a2 () {
		
		//dropOneTimePasswordTable
		assertEquals("OneTimePassword Table Created", oneTimePasswordRepository.createOneTimePasswordTable());
	}

	@Test
	void a3() {
		
		//createOneTimePassword
		
		oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		oneTimePassword.setEmail("eu1@fa.hu");
		oneTimePassword.setPassword("012345");	
		
		assertEquals(oneTimePassword, oneTimePasswordRepository.createOneTimePassword(oneTimePassword));
	}

	@Test
	void a4() {
		
		//OneTimePasswordCheck
		assertEquals(1, oneTimePasswordRepository.OneTimePasswordCheck("eu1@fa.hu"));
		
	}
	
	@Test
	void a5() {
		
		//OneTimePasswordCheck
				assertEquals(0, oneTimePasswordRepository.OneTimePasswordCheck("eu2@fa.hu"));
	}

	@Test
	void a6() {

		//findOneTimePassword
		
		oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		oneTimePassword.setEmail("eu1@fa.hu");
		oneTimePassword.setPassword("012345");	
		assertEquals(oneTimePassword, oneTimePasswordRepository.findOneTimePassword("eu1@fa.hu"));
		
		}
	
	@Test
	void a7() {

		//findOneTimePassword
		
		assertEquals(null, oneTimePasswordRepository.findOneTimePassword("eu2@fa.hu"));
		
		}
	
	@Test
	void a8() {	
		
		//removeOneTimePassword
		assertEquals("OneTimePassword Deleted", oneTimePasswordRepository.removeOneTimePassword("eu1@fa.hu"));	
	}
	
	@Test
	void a9() {
		
		//verify otp remove
		assertEquals(0, oneTimePasswordRepository.OneTimePasswordCheck("eu1@fa.hu"));
		
	}

}
