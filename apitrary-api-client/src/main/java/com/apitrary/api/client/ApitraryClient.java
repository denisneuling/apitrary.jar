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
package com.apitrary.api.client;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.apitrary.api.client.exception.CommunicationErrorException;
import com.apitrary.api.client.exception.SerializationException;
import com.apitrary.api.client.support.AbstractApitraryClient;
import com.apitrary.api.client.util.RequestUtil;
import com.apitrary.api.request.Request;
import com.apitrary.api.response.Response;

/**
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 */
public class ApitraryClient extends AbstractApitraryClient {

	private ApitraryApi api;
	
	private int connectionTimeOut = DEFAULTCONNECTIONTIMEOUT;
	private int receiveTimeout = DEFAULTRECEIVETIMEOUT;

	private ApitraryClient(ApitraryApi api) {
		this.api = api;
	}

	public static ApitraryClient connectTo(ApitraryApi api) {
		return new ApitraryClient(api);
	}

	public <T> T send(Request<T> request) {

		if (request == null) {
			throw new CommunicationErrorException("Request cannot be null.");
		}
		RequestUtil.validate(request);

		@SuppressWarnings("unchecked")
		T response = (T) dispatchByMethod(request);
		
		return response;
	}

	@Override
	protected <T> String inquireVHost() {
		return protocol + api.getApiId() + "." + apitraryUrl;
	}

	@Override
	protected <T> Response<T> deserialize(String response, Request<T> request) {
		Response<T> target = RequestUtil.getInstanceOfParameterizedType(request);
		target.setResult(response);
		return target;
	}

	@Override
	protected <T> Response<T> deserialize(InputStream inputStream, Request<T> request) {
		String content = null;
		try {
			content = IOUtils.readStringFromStream(inputStream);
		} catch (IOException e) {
			throw new SerializationException(e);
		}
		return deserialize(content, request);
	}

	@Override
	protected WebClient instantiateWebClient() {
		WebClient webClient = WebClient.create(inquireVHost());
		webClient = webClient.accept(MediaType.APPLICATION_JSON);
		webClient = webClient.header(apiAuthHeaderKey, api.getApiKey());
		webClient = webClient.header("Content-Type", contentType);
		
		HTTPConduit conduit = WebClient.getConfig(webClient).getHttpConduit();
		TLSClientParameters params = conduit.getTlsClientParameters();
		if (params == null) {
			params = new TLSClientParameters();
			conduit.setTlsClientParameters(params);
		}
		params.setDisableCNCheck(true);

		HTTPClientPolicy policy = new HTTPClientPolicy();
		policy.setConnectionTimeout(connectionTimeOut);
		policy.setReceiveTimeout(receiveTimeout);
		policy.setAllowChunking(false);
		conduit.setClient(policy);
		
		return webClient;
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public int getReceiveTimeout() {
		return receiveTimeout;
	}

	public void setReceiveTimeout(int receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}
}
