package com.apitrary.api.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;

import com.apitrary.api.annotation.Body;
import com.apitrary.api.annotation.Query;
import com.apitrary.api.annotation.Required;
import com.apitrary.api.client.exception.ValidationConstraintViolationException;
import com.apitrary.api.client.exception.ValidationConstraintViolationException.ConstraintViolation;
import com.apitrary.api.request.Request;
import com.apitrary.api.response.Response;
import com.google.gson.JsonObject;

public class RequestUtil {

	private static final String EMPTY = "{}";
	private static final String preMessage = "Request breaks constraints.";

	/**
	 * <p>
	 * getInstanceOfParameterizedType.
	 * </p>
	 *
	 * @param request
	 *            a {@link com.cloudcontrolled.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.cloudcontrolled.api.response.Response} object.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Response<T> getInstanceOfParameterizedType(Request<T> request) {
		Type superclazz = request.getClass().getGenericSuperclass();
		try {
			Type parameterizedTypeClazz = ((ParameterizedType) superclazz).getActualTypeArguments()[0];
			return (Response<T>) ((Class<T>) parameterizedTypeClazz).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * getBodyAsMultiValuedMap.
	 * </p>
	 *
	 * @param request
	 *            a {@link com.cloudcontrolled.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link javax.ws.rs.core.MultivaluedMap} object.
	 */
	@SuppressWarnings("unused")
	public static <T> String getRequestPayload(Request<T> request) {
		Class<?> referenceClazz = request.getClass();
		List<Field> fields = ClassUtil.getAnnotatedFields(referenceClazz, Body.class);
		List<JsonObject> objects = new LinkedList<JsonObject>();
		for (Field field : fields) {
			Body body = field.getAnnotation(Body.class);

			String asString = ClassUtil.getValueOf(field, request, referenceClazz, String.class);
			return (asString!=null?asString:"");
//			objects.add(jsonObject);
		}
		return EMPTY;
	}

	/**
	 * <p>resolveAndSetQueryPart.</p>
	 *
	 * @param request a {@link com.cloudcontrolled.api.request.Request} object.
	 * @param webClient a {@link org.apache.cxf.jaxrs.client.WebClient} object.
	 * @param <T> a T object.
	 * @return a {@link org.apache.cxf.jaxrs.client.WebClient} object.
	 * @since 0.1.1
	 */
	public static <T> WebClient resolveAndSetQueryPart(Request<T> request, WebClient webClient) {
		HashMap<String, String> queryParts = resolveQueryPart(request);
		Iterator<String> iterator = queryParts.keySet().iterator();
		if (!iterator.hasNext()) {
			return webClient;
		} else {
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = queryParts.get(key);
				webClient = webClient.replaceQueryParam(key, value);
			}
		}
		return webClient;
	}

	/**
	 * <p>resolveQueryPart.</p>
	 *
	 * @param request a {@link com.cloudcontrolled.api.request.Request} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.HashMap} object.
	 * @since 0.1.1
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
	 *            a {@link com.cloudcontrolled.api.request.Request} object.
	 * @throws com.cloudcontrolled.api.client.exception.ValidationConstraintViolationException
	 *             if any.
	 * @param <T>
	 *            a T object.
	 * @since 0.1.1
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