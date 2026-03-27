package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserPasswordException extends RuntimeException {

	private static final long serialVersionUID = 5116235799569409711L;

	public UserPasswordException(String message) {
		super(message);
	}

	public UserPasswordException(String message, Throwable cause) {
		super(message, cause);
	}
}
