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
package com.apitrary.orm.core;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.orm.core.scheme.SchemeCache;

import com.apitrary.orm.annotations.Entity;
import com.apitrary.orm.annotations.Id;

/**
 * <p>Abstract AbstractApitraryDaoSupport class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 * @version $Id: $Id
 */
public abstract class AbstractApitraryDaoSupport {

	protected SchemeCache schemeCache = new SchemeCache();
	
	/**
	 * <p>resolveApitraryEntity.</p>
	 *
	 * @param entity a T object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.String} object.
	 */
	protected <T> String resolveApitraryEntity(T entity){
		String entityName = ClassUtil.getClassAnnotationValue(entity.getClass(), Entity.class, "value", String.class);
		if(entityName==null){
			// TODO just the first letter to lower
			entityName = StringUtils.uncapitalize(entity.getClass().getSimpleName());
		}
		return entityName;
	}
	
	/**
	 * <p>resolveApitraryEntityId.</p>
	 *
	 * @param entity a T object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.String} object.
	 */
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
	
	/**
	 * <p>dump.</p>
	 *
	 * @param entity a T object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.String} object.
	 */
	protected <T> String dump(T entity){
		// TODO implement
		return "{}";
	}
	
	/**
	 * <p>map.</p>
	 *
	 * @param entity a T object.
	 * @param result a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	protected <T> T map(T entity, String result){
		// TODO implement
		return entity;
	}
	
	/**
	 * <p>map.</p>
	 *
	 * @param entity a {@link java.lang.Class} object.
	 * @param result a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	protected <T> T map(Class<T> entity, String result){
		// TODO implement
		return null;
	}
}
