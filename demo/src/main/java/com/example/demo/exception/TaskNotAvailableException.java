package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskNotAvailableException extends RuntimeException{

	private static final long serialVersionUID = -4326468159911673163L;

	public TaskNotAvailableException(String message) {
		super(message);
	}

	public TaskNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}
}
