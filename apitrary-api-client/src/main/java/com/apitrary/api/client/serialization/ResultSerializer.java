package com.apitrary.api.client.serialization;

import javax.xml.ws.Response;

import com.sun.xml.internal.ws.encoding.soap.SerializationException;

public class ResultSerializer {

//	protected Gson gson;

	/**
	 * <p>
	 * Constructor for JsonDeserializer.
	 * </p>
	 */
	public ResultSerializer() {
		initializeGson();
	}

	/**
	 * 
	 */
	private void initializeGson() {
//		gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
//			public boolean shouldSkipField(FieldAttributes fieldAttributes) {
//				if (fieldAttributes.getName().equalsIgnoreCase("serialVersionUUID")) {
//					return true;
//				}
//				return false;
//			}
//
//			public boolean shouldSkipClass(Class<?> arg0) {
//				return false;
//			}
//		}).create();
	}
	
	/**
	 * <p>
	 * fromJSON.
	 * </p>
	 *
	 * @param response
	 *            a {@link java.lang.String} object.
	 * @param target
	 *            a {@link com.cloudcontrolled.api.response.Response} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link com.cloudcontrolled.api.response.Response} object.
	 */
	@SuppressWarnings("unchecked")
	public <T> Response<T> fromJSON(String response, Response<T> target) {
//		try {
//			response = StandardizationUtil.getJSONStandardizer(target).normalize(response);
//		} catch (Exception e) {
//			throw new SerializationException(e);
//		}
//
//		try {
//			Response<T> fromJson = gson.fromJson(response, target.getClass());
//			if (fromJson == null) {
//				fromJson = target;
//			}
//			return fromJson;
//		} catch (/*JsonSyntax*/Exception jse) {
//			throw new SerializationException(jse);
//		}
		return null;
	}

}
