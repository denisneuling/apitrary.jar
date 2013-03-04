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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apitrary.api.annotation.Default;
import com.apitrary.api.annotation.Path;
import com.apitrary.api.annotation.PathVariable;
import com.apitrary.api.request.Request;

/**
 * <p>
 * PathUtil class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class PathUtil {

	private static final String PATTERN = "\\$\\{(.*?)*\\}(.*?)";

	/**
	 * <p>
	 * resolveResourcePath.
	 * </p>
	 * 
	 * @param request
	 *            a {@link com.apitrary.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	public static <T> String resolveResourcePath(Request<T> request) {
		infixPotentialPathDefaults(request, request.getClass());

		Class<?> clazz = request.getClass();

		String unresolvedPath = ClassUtil.getClassAnnotationValue(clazz, Path.class, "value", String.class);

		Map<String, String> patternMap = new HashMap<String, String>();
		for (Field field : clazz.getDeclaredFields()) {
			PathVariable part = field.getAnnotation(PathVariable.class);
			if (part != null) {
				try {
					String pattern = part.value();
					field.setAccessible(true);
					String value = (String) field.get(request);
					patternMap.put(pattern, value);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(unresolvedPath);

		LinkedHashSet<String> placeholder = new LinkedHashSet<String>();
		while (matcher.find()) {
			placeholder.add(matcher.group());
		}

		for (String key : placeholder) {
			String found = patternMap.get(key);
			if (found != null) {
				unresolvedPath = unresolvedPath.replace((String) key, found);
			}
		}

		if (unresolvedPath.endsWith("/")) {
			unresolvedPath = unresolvedPath.substring(0, unresolvedPath.length() - 1);
		}
		return unresolvedPath;
	}

	private static <T> void infixPotentialPathDefaults(Request<T> request, Class<?> targetClazz) {
		if (targetClazz == null) {
			targetClazz = request.getClass();
		}

		Class<?> superClass = targetClazz.getSuperclass();
		if (superClass != null && superClass.equals(Request.class)) {
			infixPotentialPathDefaults(request, superClass);
		}

		Field[] declaredFields = targetClazz.getDeclaredFields();
		for (Field property : declaredFields) {
			Default defaultValue = property.getAnnotation(Default.class);
			if (null != defaultValue) {
				property.setAccessible(true);
				try {
					Object value = property.get(request);
					if (value == null) {
						property.set(request, defaultValue.value());
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
