package com.org.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleJsonMappingException(JsonMappingException ex) {
		ExceptionResponse errorResponse = new ExceptionResponse("Request is not valid", ex.getLocalizedMessage());
		return new ResponseEntity<ExceptionResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleRecordNotFoundException(RecordNotFoundException ex) {
		ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), "");
		return new ResponseEntity<ExceptionResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RecordAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleRecordAlreadyExistsException(RecordAlreadyExistsException ex) {
		ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), "");
		return new ResponseEntity<ExceptionResponse>(errorResponse, HttpStatus.CONFLICT);
	}

	

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionResponse> handleDefaultException(Throwable ex) {
		ExceptionResponse errorResponse = new ExceptionResponse("Request is unable to process now ",
				ex.getLocalizedMessage());
		return new ResponseEntity<ExceptionResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
