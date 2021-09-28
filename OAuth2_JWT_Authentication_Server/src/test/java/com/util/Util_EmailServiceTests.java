package com.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Util_EmailServiceTests {

	@Autowired
	private Util_EmailService emailService;
		
	@Test
	void messageSendTest() throws MessagingException {
		
		assertEquals("message send!", emailService.sendMessageen("skybolt83@gmail.com", "Eugen Fazekas", "key")); 
	}
	
	@Test
	void getterSetterTest() {
		
		emailService.setSubjecten("Demo Project account activation");
		emailService.setText1en("Dear");
		emailService.setText2en("Thanks for you're registration to our website, and you can sign is with the followed username: ");
		emailService.setText3en("You can activate you're account by the followed link");
		emailService.setValidationLink("JUnit Test No validation link");
		
		assertAll(		
	    		 () -> assertEquals("Demo Project account activation", emailService.getSubjecten()),
	    		 () -> assertEquals("Dear", emailService.getText1en()),
	    		 () -> assertEquals("Thanks for you're registration to our website, and you can sign is with the followed username: ", emailService.getText2en()),
	    		 () -> assertEquals("You can activate you're account by the followed link", emailService.getText3en()),
	    		 () -> assertEquals("JUnit Test No validation link", emailService.getValidationLink())    		 
	    		);
	}
}