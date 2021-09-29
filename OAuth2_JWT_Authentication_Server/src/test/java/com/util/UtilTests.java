package com.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilTests {

	@Autowired
	private Util util;
	
	@Test
	@DisplayName("Testing Authentication_Service UtilTests UUID_generatorTest function")
	void UUID_generatorTest() {
		String uuid = util.UUID_generator();
		assertEquals(true, uuid.length() == 36);		
	}
}
