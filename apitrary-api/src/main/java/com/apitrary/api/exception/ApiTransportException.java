/*
 * Copyright 2012-2013 Denis Neuling 
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
package com.apitrary.api.exception;

/**
 * <p>ApiTransportException class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 * @since 0.1.1
 */
public class ApiTransportException extends ApiException{
	private static final long serialVersionUID = -1437773368782858652L;

	/**
	 * <p>
	 * Constructor for ApiTransportException.
	 * </p>
	 *
	 * @param th
	 *            a {@link java.lang.Throwable} object.
	 */
	public ApiTransportException(Throwable th) {
		super(th);
	}

	/**
	 * <p>
	 * Constructor for ApiTransportException.
	 * </p>
	 *
	 * @param message
	 *            a {@link java.lang.String} object.
	 */
	public ApiTransportException(String message) {
		super(message);
	}
}
