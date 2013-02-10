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
package com.apitrary.orm.core.unmarshalling;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;

import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.api.response.GetResponse;
import com.apitrary.api.response.Response;
import com.apitrary.orm.annotations.Column;
import com.apitrary.orm.annotations.Reference;
import com.apitrary.orm.core.ApitraryDaoSupport;
import com.apitrary.orm.core.exception.MappingException;
import com.apitrary.orm.core.unmarshalling.api.Unmarshaller;
import com.apitrary.orm.core.util.ProxyUtil;

/**
 * <p>
 * GetResponseMapper class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class GetResponseUnmarshaller extends JsonResponseConsumer implements Unmarshaller<GetResponse> {
	protected Logger log = Logger.getLogger(getClass());

	private boolean found = false;
	private boolean resultStarted = false;
	private Object entity;

	private ApitraryDaoSupport daoSupport;

	/**
	 * <p>
	 * Constructor for GetResponseUnmarshaller.
	 * </p>
	 * 
	 * @param daoSupport
	 *            a {@link com.apitrary.orm.core.ApitraryDaoSupport} object.
	 */
	public GetResponseUnmarshaller(ApitraryDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	/** {@inheritDoc} */
	@Override
	public Object unMarshall(Response<GetResponse> response, Object entity) {
		return unMarshall(response, entity.getClass());
	}

	/** {@inheritDoc} */
	@Override
	public Object unMarshall(Response<GetResponse> response, Class<?> entity) {
		this.entity = ClassUtil.newInstance(entity);

		try {
			consume(response.getResult());

			if (found) {
				return this.entity;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new MappingException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onFieldName(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/** {@inheritDoc} */
	@Override
	protected void onEndArray(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/** {@inheritDoc} */
	@Override
	protected void onStartArray(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/** {@inheritDoc} */
	@Override
	protected void onStartObject(String fieldName, JsonParser jp) {
		log.trace(fieldName);

		if ("result".equals(fieldName)) {
			resultStarted = true;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onEndObject(String fieldName, JsonParser jp) {
		log.trace(fieldName);

		if ("result".equals(fieldName)) {
			resultStarted = false;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onBoolean(Boolean bool, String fieldName, JsonParser jp) {
		if (resultStarted) {
			found = true;
			ClassUtil.setSilent(entity, fieldName, bool);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onInt(Integer val, String fieldName, JsonParser jp) {
		if (resultStarted) {
			found = true;
			ClassUtil.setSilent(entity, fieldName, val);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onDouble(Double floating, String fieldName, JsonParser jp) {
		if (resultStarted) {
			found = true;
			ClassUtil.setSilent(entity, fieldName, floating);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onString(String text, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + text);

		if (resultStarted) {
			found = true;
			java.lang.reflect.Field field = ClassUtil.getDeclaredFieldSilent(entity.getClass(), fieldName);
			if (field != null) {
				if (field.isAnnotationPresent(Reference.class)) {
					ClassUtil.setSilent(entity, fieldName, ProxyUtil.createLazyProxy(field.getType(), daoSupport, text));
				} else if (field.isAnnotationPresent(Column.class)) {
					ClassUtil.setSilent(entity, fieldName, text);
				}
			}
		}
	}
}
