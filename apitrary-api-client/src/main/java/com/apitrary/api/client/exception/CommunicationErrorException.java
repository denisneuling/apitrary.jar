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
