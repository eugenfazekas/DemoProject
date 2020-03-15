package com.demoproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionGeneral {

	public String exception (Exception ex , Model model) {
		model.addAttribute("status", ex.getClass());
		
		return "detailederror";
	}
	
}
