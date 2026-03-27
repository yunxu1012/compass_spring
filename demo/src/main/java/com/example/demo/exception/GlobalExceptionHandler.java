package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> handleAuthenticationException(UserNotFoundException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleAuthenticationException(UserTypeErrorException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleAuthenticationException(UserPasswordException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleAuthenticationException(AlreadyExistsException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleAuthenticationException(TokenExpiredException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

}
