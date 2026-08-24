package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EmailCodeException extends RuntimeException {

	private static final long serialVersionUID = -7244989517675165665L;

	public EmailCodeException(String message) {
		super(message);
	}

	public EmailCodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
