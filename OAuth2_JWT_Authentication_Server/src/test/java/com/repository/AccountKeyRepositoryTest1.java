package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.AccountKey;

@SpringBootTest
public class AccountKeyRepositoryTest1 {
	
	@Autowired
	private AccountKeyRepository accountKeyRepository;

	
	private AccountKey accountKey;
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository dropAccountKeyTable function")
	void a1() {

		assertEquals("AccountKey table dropped", accountKeyRepository.dropAccountKeyTable());
	}

	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository createAccountKeyTable function")
	void a2() {

		assertEquals("AccountKey table created", accountKeyRepository.createAccountKeyTable());
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository createAccountKey function")
	void a3() {

		accountKey = new AccountKey();
		accountKey.setKey("key1");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		assertEquals("New AccountKey Created", accountKeyRepository.createAccountKey(accountKey));		
	}

	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository keyCheck function; counting inserted keys with this key")
	void a4() {
		
		assertEquals(1, accountKeyRepository.keyCheck("key1"));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository keyCheck function; counting inserted keys with this key")
	void a5() {

		assertEquals(0, accountKeyRepository.keyCheck("key2"));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository findAccountKey function with valid inserted key (getting DAO object)")
	void a6() {

		accountKey = new AccountKey();
		accountKey.setKey("key1");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		assertEquals(accountKey, accountKeyRepository.findAccountKey("key1"));
		}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository findAccountKey function with invalid, not inserted key")
	void a7() {

			assertEquals(null, accountKeyRepository.findAccountKey("key2"));
		}

	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository removeKey function; removing inserted key")
	void a8() {

		assertEquals("AccountKey Deleted", accountKeyRepository.removeKey("key1"));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository verifying  if key was removed")
	void a9() {

		assertEquals(0, accountKeyRepository.keyCheck("key1"));
	}
}
