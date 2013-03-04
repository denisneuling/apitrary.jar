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
package com.apitrary.api.client.support;

import java.io.InputStream;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;

import com.apitrary.api.client.common.HttpStatus;
import com.apitrary.api.client.common.Timer;
import com.apitrary.api.client.exception.CommunicationErrorException;
import com.apitrary.api.client.util.HttpMethodUtil;
import com.apitrary.api.client.util.PathUtil;
import com.apitrary.api.client.util.RequestUtil;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.request.Request;
import com.apitrary.api.response.Response;

/**
 * <p>
 * Abstract AbstractApitraryClient class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public abstract class AbstractApitraryClient {
	protected final Logger log = Logger.getLogger(this.getClass());

	/** Constant <code>apitraryUrl="api.apitrary.com"</code> */
	protected static final String apitraryUrl = "api.apitrary.com";
	
	/** Constant <code>protocol="http://"</code> */
	protected static final String protocol = "https://";
	
	/** Constant <code>apiAuthHeaderKey="X-Api-Key"</code> */
	protected static final String apiAuthHeaderKey = "X-Api-Key";
	
	/** Constant <code>contentType="application/json"</code> */
	protected static final String contentType = "application/json";

	/** Constant <code>DEFAULTCONNECTIONTIMEOUT=60000</code> */
	protected static final int DEFAULTCONNECTIONTIMEOUT = 60000;
	
	/** Constant <code>DEFAULTRECEIVETIMEOUT=60000</code> */
	protected static final int DEFAULTRECEIVETIMEOUT = 60000;

	/**
	 * <p>
	 * dispatchByMethod.
	 * </p>
	 * 
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.apitrary.api.response.Response} object.
	 */
	protected <T> Response<T> dispatchByMethod(Request<T> request) {
		HttpMethod method = HttpMethodUtil.retrieveMethod(request);

		WebClient webClient = instantiateWebClient();

		switch (method) {
			case GET:
				return doGet(webClient, request);
			case POST:
				return doPost(webClient, request);
			case PUT:
				return doPut(webClient, request);
			case DELETE:
				return doDelete(webClient, request);
			default:
				throw new CommunicationErrorException(HttpStatus.Not_Implemented);
		}
	}

	/**
	 * <p>
	 * doGet.
	 * </p>
	 * 
	 * @param webClient
	 *            a {@link org.apache.cxf.jaxrs.client.WebClient} object.
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.apitrary.api.response.Response} object.
	 */
	protected <T> Response<T> doGet(WebClient webClient, Request<T> request) {
		webClient = webClient.path(inquirePath(request));
		webClient = RequestUtil.resolveAndSetQueryPart(request, webClient);

		Timer timer = Timer.tic();
		javax.ws.rs.core.Response cxfResponse = webClient.get();
		timer.toc();

		log.trace(cxfResponse.getStatus() + " " + webClient.getCurrentURI() + " took " + timer.getDifference() + "ms");

		Response<T> response = toResponse(timer, cxfResponse, request);

		return response;
	}

	/**
	 * <p>
	 * doPost.
	 * </p>
	 * 
	 * @param webClient
	 *            a {@link org.apache.cxf.jaxrs.client.WebClient} object.
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.apitrary.api.response.Response} object.
	 */
	protected <T> Response<T> doPost(WebClient webClient, Request<T> request) {
		webClient = webClient.path(inquirePath(request));
		webClient = RequestUtil.resolveAndSetQueryPart(request, webClient);

		String payload = RequestUtil.getRequestPayload(request);

		Timer timer = Timer.tic();
		javax.ws.rs.core.Response cxfResponse = webClient.post(payload);
		timer.toc();

		log.trace(cxfResponse.getStatus() + " " + webClient.getCurrentURI() + " took " + timer.getDifference() + "ms");

		Response<T> response = toResponse(timer, cxfResponse, request);

		return response;
	}

	/**
	 * <p>
	 * doPut.
	 * </p>
	 * 
	 * @param webClient
	 *            a {@link org.apache.cxf.jaxrs.client.WebClient} object.
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.apitrary.api.response.Response} object.
	 */
	protected <T> Response<T> doPut(WebClient webClient, Request<T> request) {
		webClient = webClient.path(inquirePath(request));
		webClient = RequestUtil.resolveAndSetQueryPart(request, webClient);

		String payload = RequestUtil.getRequestPayload(request);

		Timer timer = Timer.tic();
		javax.ws.rs.core.Response cxfResponse = webClient.put(payload);
		timer.toc();

		log.trace(cxfResponse.getStatus() + " " + webClient.getCurrentURI() + " took " + timer.getDifference() + "ms");

		Response<T> response = toResponse(timer, cxfResponse, request);

		return response;
	}

	/**
	 * <p>
	 * doDelete.
	 * </p>
	 * 
	 * @param webClient
	 *            a {@link org.apache.cxf.jaxrs.client.WebClient} object.
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.apitrary.api.response.Response} object.
	 */
	protected <T> Response<T> doDelete(WebClient webClient, Request<T> request) {
		webClient = webClient.path(inquirePath(request));
		webClient = RequestUtil.resolveAndSetQueryPart(request, webClient);

		Timer timer = Timer.tic();
		javax.ws.rs.core.Response cxfResponse = webClient.delete();
		timer.toc();

		log.trace(cxfResponse.getStatus() + " " + webClient.getCurrentURI() + " took " + timer.getDifference() + "ms");

		Response<T> response = toResponse(timer, cxfResponse, request);

		return response;
	}

	private <T> Response<T> toResponse(Timer timer, javax.ws.rs.core.Response cxfResponse, Request<T> request) {
		InputStream inputStream = (InputStream) cxfResponse.getEntity();
		Response<T> response = null;

		HttpStatus status = HttpStatus.getStatus(cxfResponse.getStatus());

		response = deserialize(inputStream, request);

		response.setStatusCode(status.getCode());
		response.setResponseTime(timer.getDifference());

		return response;
	}

	/**
	 * <p>
	 * inquirePath.
	 * </p>
	 * 
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	protected <T> String inquirePath(Request<T> request) {
		return PathUtil.resolveResourcePath(request);
	}

	/**
	 * <p>
	 * inquireVHost.
	 * </p>
	 * 
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	protected abstract <T> String inquireVHost();

	/**
	 * <p>
	 * deserialize.
	 * </p>
	 * 
	 * @param response
	 *            a {@link java.lang.String} object.
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.apitrary.api.response.Response} object.
	 */
	protected abstract <T> Response<T> deserialize(String response, Request<T> request);

	/**
	 * <p>
	 * deserialize.
	 * </p>
	 * 
	 * @param inputStream
	 *            a {@link java.io.InputStream} object.
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.apitrary.api.response.Response} object.
	 */
	protected abstract <T> Response<T> deserialize(InputStream inputStream, Request<T> request);

	/**
	 * <p>
	 * instantiateWebClient.
	 * </p>
	 * 
	 * @return a {@link org.apache.cxf.jaxrs.client.WebClient} object.
	 */
	protected abstract WebClient instantiateWebClient();
}
