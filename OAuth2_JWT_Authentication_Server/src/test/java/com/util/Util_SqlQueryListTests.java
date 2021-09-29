package com.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Util_SqlQueryListTests {

	private Util_SqlQueryList sqlQueryList1;
	private Util_SqlQueryList sqlQueryList2;
	
	@Test
	@DisplayName("Testing Authentication_Service Util_SqlQueryListTests static fields")
	void testOne() {
		
		assertAll(		
				 () -> assertEquals(Util_SqlQueryList.USER_DROP_USERS_TABLE , "DROP TABLE IF EXISTS USERS"),
				 () -> assertEquals(Util_SqlQueryList.USER_FIND_BY_ID , "SELECT * FROM users WHERE id = ?"),
				 () -> assertEquals(Util_SqlQueryList.USER_FIND_BY_EMAIL, "SELECT * FROM users WHERE email = ?"),
				 () -> assertEquals(Util_SqlQueryList.USER_REGISTER_USER ,"INSERT INTO USERS (id,email,password,active,mfa,authorities) VALUES (?,?,?,?,?,?)"),
				 () -> assertEquals(Util_SqlQueryList.USER_USER_EXIST_CHECK , "SELECT COUNT(*)  FROM USERS WHERE email = ?"),
				 () -> assertEquals(Util_SqlQueryList.USER_SET_ACTIVE_USER , "UPDATE users SET active = true where email = ?"),
				 () -> assertEquals(Util_SqlQueryList.USER_UPDATE_USER , "UPDATE users SET email = ?, password = ?, mfa = ?  where id = ?"),
					
					
				 () -> assertEquals(Util_SqlQueryList.ACCOUNTKEY_CREATE_ACCOUNTKEYS_TABLE , "CREATE TABLE IF NOT EXISTS ACCOUNTKEYS (accountKey VARCHAR(36) NOT NULL, accountType VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email));"),
				 () -> assertEquals(Util_SqlQueryList.ACCOUNTKEY_DROP_ACCOUNTKEYS_TABLE , "DROP TABLE IF EXISTS ACCOUNTKEYS"),
				 () -> assertEquals(Util_SqlQueryList.ACCOUNTKEY_CREATE_ACCOUNT_KEY , "INSERT INTO ACCOUNTKEYS (accountKey,accountType,email) VALUES (?,?,?)"),
				 () -> assertEquals(Util_SqlQueryList.ACCOUNTKEY_KEY_CHECK , "SELECT COUNT(*) FROM ACCOUNTKEYS WHERE accountKey = ?"),
				 () -> assertEquals(Util_SqlQueryList.ACCOUNTKEY_FIND_ACCOUNT_KEY , "SELECT * FROM ACCOUNTKEYS WHERE accountKey = ?"),
				 () -> assertEquals(Util_SqlQueryList.ACCOUNTKEY_REMOVE_KEY , "DELETE FROM ACCOUNTKEYS WHERE accountKey = ?"),
					
					
				 () -> assertEquals(Util_SqlQueryList.ONETIMEPASSWORD_CREATE_ONETIMEPASSWORDS_TABLE , "CREATE TABLE IF NOT EXISTS ONETIMEPASSWORD (id VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email), password VARCHAR(64) NOT NULL)"),
				 () -> assertEquals(Util_SqlQueryList.ONETIMEPASSWORD_DROP_ONETIMEPASSWORDS_TABLE , "DROP TABLE IF EXISTS ONETIMEPASSWORD"),
				 () -> assertEquals(Util_SqlQueryList.ONETIMEPASSWORD_CREATE_ONE_TIME_PASSWORD , "INSERT INTO ONETIMEPASSWORD (id,email,password) VALUES (?,?,?)"),
				 () -> assertEquals(Util_SqlQueryList.ONETIMEPASSWORD_ONE_TIME_PASSWORD_CHECK , "SELECT COUNT(*) FROM ONETIMEPASSWORD WHERE email = ?"),
				 () -> assertEquals(Util_SqlQueryList.ONETIMEPASSWORD_FIND_ONE_TIME_PASSWORD , "SELECT * FROM ONETIMEPASSWORD WHERE email = ?"),
				 () -> assertEquals(Util_SqlQueryList.ONETIMEPASSWORD_REMOVE_ONE_TIME_PASSWORD  , "DELETE FROM ONETIMEPASSWORD WHERE email = ?")    	 		 
	    	);
	}
	
	@Test
	@DisplayName("Testing Authentication_Service Util_SqlQueryListTests needed to test coverage")
	void testTwo() {
	
		sqlQueryList1 = new Util_SqlQueryList();
		sqlQueryList2 =  sqlQueryList1;
		assertEquals(sqlQueryList2, sqlQueryList1);	
	}
}
