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
import org.apache.log4j.Logger;

import com.apitrary.api.exception.ApiTransportException;
import com.apitrary.api.transport.TransportResult;

/**
 * <p>HttpClientTransportResult class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class HttpClientTransportResult implements TransportResult{
	protected Logger log = Logger.getLogger(HttpClientTransportResult.class);

	private HttpResponse response;
	
	private int statusCode;
	private String result;
	
	/**
	 * <p>Constructor for HttpClientTransportResult.</p>
	 *
	 * @param response a {@link org.apache.http.HttpResponse} object.
	 */
	public HttpClientTransportResult(HttpResponse response){
		this.response = response;
		
		readIn();
	}
	
	private void readIn(){
		String content = null;
		this.statusCode = response.getStatusLine().getStatusCode();
		try {
			if(this.statusCode!=204){
				content = IOUtils.toString(response.getEntity().getContent());
				this.result = content;
			}
		} catch (IOException e) {
			throw new ApiTransportException(e);
		} finally {
			try {
				if(this.statusCode!=204){
					response.getEntity().getContent().close();
				}
			} catch (IllegalStateException e) {
				// fuck it
			} catch (IOException e) {
				// fuck it
			}
		}
		log.debug(this);
	}

	/**
	 * <p>Getter for the field <code>statusCode</code>.</p>
	 *
	 * @return a int.
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/** {@inheritDoc} */
	@Override
	public String getResult() {
		return result;
	}
	
	@Override
	public String toString() {
		return statusCode + (result!=null? " " + (result.length()<=300?result:result.substring(0, 300)+"..."):"");
	}
}
