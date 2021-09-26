package com.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.events.source.SimpleSourceBean;

@SpringBootTest
public class SimpleSourceBeanTests {

	@Autowired
	private SimpleSourceBean simpleSourceBean;
	
	@Test
	@DisplayName("Testing Authentication_Service SimpleSourceBean publisUserAuthenticationId function with invalid and valid userId-s")
	void publisUserAuthenticationIdTest() {
		
		String uuid = UUID.randomUUID().toString();
		
		Assertions.assertThrows(RuntimeException.class, () -> {  simpleSourceBean.publisUserAuthenticationId("");    });
	    Assertions.assertThrows(RuntimeException.class, () -> {  simpleSourceBean.publisUserAuthenticationId(null);  });
	    
	    assertEquals(true, simpleSourceBean.publisUserAuthenticationId(uuid));
	}
}
