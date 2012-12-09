package com.apitrary.orm.core.constraint;

public class EntityTableResolver {

	public String resolve(Class<?> entityClazz){
		return null;
	}
	
	public String resolve(Object entity){
		return resolve(entity.getClass());
	}
}
