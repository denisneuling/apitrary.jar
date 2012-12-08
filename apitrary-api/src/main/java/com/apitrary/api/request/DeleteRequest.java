package com.apitrary.api.request;

import com.apitrary.api.annotation.Method;
import com.apitrary.api.annotation.Path;
import com.apitrary.api.annotation.PathVariable;
import com.apitrary.api.annotation.Required;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.response.DeleteResponse;

@Method(HttpMethod.DELETE)
@Path("/${entity}/${id}")
public class DeleteRequest extends Request<DeleteResponse>{
	private static final long serialVersionUID = -8265792383976749317L;

	@Required
	@PathVariable("${entity}")
	private String entity;
	
	@Required
	@PathVariable("${id}")
	private String id;
}
