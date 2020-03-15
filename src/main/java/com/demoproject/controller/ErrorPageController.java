package com.demoproject.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {
	
	private Calendar cal = Calendar.getInstance();
	
    Date date= cal.getTime();
    
    DateFormat dateFormat = new SimpleDateFormat(" dd MMM yyyy 'at' HH:mm:ss");

    String formattedDate=dateFormat.format(date);
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
	    Object statuscode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	
	   
	
	    model.addAttribute("status",statuscode.toString());
	
	    return "detailederror";
	}

    @Override
    public String getErrorPath() {
        return "/error";
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
}