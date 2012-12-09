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
