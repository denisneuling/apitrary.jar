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
package com.apitrary.orm.core.cascade;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.orm.core.ApitraryDaoSupport;
import com.apitrary.orm.core.annotations.Reference;
import com.apitrary.orm.core.annotations.cascade.Cascade;
import com.apitrary.orm.core.exception.ApitraryOrmIdException;

/**
 * <p>
 * CascadeDeleteCapable class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class CascadeDeleteCapable {
	protected Logger log = Logger.getLogger(getClass());

	private ApitraryDaoSupport apitraryDaoSupport;

	/**
	 * <p>
	 * Constructor for CascadeDeleteCapable.
	 * </p>
	 * 
	 * @param apitraryDaoSupport
	 *            a {@link com.apitrary.orm.core.ApitraryDaoSupport} object.
	 */
	public CascadeDeleteCapable(ApitraryDaoSupport apitraryDaoSupport) {
		this.apitraryDaoSupport = apitraryDaoSupport;
	}

	/**
	 * <p>
	 * deleteCascades.
	 * </p>
	 * 
	 * @param entity
	 *            a {@link java.lang.Object} object.
	 */
	public void deleteCascades(Object entity) {
		if (entity != null) {
			List<Field> fields = ClassUtil.getAnnotatedFields(entity.getClass(), Reference.class);
			for (Field field : fields) {
				Cascade[] cascades = ClassUtil.getFieldAnnotationValue("cascade", field, Reference.class, Cascade[].class);
				if (Arrays.asList(cascades).contains(Cascade.DELETE)) {
					Object referencedEntity = ClassUtil.getValueOfField(field, entity);
					try {
						apitraryDaoSupport.resolveApitraryEntityId(referencedEntity);
					} catch (ApitraryOrmIdException aoie) {
						log.trace("Referenced entity cannot be null. Skipping.");
					}
					apitraryDaoSupport.delete(referencedEntity);
				}
			}
		}
	}
}
