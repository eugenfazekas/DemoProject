package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.AccountKey;

@SpringBootTest
public class AccountKeyRepositoryTest {
	
	@Autowired
	private AccountKeyRepository accountKeyRepository;

	
	private AccountKey accountKey;
	
	@Test
	void a1() {

		//dropAccountKeyTableTest
		assertEquals("AccountKey table dropped", accountKeyRepository.dropAccountKeyTable());
	}

	@Test
	void a2() {

		//createAccountKeyTableTest
		assertEquals("AccountKey table created", accountKeyRepository.createAccountKeyTable());
	}
	
	@Test
	void a3() {
	
		//createAccountKeyTest
		
		accountKey = new AccountKey();
		accountKey.setKey("key1");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		assertEquals("New AccountKey Created", accountKeyRepository.createAccountKey(accountKey));
		
	}

	@Test
	void a4() {

		//keyCheckTest
		
		assertEquals(1, accountKeyRepository.keyCheck("key1"));
	}
	@Test
	void a5() {

		//keyCheckTest
		
		assertEquals(0, accountKeyRepository.keyCheck("key2"));
	}
	
	@Test
	void a6() {

		//findAccountKeyTest
		accountKey = new AccountKey();
		accountKey.setKey("key1");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		assertEquals(accountKey, accountKeyRepository.findAccountKey("key1"));
		}
	
	@Test
	void a7() {

		//findAccountKeyTest
		
		assertEquals(null, accountKeyRepository.findAccountKey("key2"));
		}

	@Test
	void a8() {

		//removeKeyTest
		
		assertEquals("New AccountKey Deleted", accountKeyRepository.removeKey("key1"));
	}
	
	@Test
	void a9() {

		//verify removeKeyTest
		
		assertEquals(0, accountKeyRepository.keyCheck("key1"));
	}
}
