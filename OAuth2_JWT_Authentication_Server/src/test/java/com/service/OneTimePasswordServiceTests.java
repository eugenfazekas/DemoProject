package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.exception.NoUsernameOrPasswordException;
import com.model.OneTimePassword;
import com.model.User;
import com.repository.OneTimePasswordRepository;

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
		
		 Throwable throwable1 = assertThrows(NoUsernameOrPasswordException.class, () -> oneTimePasswordService.createOneTimePassword(null,null));
		 assertEquals("Authentication_Server.OneTimePasswordService.createOneTimePassword --> accountkey, email, usertype cannot be null!", throwable1.getMessage());
     
		 Throwable throwable2 = assertThrows(NoUsernameOrPasswordException.class, () -> oneTimePasswordService.createOneTimePassword("",null));
		 assertEquals("Authentication_Server.OneTimePasswordService.createOneTimePassword --> accountkey, email, usertype cannot be null!", throwable2.getMessage());
		 
		 Throwable throwable3 = assertThrows(NoUsernameOrPasswordException.class, () -> oneTimePasswordService.createOneTimePassword("",""));
		 assertEquals("Authentication_Server.OneTimePasswordService.createOneTimePassword --> accountkey, email, usertype cannot be null!", throwable3.getMessage());
		 
		 Throwable throwable4 = assertThrows(NoUsernameOrPasswordException.class, () -> oneTimePasswordService.createOneTimePassword(null,""));
		 assertEquals("Authentication_Server.OneTimePasswordService.createOneTimePassword --> accountkey, email, usertype cannot be null!", throwable4.getMessage());
	    
		 user = new User();
	     user.setId("id");
	     user.setEmail("eu@fa.hu");
	     user.setPassword(encoder.encode("myPassword"));
	     user.setActive(true);
	     user.setMfa(true);
	     
	     when(userService.findByEmail("eu@fa.hu")).thenReturn(user);
	     when(oneTimePasswordRepository.OneTimePasswordCheck("eu@fa.hu")).thenReturn(0);
	     when(oneTimePasswordRepository.createOneTimePassword(new OneTimePassword())).thenReturn(new OneTimePassword());
	     
	     assertEquals("One time password send", oneTimePasswordService.createOneTimePassword("eu@fa.hu","myPassword"));
	}
	
	@Test
	void createOneTimePasswordTest2() {
	     
	     user = new User();
	     user.setId("id");
	     user.setEmail("eu@fa.hu");
	     user.setPassword(encoder.encode("myPassword")); 
	     user.setActive(true);
	     user.setMfa(true);
	     
	     when(userService.findByEmail("eu@fa.hu")).thenReturn(user);
	     when(oneTimePasswordRepository.OneTimePasswordCheck("eu@fa.hu")).thenReturn(1);
	     when(oneTimePasswordRepository.createOneTimePassword(new OneTimePassword())).thenReturn(new OneTimePassword());
	     
	     assertEquals("Invalid username or password", oneTimePasswordService.createOneTimePassword("eu@fa.hu","myPassword1"));
		
	}
	
	@Test
	void createOneTimePasswordTest3() {
	     	     
	     user = new User();
	     user.setId("id");
	     user.setEmail("eu@fa.hu");
	     user.setPassword(encoder.encode("myPassword")); 
	     user.setActive(true);
	     user.setMfa(false); // user is not using mfa
	     
	     when(userService.findByEmail("eu@fa.hu")).thenReturn(user);
	     when(oneTimePasswordRepository.OneTimePasswordCheck("eu@fa.hu")).thenReturn(0);
	     when(oneTimePasswordRepository.createOneTimePassword(new OneTimePassword())).thenReturn(new OneTimePassword());
	     
	     assertEquals("Invalid username or password", oneTimePasswordService.createOneTimePassword("eu@fa.hu","myPassword"));
		
	}

	@Test
	void findOneTimePasswordTest1() {
		
		 Throwable throwable1 = assertThrows(NoUsernameOrPasswordException.class, () -> oneTimePasswordService.findOneTimePassword(null));
		 assertEquals("Authentication_Server.OneTimePasswordService.findOneTimePassword -->  email cannot be null or empty String!", throwable1.getMessage());
     
		 Throwable throwable2 = assertThrows(NoUsernameOrPasswordException.class, () -> oneTimePasswordService.findOneTimePassword(""));
		 assertEquals("Authentication_Server.OneTimePasswordService.findOneTimePassword -->  email cannot be null or empty String!", throwable2.getMessage());
	    
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
		
		 Throwable throwable1 = assertThrows(NoUsernameOrPasswordException.class, () -> oneTimePasswordService.removeOneTimePassword(null));
		 assertEquals("Authentication_Server.OneTimePasswordService.removeOneTimePassword -->  email cannot be null or empty String!", throwable1.getMessage());
    
		 Throwable throwable2 = assertThrows(NoUsernameOrPasswordException.class, () -> oneTimePasswordService.removeOneTimePassword(""));
		 assertEquals("Authentication_Server.OneTimePasswordService.removeOneTimePassword -->  email cannot be null or empty String!", throwable2.getMessage());
	    
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