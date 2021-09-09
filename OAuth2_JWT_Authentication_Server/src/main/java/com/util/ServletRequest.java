package com.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServletRequest {
	
	@Autowired
	private HttpServletRequest request;
		
	public String getEmailHeader() {
		return request.getHeader("email");
	}
}
