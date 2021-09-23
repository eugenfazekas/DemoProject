package com.exception;

public class CreateAccountKeyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CreateAccountKeyException(String message) {
        super(message);
	}
}
