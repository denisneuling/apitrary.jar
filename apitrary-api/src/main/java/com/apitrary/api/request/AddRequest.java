package com.apitrary.api.request;

import com.apitrary.api.annotation.Method;
import com.apitrary.api.annotation.Path;
import com.apitrary.api.annotation.PathVariable;
import com.apitrary.api.annotation.Required;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.response.AddResponse;

@Method(HttpMethod.POST)
@Path("/${entity}")
public class AddRequest extends Request<AddResponse>{
	private static final long serialVersionUID = 7454490450438849781L;

	@Required
	@PathVariable("${entity}")
	private String entity;
}
