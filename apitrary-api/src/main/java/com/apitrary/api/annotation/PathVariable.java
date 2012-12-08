package com.apitrary.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface PathVariable {

	/**
	 * Defines the placeholder of the variable to set.
	 * 
	 * @return the placeholder of the variable to set.
	 */
	String value();
}