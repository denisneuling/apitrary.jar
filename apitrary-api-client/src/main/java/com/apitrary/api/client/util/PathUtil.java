package com.apitrary.api.client.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apitrary.api.annotation.Path;
import com.apitrary.api.annotation.PathVariable;
import com.apitrary.api.request.Request;

public class PathUtil {

	private static final String PATTERN = "\\$\\{(.*?)*\\}(.*?)";

	/**
	 * <p>
	 * resolveResourcePath.
	 * </p>
	 *
	 * @param request
	 *            a {@link com.cloudcontrolled.api.request.Request} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	public static <T> String resolveResourcePath(Request<T> request) {
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

		return unresolvedPath;
	}
}