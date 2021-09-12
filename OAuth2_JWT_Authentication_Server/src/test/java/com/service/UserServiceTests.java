package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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
import com.util.Util;


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
	
	@MockBean
	private Util util;
	
	@Test
	void createUsersTableTest () {
		when(mockUserRepository.createUsersTable()).thenReturn("Users Table Created!");	
		assertEquals("Users Table Created!", userService.createUsersTable());
	}
	
	@Test
	void dropUsersTableTest () {
		when(mockUserRepository.dropUsersTable()).thenReturn("Users Table Dropped!");	
		assertEquals("Users Table Dropped!", userService.dropUsersTable());
	}

	@Test
	void findByEmailTest1() {
		
		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.findByEmail(EMAIL)).thenReturn(new User());	
		
		 Assertions.assertThrows(RuntimeException.class, () -> {  userService.findByEmail("");    });
	     Assertions.assertThrows(RuntimeException.class, () -> {  userService.findByEmail(null);  });

	     assertEquals(new User(), userService.findByEmail(EMAIL));
	}
	
	@Test
	void findByEmailTest2() {
		
		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.findByEmail(EMAIL)).thenReturn(null);	

	    assertEquals(null, userService.findByEmail(EMAIL));
	}
	
	@Test
	void userExistCheckTest1() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.userExistCheck(null); });
		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.userExistCheck(EMAIL)).thenReturn(1) ;	
		assertEquals(true, userService.userExistCheck(EMAIL));
	}
	
	@Test
	void userExistCheckTest2() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.userExistCheck(""); });
		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.userExistCheck(EMAIL)).thenReturn(0) ;	
		assertEquals(false, userService.userExistCheck(EMAIL));
	}
	
	@Test
	void userActivation1() {
				
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.userActivation(""); });
		
		String key = "key";
		
		user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		
		AccountKey accountKey = new AccountKey();
		accountKey.setEmail(user.getEmail());
		
		when(mockAccountKeyService.keyCheck(key)).thenReturn(true);
		
		when(mockAccountKeyService.findAccountKey(key)).thenReturn(accountKey);
		
		when(mockUserRepository.findByEmail(accountKey.getEmail())).thenReturn(user);
		
		when(mockUserRepository.setActiveUser(user.getEmail())).thenReturn("User Activated!");
		when(mockAccountKeyService.removeKey(key)).thenReturn("AccountKey Successfully removed");
		doNothing().when(mockProxyServer).sendNewUserId(user.getId());
		
		assertEquals("User Successfully activated!", userService.userActivation(key));
	}
	
	@Test
	void userActivation2() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.userActivation(null); });
		
		String key = "key";
		when(mockAccountKeyService.keyCheck(key)).thenReturn(false);
		when(mockAccountKeyService.findAccountKey(key)).thenReturn(null);
		when(mockUserRepository.setActiveUser("eu@fa.hu")).thenReturn(null);
		when(mockAccountKeyService.removeKey(key)).thenReturn(null);
		doNothing().when(mockProxyServer).sendNewUserId("userID");
		
		assertEquals("User have not been activated!", userService.userActivation(key));
	}

	@Test
	void updateUserTest1() {
		
		UserUpdate userUpdate = new UserUpdate();		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.updateUser(userUpdate); });
		
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");
		userUpdate.setOldPassword("myPassword");
		userUpdate.setPassword("jelszo");
		
	    user = new User();			
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setPassword("myPassword");
		
		when(mockUserRepository.findById("id")).thenReturn(user);
		
		when(mockEncoder.encode("myPassword")).thenReturn("myPassword");
		
		when(mockEncoder.matches(userUpdate.getOldPassword(),user.getPassword())).thenReturn(true);
		
		when(mockUserRepository.updateUser(user)).thenReturn("User have been updated!");
			
		assertEquals("User have been updated!", userService.updateUser(userUpdate));
	}
	
	@Test
	void updateUserTest2() {
		
		UserUpdate userUpdate = new UserUpdate();		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.updateUser(userUpdate); });
		
		userUpdate.setId("id");
		userUpdate.setEmail("eu@fa.hu");
		userUpdate.setOldPassword("myPassword1");
		
		user = new User();			
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setPassword("myPassword");
		
		when(mockEncoder.encode("myPassword2")).thenReturn("myPassword2");
		when(mockEncoder.matches(userUpdate.getOldPassword(),user.getPassword())).thenReturn(false);
		when(mockUserRepository.findById("id")).thenReturn(user);
		when(mockUserRepository.updateUser(user)).thenReturn("User have been updated!");
			
		assertEquals("Update have not been executed!", userService.updateUser(userUpdate));
	}

	@Test
	void mfaCheckTest1() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.mfaCheck(); });	
		
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		
		when(servletRequest.getUsernameHeader()).thenReturn(user.getEmail());
		when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(user);
		doNothing().when(simpleSourceBean).publisUserAuthenticationId(user.getId());		
		assertEquals("false", userService.mfaCheck());
	}
	
	@Test
	void mfaCheckTest2() {
		
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setMfa(true);
		
		when(servletRequest.getUsernameHeader()).thenReturn(user.getEmail());
		when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(user);
		doNothing().when(simpleSourceBean).publisUserAuthenticationId(user.getId());		
		assertEquals("true", userService.mfaCheck());
	}
	
	@Test
	void mfaCheckTest3() {
		
		when(servletRequest.getUsernameHeader()).thenReturn("eu@fa.hu");
		when(mockUserRepository.findByEmail("eu@fa.hu")).thenReturn(null);
		doNothing().when(simpleSourceBean).publisUserAuthenticationId("eu@fa.hu");		
		assertEquals("User Not Exist!", userService.mfaCheck());
	}

	@Test
	void registerUserTest1() throws Exception, IOException {	
		
		user = new User();	
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.registerUser(user); });
		
		user.setEmail("");
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.registerUser(user); });
		
		user.setEmail("eu@fa.hu");
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.registerUser(user); });
		
		user.setPassword("");
		
		Assertions.assertThrows(RuntimeException.class, () -> {  userService.registerUser(user); });
	}
		
	@Test
	void registerUserTest2() throws Exception, IOException  {	
		
		when(util.UUID_generator()).thenReturn("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
				
		user = new User();	
	
		user.setEmail("eu@fa.hu");
		
	    user.setPassword("myPassword");
	    
	    user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
	    
	    AccountKey account = new AccountKey(user.getId(),"user",user.getEmail());
	    
	    when(mockAccountKeyService.createAccountKey(account)).thenReturn("New AccountKey Created");
		
		when(mockEmailService.sendMessageen(user.getEmail(), "User! ", user.getId())).thenReturn("message send!");
	
		when(mockUserRepository.registerUser(user,"user")).thenReturn("User Registered");
		
		assertEquals("New User registered!", userService.registerUser(user));
	}

	@Test
	void registerUserTest3() throws Exception, IOException {	
		
		when(util.UUID_generator()).thenReturn("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
		
		user = new User();	
	
		user.setEmail("eu@fa.hu");
		
	    user.setPassword("myPassword");
	    
	    user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
	    
	    AccountKey account = new AccountKey(user.getId(),"user",user.getEmail());
	    
	    when(mockAccountKeyService.createAccountKey(account)).thenReturn(null);
		
		when(mockEmailService.sendMessageen(user.getEmail(), "User! ", user.getId())).thenReturn("message send!");
	
		when(mockUserRepository.registerUser(user,"user")).thenReturn("User Registered");

		assertEquals(null, userService.registerUser(user));
	}
	
	@Test
	void registerUserTest4() throws Exception, IOException {
		
		when(util.UUID_generator()).thenReturn("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
		
		user = new User();	
	
		user.setEmail("eu@fa.hu");
		
	    user.setPassword("myPassword");
	    
	    user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
	    
	    AccountKey account = new AccountKey(user.getId(),"user",user.getEmail());
	    
	    when(mockAccountKeyService.createAccountKey(account)).thenReturn("New AccountKey Created");
		
		when(mockEmailService.sendMessageen(user.getEmail(), "User! ", user.getId())).thenReturn(null);
	
		when(mockUserRepository.registerUser(user,"user")).thenReturn("User Registered");

		assertEquals(null, userService.registerUser(user));
	}
	
	@Test
	void registerUserTest5() throws Exception, IOException {
		
		when(util.UUID_generator()).thenReturn("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
		
		user = new User();	
	
		user.setEmail("eu@fa.hu");
		
	    user.setPassword("myPassword");
	    
	    user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
	    
	    AccountKey account = new AccountKey(user.getId(),"user",user.getEmail());
	    
	    when(mockAccountKeyService.createAccountKey(account)).thenReturn("New AccountKey Created");
		
		when(mockEmailService.sendMessageen(user.getEmail(), "User! ", user.getId())).thenReturn("message send!");
	
		when(mockUserRepository.registerUser(user,"user")).thenReturn(null);

		assertEquals(null, userService.registerUser(user));
	}
}
