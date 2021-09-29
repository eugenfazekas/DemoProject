package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
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
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository dropOneTimePasswordTable function")
	void a1 () {

		assertEquals("OneTimePassword Table Deleted", oneTimePasswordRepository.dropOneTimePasswordTable());
	}

	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository createOneTimePasswordTable function")
	void a2 () {

		assertEquals("OneTimePassword Table Created", oneTimePasswordRepository.createOneTimePasswordTable());
	}

	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository createOneTimePassword function")
	void a3() {

		oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		oneTimePassword.setEmail("eu1@fa.hu");
		oneTimePassword.setPassword("012345");	
		
		assertEquals(oneTimePassword, oneTimePasswordRepository.createOneTimePassword(oneTimePassword));
	}

	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository OneTimePasswordCheck function; counting inserted keys with this otp-s ")
	void a4() {

		assertEquals(1, oneTimePasswordRepository.OneTimePasswordCheck("eu1@fa.hu"));
		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository OneTimePasswordCheck function; counting inserted keys with this otp-s ")
	void a5() {

		assertEquals(0, oneTimePasswordRepository.OneTimePasswordCheck("eu2@fa.hu"));
	}

	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository findOneTimePassword function with valid otp")
	void a6() {

		oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8e");
		oneTimePassword.setEmail("eu1@fa.hu");
		oneTimePassword.setPassword("012345");	
		assertEquals(oneTimePassword, oneTimePasswordRepository.findOneTimePassword("eu1@fa.hu"));
		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository findOneTimePassword function with invalid otp")
	void a7() {

		assertEquals(null, oneTimePasswordRepository.findOneTimePassword("eu2@fa.hu"));	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository removeOneTimePassword function with valid otp")
	void a8() {	

		assertEquals("OneTimePassword Deleted", oneTimePasswordRepository.removeOneTimePassword("eu1@fa.hu"));	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordRepository removeOneTimePassword function with invalid otp")
	void a9() {

		assertEquals(0, oneTimePasswordRepository.OneTimePasswordCheck("eu1@fa.hu"));		
	}
}
