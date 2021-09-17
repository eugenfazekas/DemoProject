package com.model;

import java.util.Objects;

public class ProxyUser {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	
	 @Override
	 public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProxyUser user = (ProxyUser) o;
        return Objects.equals(id, user.id);  		
    }

	@Override
	public String toString() {
		return "ProxyUser [id=" + id + "]";
	}	 
}
