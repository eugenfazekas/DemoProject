package com.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Value("${emailservice.subjecten}")
	  private String subjecten;
	
	@Value("${emailservice.text1en}")
	  private String text1en;
	
	@Value("${emailservice.text2en}")
	  private String text2en;
	
	@Value("${emailservice.text3en}")
	  private String text3en;
	
	@Value("${emailservice.validationLink}")
	  private String validationLink;

	
	private JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
public String sendMessageen(String email ,String fullName,String key)  {
	MimeMessage msg = javaMailSender.createMimeMessage();
    MimeMessageHelper messageen;
	try {
		messageen = new MimeMessageHelper(msg, false);
		messageen.setTo(email);
		messageen.setSubject(subjecten);
		messageen.setText("<div style='width:50%; height: auto; background-color:#007bff; color: white'>\r\n" + 
							"	<h1 style='margin:3%'>"+subjecten+"</h1>\r\n" + 
							"	<h3 style='margin-left:3%,margin-top:2%; '>" + text1en+" " + fullName +"</h3>	\r\n" + 
								"	<p  style='margin-left:3%; margin-top:2%; color: white;'> " + text2en +  "<a style='color: white' >" +email+ "</a> </p>	\r\n" + 
								"	<p  style='margin-left:3%; margin-top:2%; color: white; '> "+text3en+"</p>\r\n" + 
								"	<p  style='margin-left:3%; margin-top:2%; color: white;'>" +
								"<a style='color: white' href="+validationLink + key+">"+validationLink + key+"  </a>" +
							"</div>",true);
		javaMailSender.send(msg);
	} catch (MessagingException e) {

		e.printStackTrace();
	}
			return "message send!";
		}


	public String getSubjecten() {
		return subjecten;
	}
	
	public void setSubjecten(String subjecten) {
		this.subjecten = subjecten;
	}
	
	public String getText1en() {
		return text1en;
	}
	
	public void setText1en(String text1en) {
		this.text1en = text1en;
	}
	
	public String getText2en() {
		return text2en;
	}
	
	public void setText2en(String text2en) {
		this.text2en = text2en;
	}
	
	public String getText3en() {
		return text3en;
	}
	
	public void setText3en(String text3en) {
		this.text3en = text3en;
	}
	
	public String getValidationLink() {
		return validationLink;
	}
	
	public void setValidationLink(String validationLink) {
		this.validationLink = validationLink;
	}
}

