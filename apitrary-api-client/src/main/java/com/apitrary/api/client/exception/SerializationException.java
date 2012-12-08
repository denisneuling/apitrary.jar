package com.apitrary.api.client.exception;

import com.apitrary.api.client.exception.ClientException;

public class SerializationException extends ClientException{
	private static final long serialVersionUID = 1110855831203401596L;

	public SerializationException(String message) {
		super(message);
	}

	public SerializationException(Throwable th) {
		super(th);
	}

}
