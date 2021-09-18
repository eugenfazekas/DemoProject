
package com.auth.password;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.OneTimePassword;

@SpringBootTest
public class OneTimePasswordDetailsImplTests {
	
	private OneTimePasswordDetailsImpl oneTimePasswordDetailsImpl;
	
	@Test
	void oneTimePasswordDetailsImplTest() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		
		OneTimePassword oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("id");
		oneTimePassword.setEmail("eu@fa.hu");
		oneTimePassword.setPassword("pass");
		
		oneTimePasswordDetailsImpl = new OneTimePasswordDetailsImpl(oneTimePassword,authorities);
		
		assertAll(		
	    		 () -> assertEquals("eu@fa.hu", oneTimePasswordDetailsImpl.getUsername()),
	    		 () -> assertEquals("pass", oneTimePasswordDetailsImpl.getPassword()),
	    		 () -> assertEquals(true, oneTimePasswordDetailsImpl.isAccountNonExpired()),
	    		 () -> assertEquals(true, oneTimePasswordDetailsImpl.isAccountNonLocked()),
	    		 () -> assertEquals(true, oneTimePasswordDetailsImpl.isCredentialsNonExpired()),
	    		 () -> assertEquals(true, oneTimePasswordDetailsImpl.isEnabled()),
	    		 () -> assertEquals("id", oneTimePasswordDetailsImpl.getId())	    		 
	    		);
	}

}
