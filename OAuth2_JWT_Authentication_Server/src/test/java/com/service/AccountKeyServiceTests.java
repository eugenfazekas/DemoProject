package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.model.AccountKey;
import com.repository.AccountKeyRepository;

@SpringBootTest
public class AccountKeyServiceTests {
	
	@Autowired
	private AccountKeyService accountKeyService;
	
	@MockBean
	private AccountKeyRepository accountKeyRepository;

	@Test
	void createAccountKeyTableTest() {
		when(accountKeyRepository.createAccountKeyTable()).thenReturn("AccountKey table created");	
		assertEquals("AccountKey table created", accountKeyService.createAccountKeyTable());
	}
	
	@Test
	void dropAccountKeyTableTest() {
		when(accountKeyRepository.dropAccountKeyTable()).thenReturn("AccountKey table dropped");	
		assertEquals("AccountKey table dropped", accountKeyService.dropAccountKeyTable());
	}

	@Test
	void createAccountKeyTest1() {
		
		AccountKey account = new AccountKey();
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.createAccountKey(null); });
		
		account.setKey("key");
		account.setAccountType("user");
		account.setEmail("eu@fa.hu");
		
		when(accountKeyRepository.createAccountKey(account)).thenReturn("New AccountKey Created");	
		assertEquals("New AccountKey Created", accountKeyService.createAccountKey(account));
	}
	
	@Test
	void createAccountKeyTest2() {
		
		AccountKey account = new AccountKey();
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.createAccountKey(null); });
		
		account.setKey("key");
		account.setAccountType("user");
		account.setEmail("eu@fa.hu");
		
		when(accountKeyRepository.createAccountKey(account)).thenReturn(null);	
		assertEquals("AccountKey has not been created", accountKeyService.createAccountKey(account));
	}
	
	@Test
	void keyCheckTest1() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.keyCheck(null); });
		
		when(accountKeyRepository.keyCheck("key")).thenReturn(0);
		assertEquals(false, accountKeyService.keyCheck("key"));
	}
	
	@Test
	void keyCheckTest2() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.keyCheck(""); });
		
		when(accountKeyRepository.keyCheck("key")).thenReturn(1);
		assertEquals(true, accountKeyService.keyCheck("key"));		
	}
	
	@Test
	void removeKeyTest1() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.removeKey(null); });
		
		when(accountKeyRepository.removeKey("key")).thenReturn("AccountKey Successfully removed");
		assertEquals("AccountKey Successfully removed", accountKeyService.removeKey("key"));	
	}
	
	@Test
	void removeKeyTest2() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.removeKey(null); });
		
		when(accountKeyRepository.removeKey("key")).thenReturn(null);
		assertEquals("AccountKey has not been removed", accountKeyService.removeKey("key"));	
	}
	
	void findAccountKeyTest1() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.findAccountKey(null); });
		
		when(accountKeyRepository.findAccountKey("key")).thenReturn(new AccountKey());
		assertEquals(new AccountKey(), accountKeyService.findAccountKey("key"));	
	}
	
	@Test
	void findAccountKeyTest2() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.findAccountKey(null); });
		
		when(accountKeyRepository.findAccountKey("key")).thenReturn(null);
		assertEquals(null, accountKeyService.findAccountKey("key"));	
	}
}
