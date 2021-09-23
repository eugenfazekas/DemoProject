package com.exception;

public class NoUserActivationKeyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NoUserActivationKeyException(String message) {
        super(message);
	}
}
