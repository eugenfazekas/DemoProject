package com.model;

import java.util.Random;

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
	
	String generateCode() {	
	  Random rnd = new Random();
	  int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}
	@Override
	public String toString() {
		return "OneTimePassword [id=" + id + ", email=" + email + ", password=" + password + "]";
	}	
}
