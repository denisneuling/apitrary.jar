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
package com.apitrary.api.transport.javanet;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.apitrary.api.exception.ApiTransportException;
import com.apitrary.api.transport.TransportResult;

/**
 * <p>JavaNetClientTransportResult class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class JavaNetClientTransportResult implements TransportResult {
	protected Logger log = Logger.getLogger(getClass());

	private int statusCode = -1;
	private HttpURLConnection httpConnection;
	private String result;

	/**
	 * <p>Constructor for JavaNetClientTransportResult.</p>
	 *
	 * @param httpConnection a {@link java.net.HttpURLConnection} object.
	 */
	public JavaNetClientTransportResult(HttpURLConnection httpConnection) {
		this.httpConnection = httpConnection;
		
		readIn();
	}
	
	private void readIn(){
		String content = null;
		try {
			this.statusCode = httpConnection.getResponseCode();
			
			content = IOUtils.toString(httpConnection.getInputStream());
			this.result = content;
		} catch (IOException e) {
			if(statusCode == 500){
				try {
					content = IOUtils.toString(httpConnection.getErrorStream());
				} catch (IOException e1) {
					throw new ApiTransportException(e1);
				}
				this.result = content;
			}else{
				throw new ApiTransportException(e);
			}
		} finally {
			httpConnection.disconnect();
		}
		log.debug(this);
	}

	/** {@inheritDoc} */
	@Override
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
		return statusCode + " " + (result.length()<=300?result:result.substring(0, 300)+"...");
	}
}
