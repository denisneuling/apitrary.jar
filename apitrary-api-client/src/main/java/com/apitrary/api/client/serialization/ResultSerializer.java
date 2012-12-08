package com.apitrary.api.client.serialization;

import com.apitrary.api.client.util.NormalizationUtil;
import com.apitrary.api.response.Response;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

public class ResultSerializer {

	protected Gson gson;

	public ResultSerializer() {
		initializeGson();
	}

	private void initializeGson() {
		gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			public boolean shouldSkipField(FieldAttributes fieldAttributes) {
				if (fieldAttributes.getName().equalsIgnoreCase("serialVersionUUID")) {
					return true;
				}
				return false;
			}

			public boolean shouldSkipClass(Class<?> arg0) {
				return false;
			}
		}).create();
	}
	
	@SuppressWarnings("unchecked")
	public <T> Response<T> fromJSON(String response, Response<T> target) {
		try {
			response = NormalizationUtil.getNormalizer(target).normalize(response);
		} catch (Exception e) {
			throw new SerializationException(e);
		}

		try {
			Response<T> fromJson = gson.fromJson(response, target.getClass());
			if (fromJson == null) {
				fromJson = target;
			}
			return fromJson;
		} catch (/*JsonSyntax*/Exception jse) {
			throw new SerializationException(jse);
		}
	}

}
