package com.model;

import java.util.Objects;

public class UserUpdate {

	private String id;
	private String email;
	private String oldPassword;
	private String password;
	private boolean mfa;
	
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
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isMfa() {
		return mfa;
	}
	public void setMfa(boolean mfa) {
		this.mfa = mfa;
	}
	
	@Override
	public String toString() {
		return "UserUpdate [id=" + id + ", email=" + email + ", oldPassword=" + oldPassword + ", password=" + password
				+ ", mfa=" + mfa + "]";
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        UserUpdate user = (UserUpdate) o;
	        return Objects.equals(id, user.id) &&
	                Objects.equals(email, user.email) ;     		
	    }
}
