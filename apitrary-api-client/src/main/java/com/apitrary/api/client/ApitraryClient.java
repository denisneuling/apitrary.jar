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
package com.apitrary.api.client;

import com.apitrary.api.ApitraryApi;
import com.apitrary.api.client.exception.CommunicationErrorException;
import com.apitrary.api.client.support.AbstractApitraryClient;
import com.apitrary.api.client.util.NormalizationUtil;
import com.apitrary.api.client.util.RequestUtil;
import com.apitrary.api.request.Request;
import com.apitrary.api.response.Response;
import com.apitrary.api.response.normalized.Normalizer;

/**
 * <p>
 * ApitraryClient class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class ApitraryClient extends AbstractApitraryClient {

	/**
	 * <p>
	 * Constructor for ApitraryClient.
	 * </p>
	 *
	 * @param api
	 *            a {@link com.apitrary.api.ApitraryApi} object.
	 */
	protected ApitraryClient(ApitraryApi api) {
		this.api = api;
	}

	/**
	 * <p>
	 * Constructor for ApitraryClient.
	 * </p>
	 *
	 * @since 0.1.1
	 */
	protected ApitraryClient() {
		throw new RuntimeException("Apitrary Client needs to connect to the targeted API. Hint: Use factory method #connectTo(ApitraryApi api)");
	}

	/**
	 * <p>
	 * connectTo.
	 * </p>
	 *
	 * @param api
	 *            a {@link com.apitrary.api.ApitraryApi} object.
	 * @return a {@link com.apitrary.api.client.ApitraryClient} object.
	 */
	public static ApitraryClient connectTo(ApitraryApi api) {
		return new ApitraryClient(api);
	}

	/**
	 * <p>
	 * send.
	 * </p>
	 *
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a T object.
	 */
	public <T> T send(Request<T> request) {

		if (request == null) {
			throw new CommunicationErrorException("Request cannot be null.");
		}
		RequestUtil.validate(request);

		@SuppressWarnings("unchecked")
		T response = (T) dispatchByMethod(request);

		return response;
	}

	/** {@inheritDoc} */
	@Override
	protected <T> Response<T> deserialize(String response, Request<T> request) {
		Response<T> target = RequestUtil.getInstanceOfParameterizedType(request);

		Normalizer normalizer = NormalizationUtil.getNormalizer(target);
		response = normalizer.normalize(response);

		target.setResult(response);
		return target;
	}
}
