package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserRightsException extends RuntimeException {

	
	private static final long serialVersionUID = -2406774228211490739L;

	public UserRightsException(String message) {
		super(message);
	}

	public UserRightsException(String message, Throwable cause) {
		super(message, cause);
	}

}
