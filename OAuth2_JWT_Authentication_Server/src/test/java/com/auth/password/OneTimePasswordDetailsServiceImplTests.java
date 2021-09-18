package com.auth.password;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.model.OneTimePassword;
import com.model.User;
import com.repository.OneTimePasswordRepository;
import com.repository.UserRepository;

@SpringBootTest
public class OneTimePasswordDetailsServiceImplTests {
	
	@Autowired
	private OneTimePasswordDetailsServiceImpl oneTimePasswordDetailsServiceImpl;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean 
	private OneTimePasswordRepository oneTimePasswordRepository;
	
	@Test
	void loadUserByUsernameTest1() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		User user = new User();
		user.setEmail("eu@fa.hu");
		user.setPassword("pass");
		user.setMfa(true);
		user.setAuthorities(authorities);
		
		OneTimePassword otp = new OneTimePassword();
		otp.setId("id");
		otp.setPassword("012345");
		otp.setEmail("eu@fa.hu");
		
		when(userRepository.findByEmail("eu@fa.hu")).thenReturn(user);
		
		when(oneTimePasswordRepository.findOneTimePassword("eu@fa.hu")).thenReturn(otp); System.out.println(otp.toString());System.out.println(user.toString());
		
		assertEquals(new OneTimePasswordDetailsImpl(otp, user.getAuthorities()), oneTimePasswordDetailsServiceImpl.loadUserByUsername("eu@fa.hu"));
	}
	
	@Test
	void loadUserByUsernameTest2() {
				
		// wrong username
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		User user = new User();
		user.setEmail("eu@fa.hu");
		user.setPassword("pass");
		user.setMfa(true);
		user.setAuthorities(authorities);
		
		OneTimePassword otp = new OneTimePassword();
		otp.setId("id");
		otp.setPassword("012345");
		otp.setEmail("eu@fa.hu");
		
		when(userRepository.findByEmail("eu@fa.hu")).thenReturn(user);
		
		when(oneTimePasswordRepository.findOneTimePassword("eu@fa.hu")).thenReturn(otp); System.out.println(otp.toString());System.out.println(user.toString());
		
		 Assertions.assertThrows(UsernameNotFoundException.class, () -> {  oneTimePasswordDetailsServiceImpl.loadUserByUsername("eu1@fa.hu");    }); 
	}
}
