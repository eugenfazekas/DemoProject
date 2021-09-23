package com.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exception.CreateAccountKeyException;
import com.exception.NoUserActivationKeyException;
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
			
		account.setKey("key");
		
		Throwable throwable1 = assertThrows(CreateAccountKeyException.class, () -> accountKeyService.createAccountKey(account));
		assertEquals("Authentication_Server.AccountKeyService.createAccountKey --> accountkey, email, usertype cannot be null!", throwable1.getMessage());
		 
		account.setAccountType("user");
		
		Throwable throwable2 = assertThrows(CreateAccountKeyException.class, () -> accountKeyService.createAccountKey(account));
		assertEquals("Authentication_Server.AccountKeyService.createAccountKey --> accountkey, email, usertype cannot be null!", throwable2.getMessage());
		
		account.setEmail("eu@fa.hu");
		account.setKey(null);
		
		Throwable throwable3 = assertThrows(CreateAccountKeyException.class, () -> accountKeyService.createAccountKey(account));
		assertEquals("Authentication_Server.AccountKeyService.createAccountKey --> accountkey, email, usertype cannot be null!", throwable3.getMessage());
		
	    account.setKey("key");
	    
		when(accountKeyRepository.createAccountKey(account)).thenReturn("New AccountKey Created");	
		assertEquals("New AccountKey Created", accountKeyService.createAccountKey(account));
	}
	
	@Test
	void createAccountKeyTest2() {
		
		AccountKey account = new AccountKey();
		
		account.setEmail("eu@fa.hu");
		Assertions.assertThrows(RuntimeException.class, () -> {  accountKeyService.createAccountKey(account); });
		
		account.setKey("key");
		account.setAccountType("user");
	
		
		when(accountKeyRepository.createAccountKey(account)).thenReturn(null);	
		assertEquals("AccountKey has not been created", accountKeyService.createAccountKey(account));
	}
	
	@Test
	void keyCheckTest1() {
		
		Throwable throwable1 = assertThrows(NoUserActivationKeyException.class, () -> accountKeyService.keyCheck(null));
		assertEquals("Authentication_Server.AccountKeyService.keyCheck --> key cannot be null or empty String!", throwable1.getMessage());
		
		Throwable throwable2 = assertThrows(NoUserActivationKeyException.class, () -> accountKeyService.keyCheck(""));
		assertEquals("Authentication_Server.AccountKeyService.keyCheck --> key cannot be null or empty String!", throwable2.getMessage());
		
		when(accountKeyRepository.keyCheck("key")).thenReturn(0);
		assertEquals(false, accountKeyService.keyCheck("key"));
	}
	
	@Test
	void keyCheckTest2() {
		
		when(accountKeyRepository.keyCheck("key")).thenReturn(1);
		assertEquals(true, accountKeyService.keyCheck("key"));		
	}
	
	@Test
	void removeKeyTest1() {
		
		Throwable throwable1 = assertThrows(NoUserActivationKeyException.class, () -> accountKeyService.removeKey(null));
		assertEquals("Authentication_Server.AccountKeyService.removeKey --> key cannot be null or empty String!", throwable1.getMessage());
		
		Throwable throwable2 = assertThrows(NoUserActivationKeyException.class, () -> accountKeyService.removeKey(""));
		assertEquals("Authentication_Server.AccountKeyService.removeKey --> key cannot be null or empty String!", throwable2.getMessage());
		
		when(accountKeyRepository.removeKey("key")).thenReturn("AccountKey Successfully removed");
		assertEquals("AccountKey Successfully removed", accountKeyService.removeKey("key"));	
	}
	
	@Test
	void removeKeyTest2() {
		
		when(accountKeyRepository.removeKey("key")).thenReturn(null);
		assertEquals("AccountKey has not been removed", accountKeyService.removeKey("key"));	
	}
	
	@Test
	void findAccountKeyTest1() {
		
		Throwable throwable1 = assertThrows(NoUserActivationKeyException.class, () -> accountKeyService.findAccountKey(null));
		assertEquals("Authentication_Server.AccountKeyService.findAccountKey --> key cannot be null or empty String!", throwable1.getMessage());
		
		Throwable throwable2 = assertThrows(NoUserActivationKeyException.class, () -> accountKeyService.findAccountKey(""));
		assertEquals("Authentication_Server.AccountKeyService.findAccountKey --> key cannot be null or empty String!", throwable2.getMessage());

		when(accountKeyRepository.findAccountKey("key")).thenReturn(new AccountKey());
		assertEquals(new AccountKey(), accountKeyService.findAccountKey("key"));	
	}
	
	
	@Test
	void findAccountKeyTest2() {
		
		when(accountKeyRepository.findAccountKey("key")).thenReturn(null);
		assertEquals(null, accountKeyService.findAccountKey("key"));	
	}	
}
