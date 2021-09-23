package com.exception;

public class NoUsernameOrPasswordException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoUsernameOrPasswordException(String message) {
        super(message);
	}
}
