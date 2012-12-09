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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class ClassUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T getClassAnnotationValue(Class source, Class annotation, String attributeName, Class<T> expected) {
		Annotation instance = source.getAnnotation(annotation);
		T value = null;
		if (instance != null) {
			try {
				value = (T) instance.annotationType().getMethod(attributeName).invoke(instance);
			} catch (Exception ex) {
			}
		}
		return value;
	}

	public static <T> List<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		List<Field> annotatedFields = new LinkedList<Field>();
		Field[] allFields = getAllDeclaredFields(clazz);
		for (Field field : allFields) {
			if (null != (field.getAnnotation(annotationClass))) {
				annotatedFields.add(field);
			}
		}
		return annotatedFields;
	}

	public static Field[] getAllDeclaredFields(Class<?> clazz) {
		Field[] declaredFields = clazz.getDeclaredFields();
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			declaredFields = ArrayUtil.concat(declaredFields, getAllDeclaredFields(superClass));
		}
		return declaredFields;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValueOf(Field field, Object reference, Class<?> referenceClazz, Class<T> valueType) {
		try {
			field.setAccessible(true);
			Object toReturn = (T) field.get(reference);
			if(String.class.isInstance(valueType.getClass()) && !String.class.isInstance(toReturn.getClass())){
				toReturn = toReturn.toString();
			}
			return (T) toReturn;
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T getValueOf(String fieldName, Object reference, Class<?> referenceClazz, Class<T> valueType) {
		try {
			Field field = referenceClazz.getDeclaredField(fieldName);
			field.setAccessible(true);

			@SuppressWarnings("unchecked")
			T toReturn = (T) field.get(reference);
			return toReturn;
		} catch (Exception e) {
			return null;
		}
	}

	public static Object getValueOfField(Field field, Object ref) {
		field.setAccessible(true);
		Object value = null;
		try {
			value = field.get(ref);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return value;
	}
}