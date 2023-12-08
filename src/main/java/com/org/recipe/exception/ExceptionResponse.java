package com.org.recipe.exception;

public class ExceptionResponse {

	public ExceptionResponse(String message, String details) {
		super();
		this.message = message;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	private String message;
	private String details;

}
