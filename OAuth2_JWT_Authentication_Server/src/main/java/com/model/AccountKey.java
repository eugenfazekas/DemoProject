package com.model;

import java.util.Objects;

public class AccountKey {

	private String accountType;
	private String key;
	private String email;
	
	public AccountKey() {
	}
	
	public AccountKey( String key, String accountType, String email) {
		this.accountType = accountType;
		this.key = key;
		this.email = email;
	}

	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	 @Override
	public String toString() {
		return "AccountKey [accountType=" + accountType + ", key=" + key + ", email=" + email + "]";
	}

	@Override
	 public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountKey accountKey = (AccountKey) o;
        return Objects.equals(key, accountKey.key) &&
        	   Objects.equals(email, accountKey.email);
    }
}
