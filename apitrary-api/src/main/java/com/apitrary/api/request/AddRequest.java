package com.apitrary.api.request;

import java.util.HashMap;

import com.apitrary.api.annotation.Body;
import com.apitrary.api.annotation.Method;
import com.apitrary.api.annotation.Path;
import com.apitrary.api.annotation.PathVariable;
import com.apitrary.api.annotation.Required;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.response.AddResponse;
import com.google.gson.JsonObject;

@Method(HttpMethod.POST)
@Path("/${entity}")
public class AddRequest extends Request<AddResponse>{
	private static final long serialVersionUID = 7454490450438849781L;

	@Required
	@PathVariable("${entity}")
	private String entity;
	
	@Body
	private String requestPayload;
	
	public AddRequest(){
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getRequestPayload() {
		return requestPayload;
	}
	
	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
	}

	public void setRequestPayload(JsonObject requestPayload) {
		this.requestPayload = requestPayload.getAsString();
	}
	
	public void setRequestPayload(HashMap<String, Object> requestPayload) {
		String jsonString = getGsonInstance().toJson(requestPayload);
		this.requestPayload = jsonString;
	}
}
