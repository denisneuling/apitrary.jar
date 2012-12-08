package com.apitrary.api.request;

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
}
