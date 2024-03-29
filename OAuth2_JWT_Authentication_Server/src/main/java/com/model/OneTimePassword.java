package com.model;

import java.util.Objects;

public class OneTimePassword {

	private String id;
	private String email;
	private String password;
	
	
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

	@Override
	public String toString() {
		return "OneTimePassword [id=" + id + ", email=" + email + ", password=" + password + "]";
	}
	
	 @Override
	 public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneTimePassword oneTimePassword = (OneTimePassword) o;
        return Objects.equals(id, oneTimePassword.id) &&
        	   Objects.equals(email, oneTimePassword.email);
    }
}
