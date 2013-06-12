/*
 * Copyright 2012-2013 Denis Neuling 
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
package com.apitrary.api.transport.httpclient;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import com.apitrary.api.exception.ApiTransportException;
import com.apitrary.api.transport.TransportResult;

/**
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class HttpClientTransportResult implements TransportResult{

	private HttpResponse response;
	
	private int statusCode;
	private String result;
	
	public HttpClientTransportResult(HttpResponse response){
		this.response = response;
		this.statusCode = response.getStatusLine().getStatusCode();
		
		readIn();
	}
	
	private void readIn(){
		String content = null;
		try {
			content = IOUtils.toString(response.getEntity().getContent());
			this.result = content;
		} catch (IOException e) {
			throw new ApiTransportException(e);
		} finally {
			try {
				response.getEntity().getContent().close();
			} catch (IllegalStateException e) {
				// fuck it
			} catch (IOException e) {
				// fuck it
			}
		}
	}

	public int getStatusCode() {
		return statusCode;
	}

	/** {@inheritDoc} */
	@Override
	public String getResult() {
		return result;
	}
}
