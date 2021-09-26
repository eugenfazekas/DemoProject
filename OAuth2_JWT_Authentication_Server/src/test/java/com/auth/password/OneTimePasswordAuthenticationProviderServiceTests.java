package com.auth.password;

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

import com.model.OneTimePassword;
import com.model.User;
import com.service.OneTimePasswordService;

@SpringBootTest
public class OneTimePasswordAuthenticationProviderServiceTests {
	
	@Autowired
	private OneTimePasswordAuthenticationProviderService oneTimePasswordAuthenticationProviderService;
	
	@MockBean
	private OneTimePasswordDetailsServiceImpl oneTimePasswordDetailsServiceImpl;
	
	@MockBean
	private OneTimePasswordService oneTimePasswordService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordProviderService authenticate function with valid user")
	void authenticateTest1() {
		
		TestingAuthenticationToken token = new TestingAuthenticationToken("username","password");
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<String> authorities2 = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("user"));
		authorities2.add("user");
		String username = "username";
		String password = "password";
				
		User user = new User();
		user.setAuthorities(authorities2);
	
		OneTimePassword otp = new OneTimePassword();
		otp.setId("id");
		otp.setEmail("username");
		otp.setPassword(passwordEncoder.encode(password));
		
		when(oneTimePasswordDetailsServiceImpl.loadUserByUsername(username)).thenReturn(new OneTimePasswordDetailsImpl(otp,user.getAuthorities()));

		assertEquals(new UsernamePasswordAuthenticationToken(otp.getId(), password, authorities), oneTimePasswordAuthenticationProviderService.authenticate(token));		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordProviderService authenticate function with invalid user credentials")
	void authenticateTest2() {
		
		TestingAuthenticationToken token = new TestingAuthenticationToken("username","password");
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<String> authorities2 = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("user"));
		authorities2.add("user");
		String username = "username";
		String password = "password1"; //wrong password
				
		User user = new User();
		user.setAuthorities(authorities2);
	
		OneTimePassword otp = new OneTimePassword();
		otp.setId("id");
		otp.setEmail("username");
		otp.setPassword(passwordEncoder.encode(password));
		
		when(oneTimePasswordDetailsServiceImpl.loadUserByUsername(username)).thenReturn(new OneTimePasswordDetailsImpl(otp,user.getAuthorities()));

		 Assertions.assertThrows(BadCredentialsException.class, () -> {  oneTimePasswordAuthenticationProviderService.authenticate(token);    });		
	}
	
	@Test
	@DisplayName("Testing Authentication_Service OneTimePasswordProviderService support function")
	void supportTest() {
		
		assertEquals(true, oneTimePasswordAuthenticationProviderService.supports(UsernamePasswordAuthenticationToken.class));
	}
}
