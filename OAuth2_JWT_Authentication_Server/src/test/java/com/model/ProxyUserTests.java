package com.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProxyUserTests {
	
	private ProxyUser proxyUser1;
	
	private ProxyUser proxyUser2;
	
	@Test
	void getterSetterTest() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
	
		assertEquals("id", proxyUser1.getId());
	}
	
	@Test
	void equalsTest1() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		ProxyUser proxyUser2 = new ProxyUser();
		proxyUser2.setId("id2");
		
		assertEquals(false, proxyUser1.equals(proxyUser2));
	
	}
	
	@Test
	void equalsTest2() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		ProxyUser proxyUser2 = new ProxyUser();
		
		assertEquals(false, proxyUser1.equals(proxyUser2));	
	}
	
	@Test
	void equalsTest3() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		ProxyUser proxyUser2 = new ProxyUser();
		proxyUser2.setId("id");
		
		assertEquals(true, proxyUser1.equals(proxyUser2));	
	}
	
	@Test
	void equalsTest4() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		ProxyUser proxyUser2 = proxyUser1;
		
		assertEquals(true, proxyUser1.equals(proxyUser2));	
	}
	
	@Test
	void equalsTest5() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		DummyTestModel model = new DummyTestModel();
		model.setId("id");
		
		assertEquals(false, proxyUser1.equals(proxyUser2));	
	}

	@Test
	void toStringTest() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		assertEquals("ProxyUser [id=" + proxyUser1.getId() + "]" , proxyUser1.toString());
	}
}
