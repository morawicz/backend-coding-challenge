package com.morawicz.backendcodingchallenge.response;

import java.util.Date;

public class ErrorResponse {

	private Date timestamp;
	private int httpStatus;
	private String httpError;
	private String message;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(Date timestamp, int httpStatus, String httpError, String message) {
		super();
		this.timestamp = timestamp;
		this.httpStatus = httpStatus;
		this.httpError = httpError;
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getHttpError() {
		return httpError;
	}

	public String getMessage() {
		return message;
	}
}
