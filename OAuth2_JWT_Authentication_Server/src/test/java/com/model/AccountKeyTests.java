package com.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountKeyTests {
	
	private AccountKey accountKey;
	
	private DummyTestModel dummyTestModel;
	
	@Test
	void constructorTest () {
		
		accountKey = new AccountKey();
		accountKey.setKey("key");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		assertEquals(accountKey, new AccountKey("key","user","eu@fa.hu"));
		
	}
	
	@Test
	void getterSetterTest() {
		
		accountKey = new AccountKey();
		accountKey.setKey("key");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		assertAll(		
	    		 () -> assertEquals("key", accountKey.getKey()),
	    		 () -> assertEquals("user",accountKey.getAccountType()),
	    		 () -> assertEquals("eu@fa.hu",accountKey.getEmail())
	    		);		
	}
	
	@Test
	void equalsTest1() {
		
		accountKey = new AccountKey();
		accountKey.setKey("key");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		AccountKey accountKey2 = new AccountKey();
		accountKey2.setKey("key");	
		accountKey2.setEmail("eu@fa.hu");
		accountKey2.setAccountType("user");
		
		assertEquals(true, accountKey.equals(accountKey2));
	}
	
	@Test
	void equalsTest2() {
		
		accountKey = new AccountKey();
		accountKey.setKey("key");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		AccountKey accountKey2 = new AccountKey();
		
		assertEquals(false, accountKey.equals(accountKey2));
		
		accountKey2.setEmail("eu@fa.hu");		
		assertEquals(false, accountKey.equals(accountKey2));
				
		accountKey2.setKey("key");
		assertEquals(true, accountKey.equals(accountKey2));
		
		accountKey2.setAccountType("user");
		assertEquals(true, accountKey.equals(accountKey2));
	}
	
	@Test
	void equalsTest3() {
	
	dummyTestModel = new DummyTestModel();	
	accountKey = new AccountKey();
	
		assertEquals(false, accountKey.equals(dummyTestModel));

	}
	
	@Test
	void toStringTest() {
		
		accountKey = new AccountKey();
		accountKey.setKey("key");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		assertEquals( "AccountKey [accountType=" + accountKey.getAccountType() + ", key=" + accountKey.getKey() + ", email=" + accountKey.getEmail() + "]" , accountKey.toString());
	}

}
