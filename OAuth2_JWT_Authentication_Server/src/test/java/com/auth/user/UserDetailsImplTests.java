package com.auth.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.User;

@SpringBootTest
public class UserDetailsImplTests {
	
	private UserDetailsImpl userDetailsImpl;
	
	@Test
	void UserDetailsImplTest() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		User user = new User();
		user.setId("id");
		user.setEmail("eu@fa.hu");
		user.setPassword("pass");
		user.setActive(true);
		user.setMfa(false);
		user.setAuthorities(authorities);
		
		userDetailsImpl = new UserDetailsImpl(user);
		
		assertAll(		
	    		 () -> assertEquals("eu@fa.hu", userDetailsImpl.getUsername()),
	    		 () -> assertEquals("pass", userDetailsImpl.getPassword()),
	    		 () -> assertEquals(true, userDetailsImpl.isAccountNonExpired()),
	    		 () -> assertEquals(true, userDetailsImpl.isAccountNonLocked()),
	    		 () -> assertEquals(true, userDetailsImpl.isCredentialsNonExpired()),
	    		 () -> assertEquals(true, userDetailsImpl.isEnabled()),
	    		 () -> assertEquals(false, userDetailsImpl.isMfa()),
	    		 () -> assertEquals("id", userDetailsImpl.getId())	    		 
	    		);
	}

}
