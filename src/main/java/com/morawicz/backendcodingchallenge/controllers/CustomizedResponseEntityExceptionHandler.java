package com.morawicz.backendcodingchallenge.controllers;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.morawicz.backendcodingchallenge.exceptions.InvalidRequestValidationRuntimeException;
import com.morawicz.backendcodingchallenge.response.ErrorResponse;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(InvalidRequestValidationRuntimeException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidRequestValidationRuntimeException(
			InvalidRequestValidationRuntimeException ex, WebRequest request) {
		ErrorResponse exceptionResponse = new ErrorResponse(
				new Date(),
				ex.getHttpStatus().value(),
				ex.getHttpStatus().getReasonPhrase(),
				ex.getMessage());
		return new ResponseEntity<ErrorResponse>(exceptionResponse, ex.getHttpStatus());
	}
}
