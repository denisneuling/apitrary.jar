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
 */
public abstract class Transport {

	/** Constant <code>apiAuthHeaderKey="X-Api-Key"</code> */
	protected static final String apiAuthHeaderKey = "X-Api-Key";

	/** Constant <code>contentType="application/json"</code> */
	protected static final String contentType = "application/json";

	private ApitraryApi apitraryApi;

	public Transport() {
	}

	public Transport(ApitraryApi apitraryApi) {
		this.apitraryApi = apitraryApi;
	}

	public ApitraryApi getApitraryApi() {
		return apitraryApi;
	}

	public void setApitraryApi(ApitraryApi apitraryApi) {
		this.apitraryApi = apitraryApi;
	}

	public abstract TransportResult doGet(URI uri) throws ApiTransportException;

	public abstract TransportResult doPost(URI uri, String payload) throws ApiTransportException;

	public abstract TransportResult doPut(URI uri, String payload) throws ApiTransportException;

	public abstract TransportResult doDelete(URI uri) throws ApiTransportException;
}
