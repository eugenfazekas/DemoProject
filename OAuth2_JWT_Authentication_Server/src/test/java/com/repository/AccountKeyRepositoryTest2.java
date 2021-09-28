package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import com.model.AccountKey;
import com.util.Util_JdbcTemplateAccountkey;

@SpringBootTest
public class AccountKeyRepositoryTest2 {
	
	@Autowired
	private AccountKeyRepository accountKeyRepository;

	@MockBean
	private Util_JdbcTemplateAccountkey jdbc;
	
	private AccountKey accountKey;
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository dropAccountKeyTable function when dropping DataAccessException")
	void a1() {
		

		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).dropAccountKeyTable();

		assertEquals(null, accountKeyRepository.dropAccountKeyTable());	
	}

	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository createAccountKeyTable function when dropping DataAccessException")
	void a2() {
		
		doThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; }).when(jdbc).createAccountKeyTable();

		assertEquals(null, accountKeyRepository.createAccountKeyTable());
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository createAccountKey function when dropping DataAccessException")
	void a3() {

		accountKey = new AccountKey();
		accountKey.setKey("key1");
		accountKey.setAccountType("user");
		accountKey.setEmail("eu@fa.hu");
		
		when(jdbc.createAccountKey(accountKey)).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });

		assertEquals(null, accountKeyRepository.createAccountKey(accountKey));		
	}

	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository keyCheck function when dropping DataAccessException")
	void a4() {

		String  key = "key";
		
		when(jdbc.keyCheck(key)).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });
		
		assertEquals(null, accountKeyRepository.keyCheck(key));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository findAccountKey function when dropping DataAccessException")
	void a5() {

		String  key = "key3";
		
		when(jdbc.findAccountKey(key)).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });

		assertEquals(null, accountKeyRepository.findAccountKey(key));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyRepository removeKey function when dropping DataAccessException")
	void a6() {

		String  key = "key3";
		
		when(jdbc.removeKey(key)).thenThrow(new DataAccessException("..."){  private static final long serialVersionUID = 1L; });
		
		assertEquals(null, accountKeyRepository.removeKey(key));
		}	
}
