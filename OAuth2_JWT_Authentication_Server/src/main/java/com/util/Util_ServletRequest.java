package com.util;

import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Util_ServletRequest {

	@Autowired
	private HttpServletRequest request;
	
	public String getUsernameHeader() {
		return request.getHeader("username");
	}
	
	public String getPasswordHeader() {
		return request.getHeader("password");
	}

}
