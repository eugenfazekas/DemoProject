package com.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private String subjecten,text1en,text2en,text3en,ValidationLink;

	private JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {}
	
public void sendMessageen(String email ,String fullName,String key) throws MessagingException, IOException  {
	
	MimeMessage msg = javaMailSender.createMimeMessage();
    MimeMessageHelper messageen = new MimeMessageHelper(msg, false);
		
			messageen.setTo(email);
			messageen.setSubject(subjecten);
			messageen.setText("<div style='width:50%; height: auto; background-color:#007bff; color: white'>\r\n" + 
								"	<h1 style='margin:3%'>"+subjecten+"</h1>\r\n" + 
								"	<h3 style='margin-left:3%,margin-top:2%; '>" + text1en+" " + fullName +"</h3>	\r\n" + 
									"	<p  style='margin-left:3%; margin-top:2%; color: white;'> " + text2en +  "<a style='color: white' >" +email+ "</a> </p>	\r\n" + 
									"	<p  style='margin-left:3%; margin-top:2%; color: white; '> "+text3en+"</p>\r\n" + 
									"	<p  style='margin-left:3%; margin-top:2%; color: white;'>" +
									"<a style='color: white' href="+ValidationLink + key+">"+ValidationLink + key+"  </a>" +
								"</div>",true);
			javaMailSender.send(msg);
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
		return ValidationLink;
	}
	
	public void setValidationLink(String validationLink) {
		ValidationLink = validationLink;
	}	
}

