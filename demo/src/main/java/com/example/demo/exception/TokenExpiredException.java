package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpiredException extends RuntimeException {
	

	private static final long serialVersionUID = -7185829468407135384L;

	public TokenExpiredException(String message) {
		super(message);
	}

	public TokenExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
