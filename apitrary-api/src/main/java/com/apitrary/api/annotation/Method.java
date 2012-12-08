package com.apitrary.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.apitrary.api.common.HttpMethod;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Method {

	HttpMethod value();
}
