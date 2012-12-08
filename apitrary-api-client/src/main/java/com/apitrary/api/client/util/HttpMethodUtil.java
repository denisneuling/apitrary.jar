package com.apitrary.api.client.util;

import com.apitrary.api.annotation.Method;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.request.Request;

public class HttpMethodUtil {

	public static <T> HttpMethod retrieveMethod(Request<T> request) {
		if (request == null) {
			return null;
		}

		Class<?> clazz = request.getClass();

		Method method = clazz.getAnnotation(Method.class);
		HttpMethod httpMethod = method.value();
		return httpMethod;
	}
}
