package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountKeyTests {
	
	private AccountKey accountKey1;
	private AccountKey accountKey2;	
	private DummyTestModel dummyTestModel;
	
	@Test
	void constructorTest () {
		
		accountKey1 = new AccountKey();
		accountKey1.setKey("key");
		accountKey1.setAccountType("user");
		accountKey1.setEmail("eu@fa.hu");
		
		assertEquals(accountKey1, new AccountKey("key","user","eu@fa.hu"));		
	}
	
	@Test
	void getterSetterTest() {
		
		accountKey1 = new AccountKey();
		accountKey1.setKey("key");
		accountKey1.setAccountType("user");
		accountKey1.setEmail("eu@fa.hu");
		
		assertAll(		
	    		 () -> assertEquals("key", accountKey1.getKey()),
	    		 () -> assertEquals("user",accountKey1.getAccountType()),
	    		 () -> assertEquals("eu@fa.hu",accountKey1.getEmail())
	    		);		
	}
	
	@Test
	void equalTest1() {
		
		accountKey1 = new AccountKey();
		accountKey1.setKey("key");
		accountKey1.setEmail("eu@fa.hu");
		accountKey1.setAccountType("user");
		
		accountKey2 = new AccountKey();
		accountKey2 = accountKey1;
		
		assertEquals(true, accountKey1.equals(accountKey2));
		
	}
	
	@Test
	void equalTest2() {
		
		accountKey1 = new AccountKey();
		accountKey1.setKey("key");
		accountKey1.setEmail("eu@fa.hu");
		accountKey1.setAccountType("user");
		
		accountKey2 = new AccountKey();
		
		assertEquals(false, accountKey1.equals(accountKey2));		
	}
	
	@Test
	void equalTest3() {
		
		accountKey1 = new AccountKey();
		accountKey1.setKey("key");
		accountKey1.setEmail("eu@fa.hu");
		accountKey1.setAccountType("user");
		
		dummyTestModel = new DummyTestModel();
		dummyTestModel.setKey("key");
		dummyTestModel.setEmail("eu@fa.hu");
		dummyTestModel.setAccountType("user");
		
		assertEquals(false, accountKey1.equals(dummyTestModel));		
	}
	
	@Test
	void equalTest4() {
		
		accountKey1 = new AccountKey();
		accountKey1.setKey("key");
		accountKey1.setEmail("eu@fa.hu");
		
		accountKey2 = new AccountKey();
		accountKey2.setKey("key");
		accountKey2.setEmail("eu@fa.hu");
		
		assertEquals(true, accountKey1.equals(accountKey2));		
	}
	
	@Test
	void toStringTest() {
		
		accountKey1 = new AccountKey();
		accountKey1.setKey("key");
		accountKey1.setAccountType("user");
		accountKey1.setEmail("eu@fa.hu");
		
		assertEquals( "AccountKey [accountType=" + accountKey1.getAccountType() + ", key=" + accountKey1.getKey() + ", email=" + accountKey1.getEmail() + "]" , accountKey1.toString());
	}
}
