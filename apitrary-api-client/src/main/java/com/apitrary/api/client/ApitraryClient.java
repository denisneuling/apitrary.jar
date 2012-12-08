package com.apitrary.api.client;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.cxf.jaxrs.client.WebClient;

import com.apitrary.api.client.exception.CommunicationErrorException;
import com.apitrary.api.client.support.AbstractApitraryClient;
import com.apitrary.api.client.util.RequestUtil;
import com.apitrary.api.request.Request;
import com.apitrary.api.response.Response;

public class ApitraryClient extends AbstractApitraryClient{

	private static String apiAuthHeaderKey = "X-Api-Key";
	private ApitraryApi api;

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

		T response = (T) dispatchByMethod(request);
		return response;
	}

	private HashMap<String, String> prepareHeader() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(apiAuthHeaderKey, api.getApiKey());
		return map;
	}

	/////////////////////////////////////
	@Override
	protected <T> Response<T> deserialize(String response, Request<T> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T> Response<T> deserialize(InputStream inputStream,
			Request<T> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T> Response<T> deserializeError(InputStream inputStream,
			Request<T> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T> Response<T> deserializeError(String response,
			Request<T> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTargetUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected WebClient instantiateWebClient(String targeturl) {
		// TODO Auto-generated method stub
		return null;
	}
}
