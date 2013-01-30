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

import com.apitrary.api.client.ApitraryClient;
import com.apitrary.api.request.APIStateRequest;
import com.apitrary.api.request.AddRequest;
import com.apitrary.api.request.DeleteRequest;
import com.apitrary.api.request.SearchRequest;
import com.apitrary.api.request.UpdateRequest;
import com.apitrary.api.response.APIStateResponse;
import com.apitrary.api.response.AddResponse;
import com.apitrary.api.response.DeleteResponse;
import com.apitrary.api.response.SearchResponse;
import com.apitrary.api.response.UpdateResponse;
import com.apitrary.orm.core.internal.model.APIState;

/**
 * <p>ApitraryDaoSupport class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class ApitraryDaoSupport extends AbstractApitraryDaoSupport {

	protected ApitraryClient apitraryClient;

	/**
	 * <p>Constructor for ApitraryDaoSupport.</p>
	 */
	public ApitraryDaoSupport() {
	}

	/**
	 * <p>Getter for the field <code>apitraryClient</code>.</p>
	 *
	 * @return a {@link com.apitrary.api.client.ApitraryClient} object.
	 */
	public ApitraryClient getApitraryClient() {
		return apitraryClient;
	}

	/**
	 * <p>Setter for the field <code>apitraryClient</code>.</p>
	 *
	 * @param apitraryClient a {@link com.apitrary.api.client.ApitraryClient} object.
	 */
	public void setApitraryClient(ApitraryClient apitraryClient) {
		this.apitraryClient = apitraryClient;
	}

	/**
	 * <p>save.</p>
	 *
	 * @param entity a T object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	public <T> T save(T entity) {
		AddRequest request = new AddRequest();

		request.setEntity(resolveApitraryEntity(entity));
		request.setRequestPayload(dump(entity));

		AddResponse response = apitraryClient.send(request);

		return this.map(entity, response.getResult());
	}

	/**
	 * <p>update.</p>
	 *
	 * @param entity a T object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	public <T> T update(T entity) {
		UpdateRequest request = new UpdateRequest();

		request.setEntity(resolveApitraryEntity(entity));
		request.setId(resolveApitraryEntityId(entity));
		request.setRequestPayload(dump(entity));

		UpdateResponse response = apitraryClient.send(request);

		return this.map(entity, response.getResult());
	}

	/**
	 * <p>delete.</p>
	 *
	 * @param entity a T object.
	 * @param <T> a T object.
	 */
	public <T> void delete(T entity) {
		DeleteRequest request = new DeleteRequest();

		request.setEntity(resolveApitraryEntity(entity));
		request.setId(resolveApitraryEntityId(entity));

		DeleteResponse response = apitraryClient.send(request);

		// ???
		response.getResult();
	}

	/**
	 * TODO use mixin
	 * 
	 * <p>refresh.</p>
	 *
	 * @param entity a T object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	public <T> T refresh(T entity) {

		SearchRequest request = new SearchRequest();

		request.setEntity(resolveApitraryEntity(entity));

		request.setId(resolveApitraryEntityId(entity));

		SearchResponse response = apitraryClient.send(request);
		
		entity = this.map(entity, response.getResult());

		return entity;
	}

	/**
	 * <p>loadById.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param entity a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	public <T> T findById(String id, Class<T> entity) {

		SearchRequest request = new SearchRequest();

		request.setEntity(resolveApitraryEntity(entity));

		request.setId(id);

		SearchResponse response = apitraryClient.send(request);

		return this.map(entity, response.getResult());
	}

	/**
	 * <p>find.</p>
	 *
	 * @param riakQuery a {@link java.lang.String} object.
	 * @param entity a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String riakQuery, Class<T> entity) {

		SearchRequest request = new SearchRequest();

		request.setEntity(resolveApitraryEntity(entity));
		
		request.setQuery(riakQuery);

		SearchResponse response = apitraryClient.send(request);

		return ((List<T>)this.map(List.class, response.getResult()));
	}
	
	public APIState getAPIState(){
		APIStateRequest request = new APIStateRequest();
		APIStateResponse response = apitraryClient.send(request);
		return this.map(APIState.class, response.getResult());
	}
}
