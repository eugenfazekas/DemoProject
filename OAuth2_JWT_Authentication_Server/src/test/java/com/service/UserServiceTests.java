package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.events.source.SimpleSourceBean;
import com.model.AccountKey;
import com.model.User;
import com.model.UserUpdate;
import com.repository.UserRepository;
import com.util.EmailService;
import com.util.ProxyServer;
import com.util.ServletRequest;


@SpringBootTest
public class UserServiceTests {
	
	private User user; 
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository mockUserRepository;
	
	@MockBean
	private AccountKeyService mockAccountKeyService;
	
	@MockBean
	private EmailService mockEmailService;
	
	@MockBean
    private BCryptPasswordEncoder mockEncoder;
	
	@MockBean
	private ProxyServer mockProxyServer;
	
	@MockBean
	private SimpleSourceBean simpleSourceBean;	
	
	@MockBean
	private ServletRequest servletRequest;

	@Test
	void findByEmailTest() {
		
		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.findByEmail(EMAIL)).thenReturn(new User());	
		
		 Assertions.assertThrows(RuntimeException.class, () -> {  userService.findByEmail("");    });
	     Assertions.assertThrows(RuntimeException.class, () -> {  userService.findByEmail(null);  });

	     assertEquals(new User(), userService.findByEmail(EMAIL));

	}
	
	@Test
	void registerUserTest() throws Exception, IOException {	
		
		String register = "User Registered";
		
		user = new User();	
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.registerUser(user); });
		
		user.setEmail("eu@fa.hu");
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.registerUser(user); });
		
	    user.setPassword("myPassword");
	    
	    when(mockEncoder.encode("myPassword")).thenReturn("myPassword");

	    when(mockUserRepository.registerUser(user,"user")).thenReturn(register);
	    
	    doNothing().when(mockAccountKeyService).createAccountKey(new AccountKey("UUID","user","eu@fa.hu"));
	    
	    doNothing().when(mockEmailService).sendMessageen("email","fullname","key");

		assertEquals("New User registered!", userService.registerUser(user));
	}

	@Test
	void userExistCheckTest() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.userExistCheck(null); });
		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.userExistCheck(EMAIL)).thenReturn(1) ;	
		assertEquals(true, userService.userExistCheck(EMAIL));
	}
	

	@Test
	void userActivation() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.userActivation(null); });
		
		String key = "key";
		when(mockAccountKeyService.keyCheck("eu@fa.hu")).thenReturn(true);
		when(mockAccountKeyService.accountKey("eu@fa.hu")).thenReturn(new AccountKey());
		doNothing().when(mockUserRepository).setActiveUser("email");
		doNothing().when(mockAccountKeyService).removeKey(key);
		doNothing().when(mockProxyServer).sendNewUserId("userID");
		
		assertEquals("User Successfully activated!", userService.userActivation(key));
	}


	@Test
	void updateUserTest1() {
		
		UserUpdate userUpdate = new UserUpdate();		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.updateUser(userUpdate); });
		
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");
		userUpdate.setOldPassword("myPassword");
		
		User user = new User();			
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setPassword("myPassword");
		
		when(mockEncoder.encode("myPassword")).thenReturn("myPassword");
		when(mockUserRepository.findById("id")).thenReturn(user);
		when(mockUserRepository.updateUser(user)).thenReturn("User have been updated!");
			
		assertEquals("User have been updated!", userService.updateUser(userUpdate));
	}
	
	@Test
	void updateUserTest2() {
		
		UserUpdate userUpdate = new UserUpdate();		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.updateUser(userUpdate); });
		
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");
		userUpdate.setPassword("myPassword1");
		when(mockEncoder.encode("myPassword2")).thenReturn("myPassword2");
		when(mockUserRepository.findById("id")).thenReturn(new User());
		when(mockUserRepository.updateUser(new User())).thenReturn("null");
			
		assertEquals("Update have not been executed!", userService.updateUser(userUpdate));
	}

	@Test
	void mfaCheckTest1() {
		
		when(servletRequest.getEmailHeader()).thenReturn(null);
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.mfaCheck(); });
	}

	@Test
	void mfaCheckTest2() {
		
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		
		when(servletRequest.getEmailHeader()).thenReturn(user.getEmail());
		when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(user);
		doNothing().when(simpleSourceBean).publisUserAuthenticationId(user.getId());		
		assertEquals("false", userService.mfaCheck());
	}
	
	@Test
	void mfaCheckTest3() {
		
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setMfa(true);
		
		when(servletRequest.getEmailHeader()).thenReturn(user.getEmail());
		when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(user);
		doNothing().when(simpleSourceBean).publisUserAuthenticationId(user.getId());		
		assertEquals("true", userService.mfaCheck());
	}
	
	@Test
	void mfaCheckTest4() {
		
		when(servletRequest.getEmailHeader()).thenReturn("eu@fa.hu");
		when(mockUserRepository.findByEmail("eu@fa.hu")).thenReturn(null);
		doNothing().when(simpleSourceBean).publisUserAuthenticationId("eu@fa.hu");		
		assertEquals("User Not Exist!", userService.mfaCheck());
	}

}
