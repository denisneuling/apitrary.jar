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
package com.apitrary.api.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.apitrary.api.annotation.Body;
import com.apitrary.api.annotation.Query;
import com.apitrary.api.annotation.Required;
import com.apitrary.api.client.exception.ValidationConstraintViolationException;
import com.apitrary.api.client.exception.ValidationConstraintViolationException.ConstraintViolation;
import com.apitrary.api.request.Request;
import com.apitrary.api.response.Response;

/**
 * <p>
 * RequestUtil class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class RequestUtil {

	private static final String EMPTY = "{}";
	private static final String preMessage = "Request breaks constraints.";

	/**
	 * <p>
	 * getInstanceOfParameterizedType.
	 * </p>
	 *
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.apitrary.api.response.Response} object.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Response<T> getInstanceOfParameterizedType(Request<T> request) {
		Type superclazz = request.getClass().getGenericSuperclass();
		try {
			Type parameterizedTypeClazz = ((ParameterizedType) superclazz).getActualTypeArguments()[0];
			return (Response<T>) ((Class<T>) parameterizedTypeClazz).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * getRequestPayload.
	 * </p>
	 *
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	@SuppressWarnings("unused")
	public static <T> String getRequestPayload(Request<T> request) {
		Class<?> referenceClazz = request.getClass();
		List<Field> fields = ClassUtil.getAnnotatedFields(referenceClazz, Body.class);
		List<String> objects = new LinkedList<String>();
		for (Field field : fields) {
			Body body = field.getAnnotation(Body.class);

			String asString = ClassUtil.getValueOf(field, request, referenceClazz, String.class);
			return (asString != null ? asString : "");
			// what if there are more bodies?!
		}
		return EMPTY;
	}

	/**
	 * <p>
	 * resolveQueryPart.
	 * </p>
	 *
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.util.HashMap} object.
	 */
	public static <T> HashMap<String, String> resolveQueryPart(Request<T> request) {
		HashMap<String, String> queryParts = new HashMap<String, String>();
		Class<?> referenceClazz = request.getClass();
		List<Field> fields = ClassUtil.getAnnotatedFields(referenceClazz, Query.class);
		for (Field field : fields) {
			Query query = field.getAnnotation(Query.class);
			String key = query.value();

			// in case the value() is null or empty: continue
			if (key == null || (key != null && key.isEmpty())) {
				continue;
			}
			String value = ClassUtil.getValueOf(field, request, referenceClazz, String.class);
			if (value != null) {
				queryParts.put(key, value);
			}
		}

		return queryParts;
	}

	/**
	 * <p>
	 * validate.
	 * </p>
	 *
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @throws com.apitrary.api.client.exception.ValidationConstraintViolationException
	 *             if any.
	 */
	public static <T> void validate(Request<T> request) throws ValidationConstraintViolationException {
		if (request != null) {
			Class<?> clazz = request.getClass();
			List<ConstraintViolation> leafs = new LinkedList<ConstraintViolation>();

			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (null != field.getAnnotation(Required.class)) {

					field.setAccessible(true);
					Object value = null;
					try {
						value = field.get(request);
					} catch (Exception e) {
						// not cool...
						throw new ValidationConstraintViolationException(e);
					}

					if (value == null || value instanceof String && ((String) value).isEmpty()) {
						ConstraintViolation violation = ConstraintViolation.newConstraintViolation("@" + Required.class.getSimpleName(), field);
						leafs.add(violation);
					}
				}
			}

			if (!leafs.isEmpty()) {
				throw new ValidationConstraintViolationException(preMessage, leafs);
			}
		}
	}
}
