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

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.apitrary.api.client.ApitraryClient;
import com.apitrary.api.client.common.HttpStatus;
import com.apitrary.api.client.exception.CommunicationErrorException;
import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.api.common.status.APIState;
import com.apitrary.api.request.APIStateRequest;
import com.apitrary.api.request.DeleteRequest;
import com.apitrary.api.request.GetRequest;
import com.apitrary.api.request.PostRequest;
import com.apitrary.api.request.PutRequest;
import com.apitrary.api.request.QueriedGetRequest;
import com.apitrary.api.response.APIStateResponse;
import com.apitrary.api.response.DeleteResponse;
import com.apitrary.api.response.GetResponse;
import com.apitrary.api.response.PostResponse;
import com.apitrary.api.response.PutResponse;
import com.apitrary.api.response.QueriedGetResponse;
import com.apitrary.orm.core.annotations.Entity;
import com.apitrary.orm.core.annotations.Id;
import com.apitrary.orm.core.cascade.CascadeDeleteCapable;
import com.apitrary.orm.core.exception.ApitraryOrmDeleteException;
import com.apitrary.orm.core.exception.ApitraryOrmException;
import com.apitrary.orm.core.exception.ApitraryOrmIdDefinitionsException;
import com.apitrary.orm.core.exception.ApitraryOrmIdException;
import com.apitrary.orm.core.exception.ApitraryOrmUpdateException;
import com.apitrary.orm.core.exception.DaoSupportUninitializedException;
import com.apitrary.orm.core.exception.MappingException;
import com.apitrary.orm.core.marshalling.PayloadMarshaller;
import com.apitrary.orm.core.unmarshalling.DeleteResponseUnmarshaller;
import com.apitrary.orm.core.unmarshalling.GetResponseUnmarshaller;
import com.apitrary.orm.core.unmarshalling.PostResponseUnmarshaller;
import com.apitrary.orm.core.unmarshalling.PutResponseUnmarshaller;
import com.apitrary.orm.core.unmarshalling.QueriedGetResponseUnmarshaller;
import com.apitrary.orm.core.util.StringUtil;

