package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.view.RedirectView;

import com.events.source.SimpleSourceBean;
import com.exception.DuplicateUserException;
import com.exception.NoUserActivationKeyException;
import com.exception.NoUserIdException;
import com.exception.NoUsernameOrPasswordException;
import com.model.AccountKey;
import com.model.User;
import com.model.UserUpdate;
import com.repository.UserRepository;
import com.util.Util_EmailService;
import com.util.Util_ProxyServer;
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
	private Util_EmailService mockEmailService;
	
	@MockBean
    private BCryptPasswordEncoder mockEncoder;
	
	@MockBean
	private Util_ProxyServer mockProxyServer;
	
	@MockBean
	private SimpleSourceBean simpleSourceBean;	
	
	@MockBean
	private Util util;
	
	@Test
	@DisplayName("Testing Authentication_Service UserService createUsersTable function")
	void createUsersTableTest () {
		when(mockUserRepository.createUsersTable()).thenReturn("Users Table Created!");	
		assertEquals("Users Table Created!", userService.createUsersTable());
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService dropUsersTable function")
	void dropUsersTableTest () {
		when(mockUserRepository.dropUsersTable()).thenReturn("Users Table Dropped!");	
		assertEquals("Users Table Dropped!", userService.dropUsersTable());
	}

	@Test
	@DisplayName("Testing Authentication_Service UserService findByEmail function; testing EXCEPTION-s")
	void findByEmailTest1() {
		
		Throwable throwable1 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.findByEmail(null));
		 assertEquals("Authentication_Server.UserService.findByEmail --> email cannot be null or empty string!", throwable1.getMessage());
		
		Throwable throwable2 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.findByEmail(""));
		 assertEquals("Authentication_Server.UserService.findByEmail --> email cannot be null or empty string!", throwable2.getMessage());
		
		String EMAIL = "eu@fa.hu";
		
		when(mockUserRepository.findByEmail(EMAIL)).thenReturn(new User());	
	     assertEquals(new User(), userService.findByEmail(EMAIL));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService findByEmail function with null return value from repository")
	void findByEmailTest2() {
		
		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.findByEmail(EMAIL)).thenReturn(null);	

	    assertEquals(null, userService.findByEmail(EMAIL));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService userExistCheck function; testing EXCEPTION-s")
	void userExistCheckTest1() {
		
		Throwable throwable1 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.userExistCheck(null));
		 assertEquals("Authentication_Server.UserService.userExistCheck --> email cannot be null or empty string!", throwable1.getMessage());
		 
		Throwable throwable2 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.userExistCheck(""));
		 assertEquals("Authentication_Server.UserService.userExistCheck --> email cannot be null or empty string!", throwable2.getMessage());
		 
		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.userExistCheck(EMAIL)).thenReturn(1) ;	
		assertEquals(true, userService.userExistCheck(EMAIL));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService findByEmail function with 0 return value from repository")
	void userExistCheckTest2() {

		String EMAIL = "eu@fa.hu";
		when(mockUserRepository.userExistCheck(EMAIL)).thenReturn(0) ;	
		assertEquals(false, userService.userExistCheck(EMAIL));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService userActivation function; testing EXCEPTION-s then activating user")
	void userActivation1() {
				
		Throwable throwable1 = assertThrows(NoUserActivationKeyException.class, () -> userService.userActivation(null));
		 assertEquals("Authentication_Server.UserService.userActivation --> key cannot be null or empty string!", throwable1.getMessage());
		 
		 Throwable throwable2 = assertThrows(NoUserActivationKeyException.class, () -> userService.userActivation(""));
		 assertEquals("Authentication_Server.UserService.userActivation --> key cannot be null or empty string!", throwable2.getMessage());
		
		String key = "key";
		
		user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:4200");
		
		AccountKey accountKey = new AccountKey();
		accountKey.setEmail(user.getEmail());
		
		when(mockAccountKeyService.keyCheck(key)).thenReturn(true);
		
		when(mockAccountKeyService.findAccountKey(key)).thenReturn(accountKey);
		
		when(mockUserRepository.findByEmail(accountKey.getEmail())).thenReturn(user);
		
		when(mockUserRepository.setActiveUser(user.getEmail())).thenReturn("User Activated!");
		when(mockAccountKeyService.removeKey(key)).thenReturn("AccountKey Successfully removed");
		when(mockProxyServer.sendNewUserId(user.getId())).thenReturn("Resource Server Status 200 user activated");
		when(util.redirectView("User Successfully activated!")).thenReturn(redirectView);
		
		assertEquals(redirectView, userService.userActivation(key));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService userActivation function with null return values from dependencys")
	void userActivation2() {
		
		String key = "key";
		when(mockAccountKeyService.keyCheck(key)).thenReturn(false);
		when(mockAccountKeyService.findAccountKey(key)).thenReturn(null);
		when(mockUserRepository.setActiveUser("eu@fa.hu")).thenReturn(null);
		when(mockAccountKeyService.removeKey(key)).thenReturn(null);
		when(mockProxyServer.sendNewUserId("userId")).thenReturn(null);
		
		assertEquals(null, userService.userActivation(key));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService userActivation function with null return values from redirectView")
	void userActivation3() {
		
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
		when(mockProxyServer.sendNewUserId(user.getId())).thenReturn("Resource Server Status 200 user activated");
		when(util.redirectView("key")).thenReturn(null);
		
		assertEquals(null, userService.userActivation(key));
	}

	@Test
	@DisplayName("Testing Authentication_Service UserService updateUser function with valid user")
	void updateUserTest1() {
		
		UserUpdate userUpdate = new UserUpdate();	
		
		Throwable throwable = assertThrows(NoUserIdException.class, () -> userService.updateUser(userUpdate));
		 assertEquals("Authentication_Server.UserService.updateUser --> updateUser.id cannot be null or empty string!", throwable.getMessage());
		
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
	@DisplayName("Testing Authentication_Service UserService updateUser function with invalid user password")
	void updateUserTest2() {
		
		UserUpdate userUpdate = new UserUpdate();			
		userUpdate.setId("");
		
		Throwable throwable = assertThrows(NoUserIdException.class, () -> userService.updateUser(userUpdate));
		 assertEquals("Authentication_Server.UserService.updateUser --> updateUser.id cannot be null or empty string!", throwable.getMessage());
		
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
	@DisplayName("Testing Authentication_Service UserService updateUser function with invalid repository return")
	void updateUserTest3() {
		
		UserUpdate userUpdate = new UserUpdate();			
		userUpdate.setId("");
		
		Throwable throwable = assertThrows(NoUserIdException.class, () -> userService.updateUser(userUpdate));
		 assertEquals("Authentication_Server.UserService.updateUser --> updateUser.id cannot be null or empty string!", throwable.getMessage());
		
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
		when(mockUserRepository.updateUser(user)).thenReturn("User not updated!");
			
		assertEquals("Update have not been executed!", userService.updateUser(userUpdate));
	}

	@Test
	@DisplayName("Testing Authentication_Service UserService mfaCheck function with invalid  mfa user")
	void mfaCheckTest1() {
		
		Throwable throwable1 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.mfaCheck(null));
		 assertEquals("Authentication_Server.UserService.mfaCheck --> header email cannot be null or empty string!", throwable1.getMessage());
				 
		 Throwable throwable2 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.mfaCheck(""));
		 assertEquals("Authentication_Server.UserService.mfaCheck --> header email cannot be null or empty string!", throwable2.getMessage());
				 
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");

		when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(user);
		when(simpleSourceBean.publisUserAuthenticationId(user.getId())).thenReturn(true);	
		assertEquals("false", userService.mfaCheck("eu@fa.hu"));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService mfaCheck function with valid  mfa user")
	void mfaCheckTest2() {
		
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setMfa(true);

		when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(user);
		when(simpleSourceBean.publisUserAuthenticationId(user.getId())).thenReturn(true);			
		assertEquals("true", userService.mfaCheck("eu@fa.hu"));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserService mfaCheck function with not registered user")
	void mfaCheckTest3() {

		when(mockUserRepository.findByEmail("eu@fa.hu")).thenReturn(null);		
		assertEquals("User Not Exist!", userService.mfaCheck("eu@fa.hu"));
	}

	@Test
	@DisplayName("Testing Authentication_Service UserService registerUser function; testing EXCEPTION-s")
	void registerUserTest1() throws Exception, IOException {	
		
		user = new User();	
		
		Throwable throwable1 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.registerUser(user));
		 assertEquals("Authentication_Server.UserService.registerUser --> email or password cannot be null or empty string!", throwable1.getMessage());
		 	
		user.setEmail("");
		
		Throwable throwable2 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.registerUser(user));
		 assertEquals("Authentication_Server.UserService.registerUser --> email or password cannot be null or empty string!", throwable2.getMessage());
		
		user.setEmail("eu@fa.hu");
		
		Throwable throwable3 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.registerUser(user));
		 assertEquals("Authentication_Server.UserService.registerUser --> email or password cannot be null or empty string!", throwable3.getMessage());
		
		user.setPassword("");
		
		Throwable throwable4 = assertThrows(NoUsernameOrPasswordException.class, () -> userService.registerUser(user));
		 assertEquals("Authentication_Server.UserService.registerUser --> email or password cannot be null or empty string!", throwable4.getMessage());
		 
		 user.setPassword("password");
		 user.setEmail("eu@fa.hu");
		 
		 when(mockUserRepository.userExistCheck("eu@fa.hu")).thenReturn(1);
		 
		 Throwable throwable5 = assertThrows(DuplicateUserException.class, () -> userService.registerUser(user));
		 assertEquals("Authentication_Server.UserService.registerUser --> User with this username allready exist!", throwable5.getMessage());	
	 
	}
		
	@Test
	@DisplayName("Testing Authentication_Service UserService registerUser function then register user")
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
	@DisplayName("Testing Authentication_Service UserService registerUser function but with null return value from createAccountKey function")
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
	@DisplayName("Testing Authentication_Service UserService registerUser function but with null return value from sendMessageen function")
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
	@DisplayName("Testing Authentication_Service UserService registerUser function but with null return value from registerUser(UserRepository) function")
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
	
	@Test
	@DisplayName("Testing Authentication_Service UserService registerUser function but with MessagingException from sendMessageen")
	void registerUserTest6() throws MessagingException {
		
		when(util.UUID_generator()).thenReturn("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
		
		user = new User();	
	
		user.setEmail("eu@fa.hu");
		
	    user.setPassword("myPassword");
	    
	    user.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
	    
	    AccountKey account = new AccountKey(user.getId(),"user",user.getEmail());
	    
	    when(mockAccountKeyService.createAccountKey(account)).thenReturn("New AccountKey Created");
		
		when(mockEmailService.sendMessageen(user.getEmail(), "User! ", user.getId())).thenThrow(MessagingException.class);
	
		when(mockUserRepository.registerUser(user,"user")).thenReturn("User Registered");

		assertEquals(null, userService.registerUser(user));
	}
}
