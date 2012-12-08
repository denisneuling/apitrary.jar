package com.apitrary.api.client.exception;

import com.apitrary.api.client.common.HttpStatus;

public class CommunicationErrorException extends ClientException {
	private static final long serialVersionUID = 212232635139714702L;

	public CommunicationErrorException(String message) {
		super(message);
	}

	public CommunicationErrorException(Throwable th) {
		super(th);
	}

	public CommunicationErrorException(HttpStatus httpStatus, String message) {
		super(httpStatus.getCode() + " " + message);
	}

	public CommunicationErrorException(HttpStatus httpStatus) {
		super(httpStatus.getCode() + " " + httpStatus.toString());
	}

}
