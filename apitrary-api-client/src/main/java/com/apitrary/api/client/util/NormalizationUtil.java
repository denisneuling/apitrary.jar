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
package com.apitrary.api.client.util;

import com.apitrary.api.annotation.Normalized;
import com.apitrary.api.response.Response;
import com.apitrary.api.response.normalized.Normalizer;

public class NormalizationUtil {

	@SuppressWarnings("unchecked")
	public static <T> Normalizer getNormalizer(Response<T> response) {
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
