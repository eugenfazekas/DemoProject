package com.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;


@SpringBootTest
public class UserControllerConfigTest {

	@Autowired
	private ControllerConfig controllerConfig;
	
	@Test
	@DisplayName("Testing Authentication_Service accountKeyService dropAccountKeyTable function")
	void dropAccountKeyTableTest() {
		String msg = "msg";
		Throwable cause = null; 
		HttpInputMessage httpInputMessage = null;
		HttpMessageNotReadableException exception = new HttpMessageNotReadableException(msg,cause,httpInputMessage);
		
		Throwable throwable1 = assertThrows(HttpMessageNotReadableException.class, () -> controllerConfig.handle(exception));	
		assertEquals(msg, throwable1.getMessage());
	}
}
                        