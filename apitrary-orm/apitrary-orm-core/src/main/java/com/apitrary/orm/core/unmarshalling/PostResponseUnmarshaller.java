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
package com.apitrary.orm.core.unmarshalling;

import java.util.List;

import org.codehaus.jackson.JsonParser;

import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.api.response.PostResponse;
import com.apitrary.api.response.Response;
import com.apitrary.orm.core.annotations.Id;
import com.apitrary.orm.core.exception.ApitraryOrmIdException;
import com.apitrary.orm.core.exception.MappingException;
import com.apitrary.orm.core.unmarshalling.api.Unmarshaller;

/**
 * <p>
 * PostResponseMapper class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class PostResponseUnmarshaller extends JsonResponseConsumer implements Unmarshaller<PostResponse> {

	private Object entity;

	/** {@inheritDoc} */
	@Override
	public Object unMarshall(Response<PostResponse> response, Object entity) {
		this.entity = entity;
		return unMarshall(response, entity.getClass());
	}

	/** {@inheritDoc} */
	@Override
	public Object unMarshall(Response<PostResponse> response, Class<?> entity) {
		try {
			consume(response.getResult());

			return this.entity;
		} catch (Exception e) {
			throw new MappingException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onString(String text, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + text);

		if ("_id".equals(fieldName)) {
			List<java.lang.reflect.Field> fields = ClassUtil.getAnnotatedFields(entity.getClass(), Id.class);
			if (fields.isEmpty() || fields.size() > 1) {
				throw new ApitraryOrmIdException("Illegal amount of annotated id properties of class " + entity.getClass().getName());
			} else {
				ClassUtil.setSilent(this.entity, fields.get(0).getName(), text);
			}
		}
	}
}
