package com.demoproject.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
		
	@Value("${EmailService.validation}")   
	private String EmailValidation ;
	
	@Value("${EmailService.adminValidation}") 
	private String AdminEmailValidation ;
	
	@Value("${EmailService.subjecten}")
	private String Emailsubjecten ;
	
	@Value("${EmailService.text1en}")
	private String Emailtext1en ;
	
	@Value("${EmailService.text2en}")
	private String Emailtext2en ;
	
	@Value("${EmailService.text3en}")
	private String Emailtext3en ;
	
	@Value("${EmailService.subjecthu}")
	private String Emailsubjecthu ;
	
	@Value("${EmailService.text1hu}")
	private String Emailtext1hu ;
	
	@Value("${EmailService.text2hu}")
	private String Emailtext2hu ;
	
	@Value("${EmailService.text3hu}")
	private String Emailtext3hu ;
	
	@Value("${EmailService.subjectro}")
	private String Emailsubjectro ;
	
	@Value("${EmailService.text1ro}")
	private String Emailtext1ro ;
	
	@Value("${EmailService.text2ro}")
	private String Emailtext2ro ;
	
	@Value("${EmailService.text3ro}")
	private String Emailtext3ro ;
	
	@Value("${EmailService.adminKeyEmailAddress}")
	private String adminKeyEmailAddress;
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;

	private JavaMailSender javaMailSender;

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
public void sendMessageen(String email ,String firstName, String lastName,String key) {
		
		SimpleMailMessage messageen = null;
		
		try {
			messageen = new SimpleMailMessage();
			messageen.setFrom(MESSAGE_FROM);
			messageen.setTo(email);
			messageen.setSubject(Emailsubjecten);
			messageen.setText(Emailtext1en+" "+ lastName+" " + firstName + "! \n \n" +Emailtext2en
				+",\n"	+Emailtext3en+":\n" + EmailValidation+key);
			javaMailSender.send(messageen);
			
		} catch (Exception e) {
			log.error("Hiba az e-mail kuldeskor" + email + " " + e);
		}
	}
	
public void sendMessagehu(String email ,String firstName, String lastName,String key) {
	
	SimpleMailMessage messagehu = null;
	
	try {
		messagehu = new SimpleMailMessage();
		messagehu.setFrom(MESSAGE_FROM);
		messagehu.setTo(email);
		messagehu.setSubject(Emailsubjecthu);
		messagehu.setText(Emailtext1hu+" "+ lastName+" " + firstName + "! \n \n" +Emailtext2hu
			+",\n"	+Emailtext3hu+":\n" + EmailValidation+key);
		javaMailSender.send(messagehu);
		
	} catch (Exception e) {
		log.error("Hiba az e-mail kuldeskor" + email + " " + e);
	}
}

public void sendMessagero(String email ,String firstName, String lastName,String key) {
	
	SimpleMailMessage messagero = null;
	
	try {
		messagero = new SimpleMailMessage();
		messagero.setFrom(MESSAGE_FROM);
		messagero.setTo(email);
		messagero.setSubject(Emailsubjectro);
		messagero.setText(Emailtext1ro+" "+ lastName+" " + firstName + "! \n \n" +Emailtext2ro
			+",\n"	+Emailtext3ro+":\n" + EmailValidation+key);
		javaMailSender.send(messagero);
		
	} catch (Exception e) {
		log.error("Hiba az e-mail kuldeskor" + email + " " + e);
	}
}
public void sendAdminCode(String key) {
	
SimpleMailMessage messageadmin = null;
	
	try {
		messageadmin = new SimpleMailMessage();
		messageadmin.setFrom(MESSAGE_FROM);
		messageadmin.setTo(adminKeyEmailAddress);
		messageadmin.setSubject("AdminCode");
		messageadmin.setText(AdminEmailValidation+key);
		javaMailSender.send(messageadmin);
		
	} catch (Exception e) {
		log.error("Hiba az e-mail kuldeskor az admin koddal" + key+ " " + e);
	}
}
}
