package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.model.OneTimePassword;
import com.model.User;
import com.repository.OneTimePasswordRepository;
import com.util.ServletRequest;

@SpringBootTest
public class OneTimePasswordServiceTests {

	@Autowired
	private OneTimePasswordService oneTimePasswordService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@MockBean
	private OneTimePasswordRepository oneTimePasswordRepository;
	
	@MockBean
	private UserService userService;
		
	@MockBean
	private ServletRequest servletRequest;
	
	private User user;
	
	@Test
	void createOneTimePasswordTableTest() {
		when(oneTimePasswordRepository.createOneTimePasswordTable()).thenReturn("OneTimePassword Table Created");	
		assertEquals("OneTimePassword Table Created", oneTimePasswordService.createOneTimePasswordTable());
	}
	
	@Test
	void dropOneTimePasswordTableTest() {
		when(oneTimePasswordRepository.dropOneTimePasswordTable()).thenReturn("OneTimePassword Table Deleted");
		assertEquals("OneTimePassword Table Deleted", oneTimePasswordService.dropOneTimePasswordTable());
	}

	@Test
	void createOneTimePasswordTest1() {
		
		 Assertions.assertThrows(RuntimeException.class, () -> {  oneTimePasswordService.createOneTimePassword();  });
	     Assertions.assertThrows(RuntimeException.class, () -> {  oneTimePasswordService.createOneTimePassword();  });
	     
	     when(servletRequest.getUsernameHeader()).thenReturn("eu@fa.hu");
	     when(servletRequest.getPasswordHeader()).thenReturn("myPassword");
	     
	     
	     user = new User();
	     user.setId("id");
	     user.setEmail("eu@fa.hu");
	     user.setPassword(encoder.encode("myPassword"));
	     user.setActive(true);
	     user.setMfa(true);
	     
	     when(userService.findByEmail("eu@fa.hu")).thenReturn(user);
	     when(oneTimePasswordRepository.OneTimePasswordCheck("eu@fa.hu")).thenReturn(0);
	     when(oneTimePasswordRepository.createOneTimePassword(new OneTimePassword())).thenReturn(new OneTimePassword());
	     
	     assertEquals("One time password send", oneTimePasswordService.createOneTimePassword());
	}
	
	@Test
	void createOneTimePasswordTest2() {
	     
	     when(servletRequest.getUsernameHeader()).thenReturn("eu@fa.hu");
	     when(servletRequest.getPasswordHeader()).thenReturn("myPassword1");//wrong password
	     
	     
	     user = new User();
	     user.setId("id");
	     user.setEmail("eu@fa.hu");
	     user.setPassword(encoder.encode("myPassword")); 
	     user.setActive(true);
	     user.setMfa(true);
	     
	     when(userService.findByEmail("eu@fa.hu")).thenReturn(user);
	     when(oneTimePasswordRepository.OneTimePasswordCheck("eu@fa.hu")).thenReturn(1);
	     when(oneTimePasswordRepository.createOneTimePassword(new OneTimePassword())).thenReturn(new OneTimePassword());
	     
	     assertEquals("Invalid username or password", oneTimePasswordService.createOneTimePassword());
		
	}
	
	@Test
	void createOneTimePasswordTest3() {
	     
	     when(servletRequest.getUsernameHeader()).thenReturn("eu@fa.hu");
	     when(servletRequest.getPasswordHeader()).thenReturn("myPassword");//wrong password
	     
	     
	     user = new User();
	     user.setId("id");
	     user.setEmail("eu@fa.hu");
	     user.setPassword(encoder.encode("myPassword")); 
	     user.setActive(true);
	     user.setMfa(false); // user is not using mfa
	     
	     when(userService.findByEmail("eu@fa.hu")).thenReturn(user);
	     when(oneTimePasswordRepository.OneTimePasswordCheck("eu@fa.hu")).thenReturn(0);
	     when(oneTimePasswordRepository.createOneTimePassword(new OneTimePassword())).thenReturn(new OneTimePassword());
	     
	     assertEquals("Invalid username or password", oneTimePasswordService.createOneTimePassword());
		
	}

	@Test
	void findOneTimePasswordTest1() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  oneTimePasswordService.findOneTimePassword("");  });
	    Assertions.assertThrows(RuntimeException.class, () -> {  oneTimePasswordService.findOneTimePassword(null);  });
	    
	    when(oneTimePasswordRepository.findOneTimePassword("eu@fa.hu")).thenReturn(new OneTimePassword());
	    assertEquals(new OneTimePassword(), oneTimePasswordService.findOneTimePassword("eu@fa.hu"));
		
	}
	
	@Test
	void findOneTimePasswordTest2() {

	    when(oneTimePasswordRepository.findOneTimePassword("eu@fa.hu")).thenReturn(null);
	    assertEquals(null, oneTimePasswordService.findOneTimePassword("eu@fa.hu"));
	    
	}
	
	@Test
	void removeOneTimePasswordTest1() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  oneTimePasswordService.removeOneTimePassword("");  });
	    Assertions.assertThrows(RuntimeException.class, () -> {  oneTimePasswordService.removeOneTimePassword(null);  });
	    
	    when(oneTimePasswordRepository.removeOneTimePassword("eu@fa.hu")).thenReturn("OneTimePassword Deleted");
	    assertEquals("OneTimePassword Deleted", oneTimePasswordService.removeOneTimePassword("eu@fa.hu"));
	}
	
	@Test
	void removeOneTimePasswordTest2() {

	    when(oneTimePasswordRepository.removeOneTimePassword("eu@fa.hu")).thenReturn(null);
	    assertEquals("OneTimePassword has not been deleted", oneTimePasswordService.removeOneTimePassword("eu@fa.hu"));
	}
	
	@Test 
	void getRandomNumberStringTest1() {
		
		for(int i = 0; i < 100; i++) {			
			String stringRandomNumber = oneTimePasswordService.getRandomNumberString();
			Integer randomNumber =  Integer.parseInt(stringRandomNumber);
			assertEquals(true, randomNumber > 0 && randomNumber < 1000000);
		}
	}
	
	@Test 
	void getRandomNumberStringTest2() {
		
		String stringRandomNumber = oneTimePasswordService.getRandomNumberString();
		Integer randomNumber =  Integer.parseInt(stringRandomNumber);
		assertEquals(false, randomNumber < 0 || randomNumber > 1000000);
	}
}