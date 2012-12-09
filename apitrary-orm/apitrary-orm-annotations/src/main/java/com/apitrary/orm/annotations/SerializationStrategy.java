package com.apitrary.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface SerializationStrategy {

	/**
	 * The SerializationStrategy's class to define serialization method and vice
	 * versa
	 */
	Class<?> value();
}
