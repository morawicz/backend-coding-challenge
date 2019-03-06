package com.morawicz.backendcodingchallenge.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidRequestValidationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5000535516205803000L;

	private HttpStatus httpStatus;

	public InvalidRequestValidationRuntimeException(String message) {
		super(message);
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
