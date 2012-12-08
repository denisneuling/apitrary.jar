package com.apitrary.api.client.util;

import javax.xml.ws.Response;

import com.apitrary.api.annotation.Normalized;
import com.apitrary.api.response.normalized.Normalizer;

public class NormalizationUtil {

	@SuppressWarnings("unchecked")
	public static <T> Normalizer getJSONStandardizer(Response<T> response) {
		Normalizer normalizer = new Normalizer();
		Class<Normalizer> standardizerClazz = ClassUtil.getClassAnnotationValue(response.getClass(), Normalized.class, "value", Class.class);
		if (standardizerClazz != null) {
			try {
				normalizer = standardizerClazz.newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return normalizer;
	}
}
