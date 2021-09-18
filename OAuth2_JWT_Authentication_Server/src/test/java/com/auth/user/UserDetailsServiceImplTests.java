package com.auth.user;

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

import com.model.User;
import com.repository.UserRepository;

@SpringBootTest
public class UserDetailsServiceImplTests {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	void loadUserByUsernameTest1() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		User user = new User();
		user.setEmail("eu@fa.hu");
		user.setPassword("pass");
		user.setMfa(true);
		user.setAuthorities(authorities);
		
		when(userRepository.findByEmail("eu@fa.hu")).thenReturn(user);
		
		assertEquals(new UserDetailsImpl(user), userDetailsServiceImpl.loadUserByUsername("eu@fa.hu"));
	}
	
	@Test
	void loadUserByUsernameTest2() {
		
		// wrong username
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {  userDetailsServiceImpl.loadUserByUsername("eu1@fa.hu");    });	
	}

}
