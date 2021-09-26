package com.auth.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.model.User;

@SpringBootTest
public class UserAuthenticationProviderServiceTests {

	
	@Autowired
	private UserAuthenticationProviderService authenticationProviderService;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Test
	@DisplayName("Testing Authentication_Service UserAuthenticationProviderService authenticate function with valid user")
	void authenticateTest1() {
		
		TestingAuthenticationToken token = new TestingAuthenticationToken("username","password");

		List<GrantedAuthority> authorities = new ArrayList<>();	
		List<String> authorities2 = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("user"));
		authorities2.add("user");
		
		String username = "username";
		String password = "password";
				
		User user = new User();
		user.setId("id");
		user.setPassword(passwordEncoder.encode(password));
		user.setActive(true);
		user.setMfa(false);
		user.setAuthorities(authorities2);
		
		when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(new UserDetailsImpl(user));
		assertEquals(new UsernamePasswordAuthenticationToken(user.getId(), password, authorities), authenticationProviderService.authenticate(token));		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserAuthenticationProviderService authenticate function with multi phase authentication activated")
	void authenticateTest2() {
		
		TestingAuthenticationToken token = new TestingAuthenticationToken("username","password");

		List<GrantedAuthority> authorities = new ArrayList<>();	
		List<String> authorities2 = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("user"));
		authorities2.add("user");
		
		String username = "username";
		String password = "password"; 
				
		User user = new User();
		user.setId("id");
		user.setPassword(passwordEncoder.encode(password));
		user.setActive(true);
		user.setMfa(true); // is mfa
		user.setAuthorities(authorities2);
		
		when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(new UserDetailsImpl(user));
		Assertions.assertThrows(BadCredentialsException.class, () -> {  authenticationProviderService.authenticate(token);    });		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserAuthenticationProviderService authenticate function with wrong credentials")
	void authenticateTest3() {
		
		TestingAuthenticationToken token = new TestingAuthenticationToken("username","password");

		List<GrantedAuthority> authorities = new ArrayList<>();	
		List<String> authorities2 = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("user"));
		authorities2.add("user");
		
		String username = "username";
		String password = "password1";  //wrong password
				
		User user = new User();
		user.setId("id");
		user.setPassword(passwordEncoder.encode(password));
		user.setActive(true);
		user.setMfa(true);                 
		user.setAuthorities(authorities2);
		
		when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(new UserDetailsImpl(user));
		Assertions.assertThrows(BadCredentialsException.class, () -> {  authenticationProviderService.authenticate(token);    });		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserAuthenticationProviderService support function")
	void supportTest() {
		
		assertEquals(true, authenticationProviderService.supports(UsernamePasswordAuthenticationToken.class));
	}
}
