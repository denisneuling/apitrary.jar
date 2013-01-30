/**
 * 
 */
package com.apitrary.api.request;

import com.apitrary.api.annotation.Method;
import com.apitrary.api.annotation.Path;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.response.APIStateResponse;

/**
 * @author ska
 *
 */
@Method(HttpMethod.POST)
@Path("/")
public class APIStateRequest extends Request<APIStateResponse>{
	private static final long serialVersionUID = -2599282917840158769L;

	public APIStateRequest(){
	}
}
