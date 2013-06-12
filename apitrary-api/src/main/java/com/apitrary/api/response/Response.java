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
package com.apitrary.api.response;

import java.io.Serializable;

/**
 * <p>
 * Abstract Response class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public abstract class Response<T> implements Serializable {
	private static final long serialVersionUID = 5050233742143155805L;

	protected String result;
	protected String statusMessage;
	protected int statusCode;
	protected long responseTime;

	/**
	 * <p>
	 * Getter for the field <code>result</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getResult() {
		return result;
	}

	/**
	 * <p>
	 * Setter for the field <code>result</code>.
	 * </p>
	 *
	 * @param result
	 *            a {@link java.lang.String} object.
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * <p>
	 * Getter for the field <code>statusMessage</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * <p>
	 * Setter for the field <code>statusMessage</code>.
	 * </p>
	 *
	 * @param statusMessage
	 *            a {@link java.lang.String} object.
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * <p>
	 * Getter for the field <code>statusCode</code>.
	 * </p>
	 *
	 * @return a int.
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * <p>
	 * Setter for the field <code>statusCode</code>.
	 * </p>
	 *
	 * @param statusCode
	 *            a int.
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * <p>
	 * Getter for the field <code>responseTime</code>.
	 * </p>
	 *
	 * @return a long.
	 */
	public long getResponseTime() {
		return responseTime;
	}

	/**
	 * <p>
	 * Setter for the field <code>responseTime</code>.
	 * </p>
	 *
	 * @param responseTime
	 *            a long.
	 */
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Response [result=" + result + ", statusMessage=" + statusMessage + ", statusCode=" + statusCode + ", responseTime=" + responseTime + "]";
	}
}
