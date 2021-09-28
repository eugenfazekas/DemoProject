package com.util;

public class Util_SqlQueryList {

	public final static String USER_CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS USERS (id VARCHAR(36) PRIMARY KEY, email VARCHAR(64) NOT NULL, UNIQUE  (email), password VARCHAR(64) NOT NULL, active BOOLEAN, mfa BOOLEAN, authorities VARCHAR(64) NOT NULL);";
	public final static String USER_DROP_USERS_TABLE = "DROP TABLE IF EXISTS USERS";
	public final static String USER_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
	public final static String USER_FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
	public final static String USER_REGISTER_USER = "INSERT INTO USERS (id,email,password,active,mfa,authorities) VALUES (?,?,?,?,?,?)";
	public final static String USER_USER_EXIST_CHECK = "SELECT COUNT(*)  FROM USERS WHERE email = ?";
	public final static String USER_SET_ACTIVE_USER = "UPDATE users SET active = true where email = ?";
	public final static String USER_UPDATE_USER = "UPDATE users SET email = ?, password = ?, mfa = ?  where id = ?";
	
	
	public final static String ACCOUNTKEY_CREATE_ACCOUNTKEYS_TABLE = "CREATE TABLE IF NOT EXISTS ACCOUNTKEYS (accountKey VARCHAR(36) NOT NULL, accountType VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email));";
	public final static String ACCOUNTKEY_DROP_ACCOUNTKEYS_TABLE = "DROP TABLE IF EXISTS ACCOUNTKEYS";
	public final static String ACCOUNTKEY_CREATE_ACCOUNT_KEY = "INSERT INTO ACCOUNTKEYS (accountKey,accountType,email) VALUES (?,?,?)";
	public final static String ACCOUNTKEY_KEY_CHECK = "SELECT COUNT(*) FROM ACCOUNTKEYS WHERE accountKey = ?";
	public final static String ACCOUNTKEY_FIND_ACCOUNT_KEY = "SELECT * FROM ACCOUNTKEYS WHERE accountKey = ?";
	public final static String ACCOUNTKEY_REMOVE_KEY = "DELETE FROM ACCOUNTKEYS WHERE accountKey = ?";
	
	
	public final static String ONETIMEPASSWORD_CREATE_ONETIMEPASSWORDS_TABLE = "CREATE TABLE IF NOT EXISTS ONETIMEPASSWORD (id VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email), password VARCHAR(64) NOT NULL)";
	public final static String ONETIMEPASSWORD_DROP_ONETIMEPASSWORDS_TABLE = "DROP TABLE IF EXISTS ONETIMEPASSWORD";
	public final static String ONETIMEPASSWORD_CREATE_ONE_TIME_PASSWORD = "INSERT INTO ONETIMEPASSWORD (id,email,password) VALUES (?,?,?)";
	public final static String ONETIMEPASSWORD_ONE_TIME_PASSWORD_CHECK = "SELECT COUNT(*) FROM ONETIMEPASSWORD WHERE email = ?";
	public final static String ONETIMEPASSWORD_FIND_ONE_TIME_PASSWORD = "SELECT * FROM ONETIMEPASSWORD WHERE email = ?";
	public final static String ONETIMEPASSWORD_REMOVE_ONE_TIME_PASSWORD = "DELETE FROM ONETIMEPASSWORD WHERE email = ?";

}
