package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	public ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleEmailCodeException(EmailCodeException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleUserRightsException(UserRightsException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleTaskNotAvailableExceptio(TaskNotAvailableException e) {
		// do what you want with e
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
