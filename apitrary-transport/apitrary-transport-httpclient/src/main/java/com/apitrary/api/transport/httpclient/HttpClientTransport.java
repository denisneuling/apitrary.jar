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
package com.apitrary.api.transport.httpclient;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.apitrary.api.ApitraryApi;
import com.apitrary.api.exception.ApiTransportException;
import com.apitrary.api.transport.Transport;
import com.apitrary.api.transport.TransportResult;

/**
 * <p>
 * HttpClientTransport class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class HttpClientTransport extends Transport {
	protected Logger log = Logger.getLogger(getClass());

	/**
	 * <p>
	 * Constructor for HttpClientTransport.
	 * </p>
	 */
	public HttpClientTransport() {
	}

	/**
	 * <p>
	 * Constructor for HttpClientTransport.
	 * </p>
	 * 
	 * @param apitraryApi
	 *            a {@link com.apitrary.api.ApitraryApi} object.
	 */
	public HttpClientTransport(ApitraryApi apitraryApi) {
		super(apitraryApi);
	}

	private DefaultHttpClient client = new DefaultHttpClient();

	/** {@inheritDoc} */
	@Override
	public TransportResult doGet(URI uri) {
		HttpGet request = new HttpGet(uri);

		request.setHeader(apiAuthHeaderKey, getApitraryApi().getApiKey());
		request.setHeader("Content-Type", contentType);
		request.setHeader("Accept", contentType);

		log.debug("GET " + request.getURI());

		try {
			HttpResponse response = client.execute(request);
			return new HttpClientTransportResult(response);
		} catch (Throwable t) {
			throw new ApiTransportException(t);
		}
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doPost(URI uri, String payload) {
		HttpPost request = new HttpPost(uri);

		request.setHeader(apiAuthHeaderKey, getApitraryApi().getApiKey());
		request.setHeader("Content-Type", contentType);
		request.setHeader("Accept", contentType);

		log.debug("POST " + request.getURI() + " application/json: " + payload);

		try {
			request.setEntity(new StringEntity(payload));
			HttpResponse response = client.execute(request);
			return new HttpClientTransportResult(response);
		} catch (Throwable t) {
			throw new ApiTransportException(t);
		}
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doPut(URI uri, String payload) {
		HttpPut request = new HttpPut(uri);

		request.setHeader(apiAuthHeaderKey, getApitraryApi().getApiKey());
		request.setHeader("Content-Type", contentType);
		request.setHeader("Accept", contentType);

		log.debug("PUT " + request.getURI() + " application/json: " + payload);

		try {
			request.setEntity(new StringEntity(payload));

			HttpResponse response = client.execute(request);
			return new HttpClientTransportResult(response);
		} catch (Throwable t) {
			throw new ApiTransportException(t);
		}
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doDelete(URI uri) {
		HttpDelete request = new HttpDelete(uri);

		request.setHeader(apiAuthHeaderKey, getApitraryApi().getApiKey());
		request.setHeader("Content-Type", contentType);
		request.setHeader("Accept", contentType);

		log.debug("DELETE " + request.getURI());

		try {
			HttpResponse response = client.execute(request);

			return new HttpClientTransportResult(response);
		} catch (Throwable t) {
			throw new ApiTransportException(t);
		}
	}

}
