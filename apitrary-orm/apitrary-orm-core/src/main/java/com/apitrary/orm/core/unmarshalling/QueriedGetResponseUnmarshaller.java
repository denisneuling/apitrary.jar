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

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;

import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.api.response.QueriedGetResponse;
import com.apitrary.api.response.Response;
import com.apitrary.orm.core.ApitraryDaoSupport;
import com.apitrary.orm.core.annotations.Codec;
import com.apitrary.orm.core.annotations.Column;
import com.apitrary.orm.core.annotations.Id;
import com.apitrary.orm.core.annotations.Reference;
import com.apitrary.orm.core.exception.ApitraryOrmIdException;
import com.apitrary.orm.core.exception.MappingException;
import com.apitrary.orm.core.unmarshalling.api.ListUnmarshaller;
import com.apitrary.orm.core.util.ProxyUtil;

/**
 * <p>
 * QueriedGetResponseMapper class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class QueriedGetResponseUnmarshaller extends JsonResponseConsumer implements ListUnmarshaller<QueriedGetResponse> {
	protected Logger log = Logger.getLogger(getClass());

	private Class<?> entityClazz;

	private boolean resultStarted = false;
	private boolean entityStarted = false;
	private boolean idFound = false;

	private List<Object> resultSet = new LinkedList<Object>();
	private Object entityInstance;

	private ApitraryDaoSupport daoSupport;

	/**
	 * <p>
	 * Constructor for QueriedGetResponseUnmarshaller.
	 * </p>
	 *
	 * @param daoSupport
	 *            a {@link com.apitrary.orm.core.ApitraryDaoSupport} object.
	 */
	public QueriedGetResponseUnmarshaller(ApitraryDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	/** {@inheritDoc} */
	@Override
	public List<Object> unMarshall(Response<QueriedGetResponse> response, Class<?> entity) {
		this.entityClazz = entity;
		try {
			consume(response.getResult());

			return resultSet;
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

		if (resultStarted && entityStarted) {
			idFound = false;
			entityStarted = false;
			getResultSet().add(getEntityInstance());
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onStartArray(String fieldName, JsonParser jp) {
		log.trace(fieldName);

		if (!resultStarted) {
			resultStarted = true;
		} else if (!entityStarted) {
			entityStarted = true;
			entityInstance = newEntityInstance();
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onStartObject(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/** {@inheritDoc} */
	@Override
	protected void onEndObject(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/** {@inheritDoc} */
	@Override
	protected void onBoolean(Boolean bool, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + bool);

		if (resultStarted && entityStarted && idFound && fieldName != null && bool != null) {
			ClassUtil.setSilent(getEntityInstance(), fieldName, bool);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onInt(Integer val, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + val);

		if (resultStarted && entityStarted && idFound && fieldName != null && val != null) {
			ClassUtil.setSilent(getEntityInstance(), fieldName, val);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onDouble(Double floating, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + floating);

		if (resultStarted && entityStarted && idFound && fieldName != null && floating != null) {
			ClassUtil.setSilent(getEntityInstance(), fieldName, floating);
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onString(String text, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + text);

		if (resultStarted && entityStarted) {
			if (!idFound) {
				List<java.lang.reflect.Field> fields = ClassUtil.getAnnotatedFields(getEntityInstance().getClass(), Id.class);
				if (fields.isEmpty() || fields.size() > 1) {
					throw new ApitraryOrmIdException("Illegal amount of annotated id properties of class " + getEntityInstance().getClass().getName());
				} else {
					ClassUtil.setSilent(getEntityInstance(), fields.get(0).getName(), text);
					idFound = true;
				}
			} else if (text != null && idFound) {
				java.lang.reflect.Field field = ClassUtil.getDeclaredFieldSilent(getEntityInstance().getClass(), fieldName);
				if (field != null) {
					if (field.isAnnotationPresent(Reference.class)) {
						ClassUtil.setSilent(getEntityInstance(), fieldName, ProxyUtil.createLazyProxy(field.getType(), daoSupport, text));
					} else if (field.isAnnotationPresent(Column.class)) {
						if (field.isAnnotationPresent(Codec.class)) {
							Class<? extends com.apitrary.orm.core.codec.Codec> codecClazz = ClassUtil.getFieldAnnotationValue("value", field, Codec.class, Class.class);
							com.apitrary.orm.core.codec.Codec codec = (com.apitrary.orm.core.codec.Codec) ClassUtil.newInstance(codecClazz);
							Object value = codec.decode(text);

							ClassUtil.setSilent(getEntityInstance(), fieldName, value);
						} else {
							ClassUtil.setSilent(getEntityInstance(), fieldName, text);
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * newEntityInstance.
	 * </p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	protected Object newEntityInstance() {
		return ClassUtil.newInstance(entityClazz);
	}

	/**
	 * <p>
	 * Getter for the field <code>entityInstance</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	protected Object getEntityInstance() {
		return entityInstance;
	}

	/**
	 * <p>
	 * Getter for the field <code>resultSet</code>.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	protected List<Object> getResultSet() {
		return resultSet;
	}
}
