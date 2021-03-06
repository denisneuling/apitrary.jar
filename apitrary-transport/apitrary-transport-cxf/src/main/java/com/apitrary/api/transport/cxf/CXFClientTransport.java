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

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;

import com.apitrary.api.ApitraryApi;
import com.apitrary.api.transport.Transport;
import com.apitrary.api.transport.TransportResult;

/**
 * <p>
 * CXFClientTransport class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class CXFClientTransport extends Transport {
	protected Logger log = Logger.getLogger(getClass());

	/**
	 * <p>
	 * Constructor for CXFClientTransport.
	 * </p>
	 */
	public CXFClientTransport() {
	}

	/**
	 * <p>
	 * Constructor for CXFClientTransport.
	 * </p>
	 * 
	 * @param apitraryApi
	 *            a {@link com.apitrary.api.ApitraryApi} object.
	 */
	public CXFClientTransport(ApitraryApi apitraryApi) {
		super(apitraryApi);
	}

	/** Constant <code>apitraryUrl="api.apitrary.com"</code> */
	protected static final String apitraryUrl = "apiv2.apitrary.com";

	/** Constant <code>protocol="http://"</code> */
	protected static final String protocol = "http://";

	/** Constant <code>DEFAULTCONNECTIONTIMEOUT=60000</code> */
	protected static final int DEFAULTCONNECTIONTIMEOUT = 60000;

	/** Constant <code>DEFAULTRECEIVETIMEOUT=60000</code> */
	protected static final int DEFAULTRECEIVETIMEOUT = 60000;

	/**
	 * <p>
	 * instantiateWebClient.
	 * </p>
	 * 
	 * @param uri
	 *            a {@link java.net.URI} object.
	 * @return a {@link org.apache.cxf.jaxrs.client.WebClient} object.
	 */
	protected WebClient instantiateWebClient(URI uri) {
		WebClient webClient = WebClient.create(uri);
		webClient = webClient.accept(MediaType.APPLICATION_JSON);
		webClient = webClient.header(apiAuthHeaderKey, getApitraryApi().getApiKey());
		webClient = webClient.header("Content-Type", contentType);

		HTTPConduit conduit = WebClient.getConfig(webClient).getHttpConduit();
		TLSClientParameters params = conduit.getTlsClientParameters();
		if (params == null) {
			params = new TLSClientParameters();
			conduit.setTlsClientParameters(params);
		}
		params.setDisableCNCheck(true);

		HTTPClientPolicy policy = new HTTPClientPolicy();
		policy.setConnectionTimeout(DEFAULTCONNECTIONTIMEOUT);
		policy.setReceiveTimeout(DEFAULTRECEIVETIMEOUT);
		policy.setAllowChunking(false);
		conduit.setClient(policy);

		return webClient;
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doGet(URI uri) {
		WebClient webClient = instantiateWebClient(uri);
		webClient = webClient.replaceQuery(uri.getQuery());

		log.debug("GET " + webClient.getCurrentURI());

		Response cxfResponse = webClient.get();

		return new CXFClientTransportResult(cxfResponse);
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doPost(URI uri, String payload) {
		WebClient webClient = instantiateWebClient(uri);
		webClient = webClient.replaceQuery(uri.getQuery());

		log.debug("POST " + webClient.getCurrentURI() + " application/json: " + payload);

		Response cxfResponse = webClient.post(payload);

		return new CXFClientTransportResult(cxfResponse);
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doPut(URI uri, String payload) {
		WebClient webClient = instantiateWebClient(uri);
		webClient = webClient.replaceQuery(uri.getQuery());

		log.debug("PUT " + webClient.getCurrentURI() + " application/json: " + payload);

		Response cxfResponse = webClient.put(payload);

		return new CXFClientTransportResult(cxfResponse);
	}

	/** {@inheritDoc} */
	@Override
	public TransportResult doDelete(URI uri) {
		WebClient webClient = instantiateWebClient(uri);
		webClient = webClient.replaceQuery(uri.getQuery());

		log.debug("DELETE " + webClient.getCurrentURI());

		Response cxfResponse = webClient.delete();

		return new CXFClientTransportResult(cxfResponse);
	}

}
