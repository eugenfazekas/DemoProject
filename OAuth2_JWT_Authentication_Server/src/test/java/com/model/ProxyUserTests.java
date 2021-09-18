package com.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProxyUserTests {
	
	private ProxyUser proxyUser;
	
	@Test
	void getterSetterTest() {
		
		proxyUser = new ProxyUser();
		proxyUser.setId("id");
	
		assertEquals("id", proxyUser.getId());
	}
	
	@Test
	void equalsTest() {
		
		proxyUser = new ProxyUser();
		proxyUser.setId("id");
		
		ProxyUser proxyUser2 = new ProxyUser();
		proxyUser2.setId("id2");
		
		assertEquals(false, proxyUser.equals(proxyUser2));
		
		proxyUser2.setId("id");
		
		assertEquals(true, proxyUser.equals(proxyUser2));
	}	
	
	@Test
	void toStringTest() {
		
		proxyUser = new ProxyUser();
		proxyUser.setId("id");
		
		assertEquals("ProxyUser [id=" + proxyUser.getId() + "]" , proxyUser.toString());
	}
}
