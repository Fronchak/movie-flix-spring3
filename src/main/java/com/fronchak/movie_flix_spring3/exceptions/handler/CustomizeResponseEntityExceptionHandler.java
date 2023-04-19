package com.fronchak.movie_flix_spring3.exceptions.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fronchak.movie_flix_spring3.exceptions.BadRequestException;
import com.fronchak.movie_flix_spring3.exceptions.DatabaseException;
import com.fronchak.movie_flix_spring3.exceptions.ExceptionResponse;
import com.fronchak.movie_flix_spring3.exceptions.ResourceNotFoundException;
import com.fronchak.movie_flix_spring3.exceptions.ValidationExceptionResponse;

@RestController
@ControllerAdvice
public class CustomizeResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ExceptionResponse response = makeResponse(e, request, status, "Resource not found");
		return ResponseEntity.status(status).body(response);
	}
	
	private ExceptionResponse makeResponse(Exception e, WebRequest request, HttpStatus status, String error) {
		ExceptionResponse response = new ExceptionResponse();
		response.setTimestamp(Instant.now());
		response.setStatus(status.value());
		response.setError(error);
		response.setMessage(e.getMessage());
		response.setPath(request.getDescription(false));
		return response;
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<ExceptionResponse> handleDatabaseException(DatabaseException e, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionResponse response = makeResponse(e, request, status, "Database error");
		return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex,
			WebRequest request 
			) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationExceptionResponse response = new ValidationExceptionResponse();
		response.setTimestamp(Instant.now());
		response.setStatus(status.value());
		response.setError("Validation Error");
		response.setMessage("Invalid inputs");
		response.setPath(request.getDescription(false));
		
		for(FieldError field : ex.getBindingResult().getFieldErrors()) {
			response.addError(field.getField(), field.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception e, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ExceptionResponse response = makeResponse(e, request, status, "Internal server error");
		response.setMessage("Something went wrong");
		return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionResponse response = makeResponse(e, request, status, "Bad request");
		return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> handleBadRequestException(BadCredentialsException e, WebRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ExceptionResponse response = makeResponse(e, request, status, "Unauthorized");
		return ResponseEntity.status(status).body(response);
	}
}
