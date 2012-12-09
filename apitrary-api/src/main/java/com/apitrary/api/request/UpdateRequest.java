package com.apitrary.api.request;

import com.apitrary.api.annotation.Body;
import com.apitrary.api.annotation.Method;
import com.apitrary.api.annotation.Path;
import com.apitrary.api.annotation.PathVariable;
import com.apitrary.api.annotation.Required;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.response.UpdateResponse;

@Method(HttpMethod.PUT)
@Path("/${entity}/${id}")
public class UpdateRequest extends Request<UpdateResponse>{
	private static final long serialVersionUID = 7880320624981198826L;

	@Required
	@PathVariable("${entity}")
	private String entity;
	
	@Required
	@PathVariable("${id}")
	private String id;
	
	@Body
	private String requestPayload;
	
	public UpdateRequest(){
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequestPayload() {
		return requestPayload;
	}
	
	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
	}
}