/**
 * <p>
 * ApitraryDaoSupport class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class ApitraryDaoSupport {
	protected Logger log = Logger.getLogger(getClass());

	protected ApitraryClient apitraryClient;

	/**
	 * <p>
	 * Constructor for ApitraryDaoSupport.
	 * </p>
	 */
	public ApitraryDaoSupport() {
	}

	/**
	 * <p>
	 * Getter for the field <code>apitraryClient</code>.
	 * </p>
	 * 
	 * @return a {@link com.apitrary.api.client.ApitraryClient} object.
	 */
	public ApitraryClient getApitraryClient() {
		return apitraryClient;
	}

	/**
	 * <p>
	 * Setter for the field <code>apitraryClient</code>.
	 * </p>
	 * 
	 * @param apitraryClient
	 *            a {@link com.apitrary.api.client.ApitraryClient} object.
	 */
	public void setApitraryClient(ApitraryClient apitraryClient) {
		this.apitraryClient = apitraryClient;
	}

	/**
	 * <p>
	 * save.
	 * </p>
	 * 
	 * @param entity
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 * @return a T object.
	 */
	@SuppressWarnings("unchecked")
	public <T> T save(T entity) {
		if (entity == null) {
			throw new ApitraryOrmException("Cannot persist null entity");
		}
		log.debug("Saving " + entity.getClass());

		PostRequest request = new PostRequest();
		request.setEntity(resolveApitraryEntity(entity));

		String payload = marshall(entity);

		request.setRequestPayload(payload);

		PostResponse response = resolveApitraryClient().send(request);

		if (HttpStatus.Created.ordinal() == response.getStatusCode()) {
			return (T) new PostResponseUnmarshaller().unMarshall(response, entity);
		} else {
			throw new ApitraryOrmException(response.getResult());
		}
	}

	/**
	 * <p>
	 * update.
	 * </p>
	 * 
	 * @param entity
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 * @return a T object.
	 */
	public <T> T update(T entity) {
		if (entity == null) {
			throw new ApitraryOrmException("Cannot update null entity");
		}

		log.debug("Updating " + entity.getClass());

		PutRequest request = new PutRequest();
		request.setEntity(resolveApitraryEntity(entity));
		request.setId(resolveApitraryEntityId(entity));

		String payload = marshall(entity);

		request.setRequestPayload(payload);

		PutResponse response = resolveApitraryClient().send(request);

		if (HttpStatus.No_Content.ordinal() != response.getStatusCode()) {
			if (HttpStatus.Not_Found.ordinal() == response.getStatusCode()) {
				throw new ApitraryOrmUpdateException("Cannot update object: object with given id does not exist.");
			} else {
				String statusMessage = (String) new PutResponseUnmarshaller().unMarshall(response, entity);
				throw new ApitraryOrmUpdateException(statusMessage);
			}
		}
		return entity;
	}

	/**
	 * <p>
	 * delete.
	 * </p>
	 * 
	 * @param entity
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 */
	public <T> void delete(T entity) {
		if (entity == null) {
			throw new ApitraryOrmException("Cannot delete null entity");
		}

		log.debug("Deleting " + entity.getClass());

		DeleteRequest request = new DeleteRequest();
		request.setEntity(resolveApitraryEntity(entity));
		String id = resolveApitraryEntityId(entity);
		request.setId(id);

		new CascadeDeleteCapable(this).deleteCascades(entity);

		DeleteResponse response = resolveApitraryClient().send(request);
		if (HttpStatus.OK.ordinal() != response.getStatusCode()) {
			if (HttpStatus.Not_Found.ordinal() == response.getStatusCode()) {
				throw new ApitraryOrmDeleteException("Object with id " + id + " does not exist.");
			} else {
				String statusMessage = (String) new DeleteResponseUnmarshaller().unMarshall(response, entity);
				throw new ApitraryOrmDeleteException(statusMessage);
			}
		}
	}

	/**
	 * <p>
	 * findById.
	 * </p>
	 * 
	 * @param id
	 *            a {@link java.lang.String} object.
	 * @param entity
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a T object.
	 */
	@SuppressWarnings("unchecked")
	public <T> T findById(String id, Class<T> entity) {
		if (entity == null) {
			throw new ApitraryOrmException("Cannot access null entity");
		}
		if (id == null || id.isEmpty()) {
			return null;
		}

		log.debug("Searching " + entity + " " + id);

		GetRequest request = new GetRequest();
		request.setEntity(resolveApitraryEntity(entity));
		request.setId(id);
		GetResponse response = resolveApitraryClient().send(request);

		T result = (T) new GetResponseUnmarshaller(this).unMarshall(response, entity);

		if (result != null) {
			List<Field> fields = ClassUtil.getAnnotatedFields(entity, Id.class);
			if (fields.isEmpty() || fields.size() > 1) {
				throw new ApitraryOrmIdException("Illegal amount of annotated id properties of class " + entity.getClass().getName());
			} else {
				ClassUtil.setSilent(result, fields.get(0).getName(), id);
			}
		}
		return result;
	}

	/**
	 * <p>
	 * find.
	 * </p>
	 * 
	 * @param riakQuery
	 *            a {@link java.lang.String} object.
	 * @param entity
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.util.List} object.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String riakQuery, Class<T> entity) {
		if (entity == null) {
			throw new ApitraryOrmException("Cannot access null entity");
		}
		log.debug("Searching " + entity + " " + riakQuery);

		QueriedGetRequest request = new QueriedGetRequest();
		request.setEntity(resolveApitraryEntity(entity));
		request.setQuery(riakQuery);
		QueriedGetResponse response = resolveApitraryClient().send(request);

		return (List<T>) new QueriedGetResponseUnmarshaller(this).unMarshall(response, entity);
	}

	/**
	 * <p>
	 * findAll.
	 * </p>
	 * 
	 * @param entity
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.util.List} object.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> entity) {
		if (entity == null) {
			throw new ApitraryOrmException("Cannot access null entity");
		}

		log.debug("Loading all " + entity);

		QueriedGetRequest request = new QueriedGetRequest();
		request.setEntity(resolveApitraryEntity(entity));
		QueriedGetResponse response = resolveApitraryClient().send(request);

		if (HttpStatus.OK.ordinal() == response.getStatusCode()) {
			return (List<T>) new QueriedGetResponseUnmarshaller(this).unMarshall(response, entity);
		} else {
			/*
			 * happens more often than expected...
			 */
			// if(HttpStatus.Internal_Server_Error.ordinal() ==
			// response.getStatusCode()){
			// throw new
			// CommunicationErrorException(HttpStatus.Internal_Server_Error);
			// }
			throw new CommunicationErrorException(HttpStatus.getStatus(response.getStatusCode()));
		}
	}

	/**
	 * <p>
	 * getAPIState.
	 * </p>
	 * 
	 * @return a {@link com.apitrary.orm.core.internal.model.APIState} object.
	 */
	public APIState getAPIState() {
		APIStateRequest request = new APIStateRequest();
		APIStateResponse response = resolveApitraryClient().send(request);

		APIState apiState = null;
		if (HttpStatus.OK.ordinal() == response.getStatusCode()) {
			String result = response.getResult();

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				apiState = objectMapper.readValue(result, APIState.class);
			} catch (Exception e) {
				throw new MappingException(e);
			}
		} else {
			throw new CommunicationErrorException(HttpStatus.getStatus(response.getStatusCode()));
		}
		return apiState;
	}

	/**
	 * <p>
	 * resolveApitraryEntityId.
	 * </p>
	 * 
	 * @param entity
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	public <T> String resolveApitraryEntityId(T entity) {
		if (entity == null) {
			throw new ApitraryOrmIdDefinitionsException("Apitrary entity has not to be null.");
		}
		List<java.lang.reflect.Field> fields = ClassUtil.getAnnotatedFields(entity.getClass(), Id.class);
		if (fields.isEmpty()) {
			throw new ApitraryOrmIdDefinitionsException("Apitrary entity must own an annotated ID field.");
		}
		if (fields.size() > 1) {
			throw new ApitraryOrmIdDefinitionsException("Apitrary entity can not have more than one ID field.");
		}

		java.lang.reflect.Field field = fields.get(0);
		String id = ClassUtil.getValueOf(field, entity, entity.getClass(), String.class);
		if (id == null || id.isEmpty()) {
			throw new ApitraryOrmIdException("Entity lacks ID and is probably not persisted.");
		}
		return id;
	}

	/**
	 * <p>
	 * resolveApitraryEntity.
	 * </p>
	 * 
	 * @param entity
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	protected <T> String resolveApitraryEntity(T entity) {
		String entityName = ClassUtil.getClassAnnotationValue(entity.getClass(), Entity.class, "value", String.class);
		if (entityName == null || entityName.isEmpty()) {
			entityName = StringUtil.toVerb(entity.getClass().getSimpleName());
		}
		return entityName;
	}

	/**
	 * <p>
	 * resolveApitraryEntity.
	 * </p>
	 * 
	 * @param entity
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	protected <T> String resolveApitraryEntity(Class<T> entity) {
		String entityName = ClassUtil.getClassAnnotationValue(entity, Entity.class, "value", String.class);
		if (entityName == null || entityName.isEmpty()) {
			entityName = StringUtil.toVerb(entity.getSimpleName());
		}
		return entityName;
	}

	/**
	 * <p>
	 * marshall.
	 * </p>
	 * 
	 * @param entity
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link java.lang.String} object.
	 */
	protected <T> String marshall(T entity) {
		return new PayloadMarshaller(this).marshall(entity);
	}

	private ApitraryClient resolveApitraryClient() {
		if (apitraryClient == null) {
			throw new DaoSupportUninitializedException("ApitraryClient was not set.");
		}
		return apitraryClient;
	}
}
