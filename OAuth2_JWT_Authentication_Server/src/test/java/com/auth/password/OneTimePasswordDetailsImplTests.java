
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
	
	private OneTimePasswordDetailsImpl oneTimePasswordDetailsImpl1;
	private OneTimePasswordDetailsImpl oneTimePasswordDetailsImpl2;
	
	@Test
	void oneTimePasswordDetailsImplTest() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		
		OneTimePassword oneTimePassword = new OneTimePassword();
		oneTimePassword.setId("id");
		oneTimePassword.setEmail("eu@fa.hu");
		oneTimePassword.setPassword("pass");
		
		oneTimePasswordDetailsImpl1 = new OneTimePasswordDetailsImpl(oneTimePassword,authorities);
		
		assertAll(		
	    		 () -> assertEquals("eu@fa.hu", oneTimePasswordDetailsImpl1.getUsername()),
	    		 () -> assertEquals("pass", oneTimePasswordDetailsImpl1.getPassword()),
	    		 () -> assertEquals(true, oneTimePasswordDetailsImpl1.isAccountNonExpired()),
	    		 () -> assertEquals(true, oneTimePasswordDetailsImpl1.isAccountNonLocked()),
	    		 () -> assertEquals(true, oneTimePasswordDetailsImpl1.isCredentialsNonExpired()),
	    		 () -> assertEquals(true, oneTimePasswordDetailsImpl1.isEnabled()),
	    		 () -> assertEquals("id", oneTimePasswordDetailsImpl1.getId())	    		 
	    		);
	}
	
	@Test
	void equals1() {
		
		List<String> authorities = new ArrayList<String>();
		authorities.add("user");
		
		OneTimePassword oneTimePassword1 = new OneTimePassword();
		oneTimePassword1.setId("id");
		oneTimePassword1.setEmail("eu@fa.hu");
		oneTimePassword1.setPassword("pass");
		
		oneTimePasswordDetailsImpl1 = new OneTimePasswordDetailsImpl(oneTimePassword1,authorities);
		
		OneTimePassword oneTimePassword2 = new OneTimePassword();
		oneTimePassword2.setId("id");
		oneTimePassword2.setEmail("eu@fa.hu");
		oneTimePassword2.setPassword("pass");
		
		oneTimePasswordDetailsImpl2 = new OneTimePasswordDetailsImpl(oneTimePassword2,authorities);
		
		assertEquals(true, oneTimePasswordDetailsImpl1.equals(oneTimePasswordDetailsImpl2));
	}

}
