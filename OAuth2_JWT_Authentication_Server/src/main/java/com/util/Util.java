package com.util;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

@Component
public class Util {
	
	public String UUID_generator() {
		return UUID.randomUUID().toString();
	}
	
	public RedirectView redirectView(String keycheckResponse) {

		 RedirectView redirectView = new RedirectView();
		 redirectView.setUrl("https://example.com");
		 
		 if(keycheckResponse == "User Successfully activated!")
			 return redirectView;
		 
		 return null;
	}
}
