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
package com.apitrary.api.transport.cxf;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.apitrary.api.exception.ApiTransportException;
import com.apitrary.api.transport.TransportResult;

/**
 * <p>CXFClientTransportResult class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class CXFClientTransportResult implements TransportResult {
	protected Logger log = Logger.getLogger(CXFClientTransportResult.class);

	private Response cxfResponse;
	private int statusCode;
	private String result;

	/**
	 * <p>Constructor for CXFClientTransportResult.</p>
	 *
	 * @param cxfResponse a {@link javax.ws.rs.core.Response} object.
	 */
	public CXFClientTransportResult(Response cxfResponse) {
		this.cxfResponse = cxfResponse;
		
		readIn();
	}

	private void readIn() {
		String content = null;
		try {
			this.statusCode = cxfResponse.getStatus();
			
			content = IOUtils.toString((InputStream)cxfResponse.getEntity());
			this.result = content;
		} catch (IOException e) {
			throw new ApiTransportException(e);
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
