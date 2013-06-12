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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * <p>
 * ClassUtil class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class ClassUtil {
	private static Logger log = Logger.getLogger(NormalizationUtil.class.getName());

	/**
	 * <p>
	 * getClassAnnotationValue.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Class} object.
	 * @param annotation
	 *            a {@link java.lang.Class} object.
	 * @param attributeName
	 *            a {@link java.lang.String} object.
	 * @param expected
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a T object.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T getClassAnnotationValue(Class source, Class annotation, String attributeName, Class<T> expected) {
		Annotation instance = source.getAnnotation(annotation);
		T value = null;
		if (instance != null) {
			try {
				value = (T) instance.annotationType().getMethod(attributeName).invoke(instance);
			} catch (Exception ex) {
				log.warning(ex.getClass().getSimpleName() + ": " + ex.getMessage());
			}
		}
		return value;
	}

	/**
	 * <p>
	 * getAnnotatedFields.
	 * </p>
	 *
	 * @param object
	 *            a {@link java.lang.Object} object.
	 * @param annotationClass
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.util.List} object.
	 */
	public static <T> List<Field> getAnnotatedFields(Object object, Class<? extends Annotation> annotationClass) {
		return getAnnotatedFields(object.getClass(), annotationClass);
	}

	/**
	 * <p>
	 * getAnnotatedFields.
	 * </p>
	 *
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @param annotationClass
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.util.List} object.
	 */
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

	/**
	 * <p>
	 * getAnnotatedFields.
	 * </p>
	 *
	 * @param object
	 *            a {@link java.lang.Object} object.
	 * @param annotationClasses
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.util.List} object.
	 */
	public static <T> List<Field> getAnnotatedFields(Object object, Class<? extends Annotation>... annotationClasses) {
		return getAnnotatedFields(object.getClass(), annotationClasses);
	}

	/**
	 * <p>
	 * getAnnotatedFields.
	 * </p>
	 *
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @param annotationClasses
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.util.List} object.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation>... annotationClasses) {
		Set<Field> annotatedFields = new HashSet<Field>();
		Field[] allFields = getAllDeclaredFields(clazz);
		for (Class annotationClass : annotationClasses) {
			for (Field field : allFields) {
				if (null != (field.getAnnotation(annotationClass))) {
					annotatedFields.add(field);
				}
			}
		}
		return new LinkedList<Field>(annotatedFields);
	}

	/**
	 * <p>
	 * getAllDeclaredFields.
	 * </p>
	 *
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @return an array of {@link java.lang.reflect.Field} objects.
	 */
	public static Field[] getAllDeclaredFields(Class<?> clazz) {
		Field[] declaredFields = clazz.getDeclaredFields();
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			declaredFields = ArrayUtil.concat(declaredFields, getAllDeclaredFields(superClass));
		}
		return declaredFields;
	}

	/**
	 * <p>
	 * getValueOf.
	 * </p>
	 *
	 * @param field
	 *            a {@link java.lang.reflect.Field} object.
	 * @param valueType
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a T object.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValueOf(Field field, Object reference, Class<?> referenceClazz, Class<T> valueType) {
		try {
			field.setAccessible(true);
			Object toReturn = (T) field.get(reference);
			if (String.class.isInstance(valueType.getClass()) && !String.class.isInstance(toReturn.getClass())) {
				toReturn = toReturn.toString();
			}
			return (T) toReturn;
		} catch (Exception e) {
			log.warning(e.getClass().getSimpleName() + ": " + e.getMessage());
			return null;
		}
	}

	/**
	 * <p>
	 * getValueOf.
	 * </p>
	 *
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param referenceClazz
	 *            a {@link java.lang.Class} object.
	 * @param valueType
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a T object.
	 */
	public static <T> T getValueOf(String fieldName, Object reference, Class<?> referenceClazz, Class<T> valueType) {
		try {
			Field field = referenceClazz.getDeclaredField(fieldName);
			field.setAccessible(true);

			@SuppressWarnings("unchecked")
			T toReturn = (T) field.get(reference);
			return toReturn;
		} catch (Exception e) {
			log.warning(e.getClass().getSimpleName() + ": " + e.getMessage());
			return null;
		}
	}

	/**
	 * <p>
	 * getValueOfField.
	 * </p>
	 *
	 * @param field
	 *            a {@link java.lang.reflect.Field} object.
	 * @param ref
	 *            a {@link java.lang.Object} object.
	 * @return a {@link java.lang.Object} object.
	 */
	public static Object getValueOfField(Field field, Object ref) {
		field.setAccessible(true);
		Object value = null;
		try {
			value = field.get(ref);
		} catch (IllegalArgumentException e) {
			log.warning(e.getClass().getSimpleName() + ": " + e.getMessage());
		} catch (IllegalAccessException e) {
			log.warning(e.getClass().getSimpleName() + ": " + e.getMessage());
		}
		return value;
	}

	/**
	 * <p>
	 * newInstance.
	 * </p>
	 *
	 * @param entity
	 *            a {@link java.lang.Class} object.
	 * @return a {@link java.lang.Object} object.
	 */
	public static Object newInstance(Class<?> entity) {
		if (entity == null) {
			return null;
		}
		try {
			return entity.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * setSilent.
	 * </p>
	 *
	 * @param target
	 *            a {@link java.lang.Object} object.
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param value
	 *            a {@link java.lang.Object} object.
	 * @return a {@link java.lang.Object} object.
	 */
	public static Object setSilent(Object target, String fieldName, Object value) {
		Class<?> targetClass = target.getClass();
		try {
			Field field = targetClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, box(value, field.getType()));
			return target;
		} catch (Exception e) {
			return target;
		}
	}

	/**
	 * <p>
	 * box.
	 * </p>
	 *
	 * @param property
	 *            a {@link java.lang.Object} object.
	 * @param to
	 *            a {@link java.lang.Class} object.
	 * @return a {@link java.lang.Object} object.
	 */
	public static Object box(Object property, Class<?> to) {
		if (property instanceof Exception) {
			throw new RuntimeException((Exception) property);
		}
		if (property == null) {
			return property;
		}

		if (property.getClass().equals(to)) {
			return property;
		}

		if (property instanceof String && String.class.isAssignableFrom(to)) {
			return property;
		}

		if ((property instanceof String || property instanceof Boolean) && Boolean.class.isAssignableFrom(to)) {
			if (((String) property).equalsIgnoreCase("true") || property.equals("1")) {
				return true;
			}
			if (((String) property).equalsIgnoreCase("false") || property.equals("0")) {
				return false;
			}
		}

		if ((property instanceof String || property instanceof Integer) && Integer.class.isAssignableFrom(to)) {
			if (((String) property).isEmpty()) {
				return 0;
			}
			return new Integer(property.toString());
		}

		if ((property instanceof String || property instanceof Integer || property instanceof Long) && Long.class.isAssignableFrom(to)) {
			if (((String) property).isEmpty()) {
				return 0L;
			}
			return new Long(property.toString());
		}

		if ((property instanceof String || property instanceof Double) && Double.class.isAssignableFrom(to)) {
			if (((String) property).isEmpty()) {
				return 0D;
			}
			return new Double(property.toString());
		}

		return to.cast(property);
	}

	/**
	 * <p>
	 * getDeclaredFieldSilent.
	 * </p>
	 *
	 * @param target
	 *            a {@link java.lang.Class} object.
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.reflect.Field} object.
	 */
	public static Field getDeclaredFieldSilent(Class<?> target, String fieldName) {
		try {
			return target.getDeclaredField(fieldName);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * getFieldAnnotationValue.
	 * </p>
	 *
	 * @param annotationProperty
	 *            a {@link java.lang.String} object.
	 * @param field
	 *            a {@link java.lang.reflect.Field} object.
	 * @param annotationClass
	 *            a {@link java.lang.Class} object.
	 * @param ofType
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a T object.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldAnnotationValue(String annotationProperty, Field field, Class<? extends Annotation> annotationClass, Class<T> ofType) {
		Object annotation = field.getAnnotation(annotationClass);

		T result = null;
		if (annotation != null) {
			try {
				Method method = annotationClass.getMethod(annotationProperty, new Class[] {});
				result = (T) method.invoke(annotation, new Object[] {});
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}
}
