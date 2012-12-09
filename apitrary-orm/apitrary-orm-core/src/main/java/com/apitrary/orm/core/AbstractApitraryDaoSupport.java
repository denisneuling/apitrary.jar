package com.apitrary.orm.core;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.orm.core.scheme.SchemeCache;

import com.apitrary.orm.annotations.Entity;
import com.apitrary.orm.annotations.Id;

public abstract class AbstractApitraryDaoSupport {

	protected SchemeCache schemeCache = new SchemeCache();
	
	protected <T> String resolveApitraryEntity(T entity){
		String entityName = ClassUtil.getClassAnnotationValue(entity.getClass(), Entity.class, "value", String.class);
		if(entityName==null){
			// TODO just the first letter to lower
			entityName = StringUtils.uncapitalize(entity.getClass().getSimpleName());
		}
		return entityName;
	}
	
	protected <T> String resolveApitraryEntityId(T entity){
		List<java.lang.reflect.Field> fields = ClassUtil.getAnnotatedFields(entity.getClass(), Id.class);
		if(fields.isEmpty()){
			// TODO throw exception
		}
		if(fields.size()>1){
			// TODO throw exception
		}
		
		java.lang.reflect.Field field = fields.get(0);
		String id = ClassUtil.getValueOf(field, entity, entity.getClass(), String.class);
		if(id == null || id.isEmpty()){
			// TODO throw exception
		}
		return id;
	}
	
	protected <T> String dump(T entity){
		// TODO implement
		return "{}";
	}
	
	protected <T> T map(T entity, String result){
		// TODO implement
		return entity;
	}
	
	protected <T> T map(Class<T> entity, String result){
		// TODO implement
		return null;
	}
}
