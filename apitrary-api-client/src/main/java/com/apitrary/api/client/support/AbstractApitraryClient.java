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
package com.apitrary.api.client.support;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.apitrary.api.ApitraryApi;
import com.apitrary.api.client.common.HttpStatus;
import com.apitrary.api.client.common.Timer;
import com.apitrary.api.client.exception.ClientException;
import com.apitrary.api.client.exception.CommunicationErrorException;
import com.apitrary.api.client.util.HttpMethodUtil;
import com.apitrary.api.client.util.PathUtil;
import com.apitrary.api.client.util.RequestUtil;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.request.Request;
import com.apitrary.api.response.Response;
import com.apitrary.api.transport.ApiClientTransportFactory;
import com.apitrary.api.transport.TransportResult;

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

	protected ApitraryApi api;
	
	private ApiClientTransportFactory apiClientTransportFactory = new ApiClientTransportFactory();
	
	public ApiClientTransportFactory getApiClientTransportFactory() {
		return apiClientTransportFactory;
	}

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

		switch (method) {
			case GET:
				return doGet(request);
			case POST:
				return doPost(request);
			case PUT:
				return doPut(request);
			case DELETE:
				return doDelete(request);
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
	protected <T> Response<T> doGet(Request<T> request) {
		URI uri = buidURI(request);
		
		log.info(uri);
		
		Timer timer = Timer.tic();
		TransportResult result = getApiClientTransportFactory().newTransport(api).doGet(uri);
		timer.toc();
		
		log.trace(result.getStatusCode() + " " + uri.toString() + " took " + timer.getDifference() + "ms");

		Response<T> response = toResponse(timer, result, request);

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
	protected <T> Response<T> doPost(Request<T> request) {
		String payload = RequestUtil.getRequestPayload(request);
		URI uri = buidURI(request);

		log.info(uri);
		
		Timer timer = Timer.tic();
		TransportResult result = getApiClientTransportFactory().newTransport(api).doPost(uri, payload);
		timer.toc();

		log.trace(result.getStatusCode() + " " + uri.toString() + " took " + timer.getDifference() + "ms");

		Response<T> response = toResponse(timer, result, request);

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
	protected <T> Response<T> doPut(Request<T> request) {
		String payload = RequestUtil.getRequestPayload(request);
		URI uri = buidURI(request);
		
		log.info(uri);
		
		Timer timer = Timer.tic();
		TransportResult result = getApiClientTransportFactory().newTransport(api).doPut(uri, payload);
		timer.toc();

		log.trace(result.getStatusCode() + " " + uri.toString() + " took " + timer.getDifference() + "ms");

		Response<T> response = toResponse(timer, result, request);

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
	protected <T> Response<T> doDelete(Request<T> request) {
		URI uri = buidURI(request);
		
		log.info(uri);
		
		Timer timer = Timer.tic();
		TransportResult result = getApiClientTransportFactory().newTransport(api).doDelete(uri);
		timer.toc();

		log.trace(result.getStatusCode() + " " + uri.toString() + " took " + timer.getDifference() + "ms");

		Response<T> response = toResponse(timer, result, request);

		return response;
	}
	
	protected <T> URI buidURI(Request<T> request){
		URL url = null;
		try {
			url = api.getURL();
		} catch (MalformedURLException e) {
			throw new ClientException(e);
		}
		String path = inquirePath(request);
		Map<String,String> qry = RequestUtil.resolveQueryPart(request);
		
		Set<String> keys = qry.keySet();
		String query = "";
		if(!keys.isEmpty()){
			for(String key : keys){
				query += (!query.isEmpty()?"&":"?")+key+"="+qry.get(key);
			}
		}
		
		String fqrn = url.toString() + path + query;
		try {
			return new URI(fqrn);
		} catch (URISyntaxException e) {
			throw new ClientException(e);
		}
	}

	private <T> Response<T> toResponse(Timer timer, TransportResult transportResult, Request<T> request) {
		Response<T> response = null;
		HttpStatus status = HttpStatus.getStatus(transportResult.getStatusCode());
		response = deserialize(transportResult.getResult(), request);
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
}
