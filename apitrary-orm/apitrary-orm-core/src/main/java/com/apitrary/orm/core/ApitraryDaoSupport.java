package com.apitrary.orm.core;

import java.util.List;

import com.apitrary.api.client.ApitraryClient;
import com.apitrary.api.request.AddRequest;
import com.apitrary.api.request.DeleteRequest;
import com.apitrary.api.request.SearchRequest;
import com.apitrary.api.request.UpdateRequest;
import com.apitrary.api.response.AddResponse;
import com.apitrary.api.response.DeleteResponse;
import com.apitrary.api.response.SearchResponse;
import com.apitrary.api.response.UpdateResponse;

public class ApitraryDaoSupport extends AbstractApitraryDaoSupport {

	protected ApitraryClient apitraryClient;

	public ApitraryDaoSupport() {
	}

	public ApitraryClient getApitraryClient() {
		return apitraryClient;
	}

	public void setApitraryClient(ApitraryClient apitraryClient) {
		this.apitraryClient = apitraryClient;
	}

	public <T> T save(T entity) {

		AddRequest request = new AddRequest();

		request.setEntity(resolveApitraryEntity(entity));

		request.setRequestPayload(dump(entity));

		AddResponse response = apitraryClient.send(request);

		return this.map(entity, response.getResult());
	}

	public <T> T update(T entity) {

		UpdateRequest request = new UpdateRequest();

		request.setEntity(resolveApitraryEntity(entity));

		request.setId(resolveApitraryEntityId(entity));

		request.setRequestPayload(dump(entity));

		UpdateResponse response = apitraryClient.send(request);

		return this.map(entity, response.getResult());
	}

	public <T> void delete(T entity) {

		DeleteRequest request = new DeleteRequest();

		request.setEntity(resolveApitraryEntity(entity));

		request.setId(resolveApitraryEntityId(entity));

		DeleteResponse response = apitraryClient.send(request);

		// ???
		response.getResult();
	}

	public <T> T refresh(T entity) {

		SearchRequest request = new SearchRequest();

		request.setEntity(resolveApitraryEntity(entity));

		request.setId(resolveApitraryEntityId(entity));

		SearchResponse response = apitraryClient.send(request);
		
		entity = this.map(entity, response.getResult());

		return entity;
	}

	public <T> T loadById(String id, Class<T> entity) {

		SearchRequest request = new SearchRequest();

		request.setEntity(resolveApitraryEntity(entity));

		request.setId(id);

		SearchResponse response = apitraryClient.send(request);

		return this.map(entity, response.getResult());
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String riakQuery, Class<T> entity) {

		SearchRequest request = new SearchRequest();

		request.setEntity(resolveApitraryEntity(entity));
		
		request.setQuery(riakQuery);

		SearchResponse response = apitraryClient.send(request);

		return ((List<T>)this.map(List.class, response.getResult()));
	}
}
