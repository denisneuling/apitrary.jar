package com.apitrary.api.client.exception;

public class ClientException extends RuntimeException {
	private static final long serialVersionUID = -3334422740923547404L;

	public ClientException(Throwable th){
		super(th);
	}

	public ClientException(String message) {
		super(message);
	}
}
