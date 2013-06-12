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
package com.apitrary.api.transport;

import java.net.URI;

import com.apitrary.api.ApitraryApi;
import com.apitrary.api.exception.ApiTransportException;

/**
 * Single argumentless constructor is needed! Otherwise we cannot instantiate
 * reflectively the new transport instance.
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 * @since 0.1.1
 */
public abstract class Transport {

	/** Constant <code>apiAuthHeaderKey="X-Api-Key"</code> */
	protected static final String apiAuthHeaderKey = "X-Api-Key";

	/** Constant <code>contentType="application/json"</code> */
	protected static final String contentType = "application/json";

	private ApitraryApi apitraryApi;

	/**
	 * <p>Constructor for Transport.</p>
	 */
	public Transport() {
	}

	/**
	 * <p>Constructor for Transport.</p>
	 *
	 * @param apitraryApi a {@link com.apitrary.api.ApitraryApi} object.
	 */
	public Transport(ApitraryApi apitraryApi) {
		this.apitraryApi = apitraryApi;
	}

	/**
	 * <p>Getter for the field <code>apitraryApi</code>.</p>
	 *
	 * @return a {@link com.apitrary.api.ApitraryApi} object.
	 */
	public ApitraryApi getApitraryApi() {
		return apitraryApi;
	}

	/**
	 * <p>Setter for the field <code>apitraryApi</code>.</p>
	 *
	 * @param apitraryApi a {@link com.apitrary.api.ApitraryApi} object.
	 */
	public void setApitraryApi(ApitraryApi apitraryApi) {
		this.apitraryApi = apitraryApi;
	}

	/**
	 * <p>doGet.</p>
	 *
	 * @param uri a {@link java.net.URI} object.
	 * @return a {@link com.apitrary.api.transport.TransportResult} object.
	 * @throws com.apitrary.api.exception.ApiTransportException if any.
	 */
	public abstract TransportResult doGet(URI uri) throws ApiTransportException;

	/**
	 * <p>doPost.</p>
	 *
	 * @param uri a {@link java.net.URI} object.
	 * @param payload a {@link java.lang.String} object.
	 * @return a {@link com.apitrary.api.transport.TransportResult} object.
	 * @throws com.apitrary.api.exception.ApiTransportException if any.
	 */
	public abstract TransportResult doPost(URI uri, String payload) throws ApiTransportException;

	/**
	 * <p>doPut.</p>
	 *
	 * @param uri a {@link java.net.URI} object.
	 * @param payload a {@link java.lang.String} object.
	 * @return a {@link com.apitrary.api.transport.TransportResult} object.
	 * @throws com.apitrary.api.exception.ApiTransportException if any.
	 */
	public abstract TransportResult doPut(URI uri, String payload) throws ApiTransportException;

	/**
	 * <p>doDelete.</p>
	 *
	 * @param uri a {@link java.net.URI} object.
	 * @return a {@link com.apitrary.api.transport.TransportResult} object.
	 * @throws com.apitrary.api.exception.ApiTransportException if any.
	 */
	public abstract TransportResult doDelete(URI uri) throws ApiTransportException;
}
