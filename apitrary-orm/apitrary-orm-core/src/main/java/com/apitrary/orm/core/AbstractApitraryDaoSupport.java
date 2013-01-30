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

import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.orm.annotations.Entity;
import com.apitrary.orm.annotations.Id;
import com.apitrary.orm.core.exception.ApitraryOrmIdException;
import com.apitrary.orm.core.scheme.SchemeCache;
import com.apitrary.orm.core.util.StringUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <p>Abstract AbstractApitraryDaoSupport class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
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
			entityName = StringUtil.toVerb(entity.getClass().getSimpleName());
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
			throw new ApitraryOrmIdException("Apitrary entity must own an annotated ID field.");
		}
		if(fields.size()>1){
			throw new ApitraryOrmIdException("Apitrary entity can not have more than one ID field.");
		}
		
		java.lang.reflect.Field field = fields.get(0);
		String id = ClassUtil.getValueOf(field, entity, entity.getClass(), String.class);
		if(id == null || id.isEmpty()){
			throw new ApitraryOrmIdException("Entity lacks ID and is probably not persisted.");
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
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(new ExclusionStrategy(){
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return "serialVersionUID".equals(f.getName())?true:false;
			}
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}}).create();
		return gson.toJson(entity);
	}
	
	/**
	 * <p>map.</p>
	 *
	 * @param entity a T object.
	 * @param result a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	@SuppressWarnings("unchecked")
	protected <T> T map(T entity, String result){
		Gson gson = new GsonBuilder().create();
		return (T) gson.fromJson(result, entity.getClass());
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
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(result, entity);
	}
}
