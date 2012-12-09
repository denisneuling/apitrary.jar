/*
 * Copyright 2012 Denis Neuling 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.apitrary.api.client.exception;

import com.apitrary.api.client.common.HttpStatus;

/**
 * <p>CommunicationErrorException class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class CommunicationErrorException extends ClientException {
	private static final long serialVersionUID = 212232635139714702L;

	/**
	 * <p>Constructor for CommunicationErrorException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public CommunicationErrorException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for CommunicationErrorException.</p>
	 *
	 * @param th a {@link java.lang.Throwable} object.
	 */
	public CommunicationErrorException(Throwable th) {
		super(th);
	}

	/**
	 * <p>Constructor for CommunicationErrorException.</p>
	 *
	 * @param httpStatus a {@link com.apitrary.api.client.common.HttpStatus} object.
	 * @param message a {@link java.lang.String} object.
	 */
	public CommunicationErrorException(HttpStatus httpStatus, String message) {
		super(httpStatus.getCode() + " " + message);
	}

	/**
	 * <p>Constructor for CommunicationErrorException.</p>
	 *
	 * @param httpStatus a {@link com.apitrary.api.client.common.HttpStatus} object.
	 */
	public CommunicationErrorException(HttpStatus httpStatus) {
		super(httpStatus.getCode() + " " + httpStatus.toString());
	}

}
