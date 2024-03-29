package com.model;

import java.util.List;
import java.util.Objects;

public class User {
	
	private String id;
	private String email;
	private String password;
	private boolean active;
	private boolean mfa;
	private List<String> authorities;

	public User() {}		

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isMfa() {
		return mfa;
	}

	public void setMfa(boolean mfa) {
		this.mfa = mfa;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", active=" + active + ", mfa=" + mfa
				+ ", authorities=" + authorities + "]";
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        User user = (User) o;
	        return mfa == user.mfa &&
	        		Objects.equals(id, user.id) &&
	                Objects.equals(email, user.email) ;
	    }
}
