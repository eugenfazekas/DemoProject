package com.events.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDetailsPrepareTest {

	private UserDetailsPrepare detailsPrepare;
	
	@Test
	@DisplayName("Testing Authentication_Service UserDetailsPrepare getters and setters test1")
	void getterSetterTest1() {
				
		detailsPrepare = new UserDetailsPrepare("id");
		assertEquals("id", detailsPrepare.getId());
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserDetailsPrepare getters and setters test2")
	void getterSetterTest2() {
		
		String uuid = UUID.randomUUID().toString();
		
		detailsPrepare = new UserDetailsPrepare("id");
		detailsPrepare.setId(uuid);
		
		assertEquals(uuid, detailsPrepare.getId());
	}
}
