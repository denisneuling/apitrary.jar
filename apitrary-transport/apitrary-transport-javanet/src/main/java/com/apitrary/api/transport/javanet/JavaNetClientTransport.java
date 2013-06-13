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
package com.apitrary.api.transport.javanet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.apitrary.api.ApitraryApi;
import com.apitrary.api.exception.ApiTransportException;
import com.apitrary.api.transport.Transport;
import com.apitrary.api.transport.TransportResult;

/**
 * <p>
 * JavaNetClientTransport class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class JavaNetClientTransport extends Transport {
	protected Logger log = Logger.getLogger(getClass());

	/**
	 * <p>
	 * Constructor for JavaNetClientTransport.
	 * </p>
	 */
	public JavaNetClientTransport() {
	}

	/**
	 * <p>
	 * Constructor for JavaNetClientTransport.
	 * </p>
	 * 
	 * @param apitraryApi
	 *            a {@link com.apitrary.api.ApitraryApi} object.
	 */
	public JavaNetClientTransport(ApitraryApi apitraryApi) {
		super(apitraryApi);
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doGet(URI uri) {
		return doit("GET", uri, null);
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doPost(URI uri, String payload) {
		return doit("POST", uri, payload);
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doPut(URI uri, String payload) {
		return doit("PUT", uri, payload);
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doDelete(URI uri) {
		return doit("DELETE", uri, null);
	}

	/**
	 * <p>
	 * doit.
	 * </p>
	 * 
	 * @param method
	 *            a {@link java.lang.String} object.
	 * @param uri
	 *            a {@link java.net.URI} object.
	 * @param payload
	 *            a {@link java.lang.String} object.
	 * @return a {@link com.apitrary.api.transport.TransportResult} object.
	 */
	protected TransportResult doit(String method, URI uri, String payload){
		HttpURLConnection httpConnection = null;
		try {
			URL url = uri.toURL();
			URLConnection connection = url.openConnection();

			if (connection instanceof HttpURLConnection) {
				httpConnection = (HttpURLConnection) connection;

				httpConnection.setRequestMethod(method);
//				httpConnection.setRequestProperty("Content-Language", "en-US");

				httpConnection.setRequestProperty(apiAuthHeaderKey, getApitraryApi().getApiKey());
				httpConnection.setRequestProperty("Accept", contentType);
				httpConnection.setRequestProperty("Content-Type", contentType);

				httpConnection.setUseCaches(false);
				httpConnection.setDoInput(true);
				httpConnection.setDoOutput(payload!=null);

				log.debug(method+" "+ httpConnection.getURL()+(payload!=null?"application/json: "+payload:""));
				
				if(payload!=null){
					byte[] bytes = payload.getBytes();
					httpConnection.setRequestProperty("Content-Length", "" + Integer.toString(bytes.length));
					DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
					wr.writeBytes(payload);
					wr.flush();
					wr.close();
				}

				return new JavaNetClientTransportResult(httpConnection);
			} else {
				throw new ApiTransportException("Connection is not an HTTPConnection");
			}
		} catch (IOException ioe) {
			throw new ApiTransportException(ioe);
		}
	}
}
