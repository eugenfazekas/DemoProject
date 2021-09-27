package com.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProxyUserTests {
	
	private ProxyUser proxyUser1;
	
	private ProxyUser proxyUser2;
	
	@Test
	@DisplayName("Testing Authentication_Service ProxyUser getter-setter tests")
	void getterSetterTest() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
	
		assertEquals("id", proxyUser1.getId());
	}
	
	@Test
	@DisplayName("Testing Authentication_Service ProxyUser equals function with different instances and value")
	void equalsTest1() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		proxyUser2 = new ProxyUser();
		proxyUser2.setId("id2");
		
		assertEquals(false, proxyUser1.equals(proxyUser2));
	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service ProxyUser equals function with null object")
	void equalsTest2() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
	    proxyUser2 = new ProxyUser();
		
		assertEquals(false, proxyUser1.equals(proxyUser2));	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service ProxyUser equals function with same class and values but other instances")
	void equalsTest3() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		proxyUser2 = new ProxyUser();
		proxyUser2.setId("id");
		
		assertEquals(true, proxyUser1.equals(proxyUser2));	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service ProxyUser equals function with cloned object")
	void equalsTest4() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		proxyUser2 = proxyUser1;
		
		assertEquals(true, proxyUser1.equals(proxyUser2));	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service ProxyUser equals function with other class object")
	void equalsTest5() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		DummyTestModel model = new DummyTestModel();
		model.setId("id");
		
		assertEquals(false, proxyUser1.equals(proxyUser2));	
	}

	@Test
	@DisplayName("Testing Authentication_Service ProxyUser toString function")
	void toStringTest() {
		
		proxyUser1 = new ProxyUser();
		proxyUser1.setId("id");
		
		assertEquals("ProxyUser [id=" + proxyUser1.getId() + "]" , proxyUser1.toString());
	}
}
