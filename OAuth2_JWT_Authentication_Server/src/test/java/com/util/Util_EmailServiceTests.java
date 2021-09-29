package com.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.mail.MessagingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Util_EmailServiceTests {

	@Autowired
	private Util_EmailService emailService;
		
	@Test
	@DisplayName("Testing Authentication_Service Util_EmailService sendMessageen function")
	void messageSendTest() throws MessagingException {
		
		assertEquals("message send!", emailService.sendMessageen("skybolt83@gmail.com", "Eugen Fazekas", "key")); 
	}
	
	@Test
	@DisplayName("Testing Authentication_Service Util_EmailService getters-setters")
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