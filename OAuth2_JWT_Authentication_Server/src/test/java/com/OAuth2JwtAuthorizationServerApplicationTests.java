package com;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OAuth2JwtAuthorizationServerApplicationTests {

	@Test
	@DisplayName("Testing Authentication_Service main function")
    public void applicationStarts() {
		OAuth2JwtAuthorizationServerApplication.main(new String[] {});
    }
}
