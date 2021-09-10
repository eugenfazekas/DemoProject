package com.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Util {

	public String UUID_generator() {
		return UUID.randomUUID().toString();
	}
}
