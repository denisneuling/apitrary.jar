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
package com.apitrary.api.transport;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.apitrary.api.ApitraryApi;
import com.apitrary.api.exception.ApiTransportException;

/**
 * <p>ApiClientTransportFactory class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 * @since 0.1.1
 */
public class ApiClientTransportFactory {

	private List<Class<Transport>> availableTransports = new LinkedList<Class<Transport>>();

	/**
	 * <p>Constructor for ApiClientTransportFactory.</p>
	 */
	public ApiClientTransportFactory() {
		registerTransport("com.apitrary.api.transport.javanet.JavaNetClientTransport");
		registerTransport("com.apitrary.api.transport.cxf.CXFClientTransport");
		registerTransport("com.apitrary.api.transport.httpclient.HttpClientTransport");
	}

	/**
	 * <p>Getter for the field <code>availableTransports</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Class<Transport>> getAvailableTransports() {
		return availableTransports;
	}

	/**
	 * <p>newTransport.</p>
	 *
	 * @param apitraryApi a {@link com.apitrary.api.ApitraryApi} object.
	 * @return a {@link com.apitrary.api.transport.Transport} object.
	 */
	public Transport newTransport(ApitraryApi apitraryApi) {
		List<Class<Transport>> knownTransports = getAvailableTransports();
		if (knownTransports.isEmpty()) {
			throw new ApiTransportException("No transport provider available. Is there one on the classpath?");
		}
		return newTransport(apitraryApi, knownTransports.get(knownTransports.size() - 1));
	}

	/**
	 * <p>newTransport.</p>
	 *
	 * @param apitraryApi a {@link com.apitrary.api.ApitraryApi} object.
	 * @param transportClazz a {@link java.lang.Class} object.
	 * @return a {@link com.apitrary.api.transport.Transport} object.
	 */
	public Transport newTransport(ApitraryApi apitraryApi, Class<Transport> transportClazz) {
		try {
			Transport transport = transportClazz.newInstance();
			transport.setApitraryApi(apitraryApi);
			return transport;
		} catch (IllegalAccessException e) {
			throw new ApiTransportException(e);
		} catch (InstantiationException e) {
			throw new ApiTransportException(e);
		}
	}

	/**
	 * <p>registerTransport.</p>
	 *
	 * @param transportClazz a {@link java.lang.String} object.
	 */
	@SuppressWarnings("unchecked")
	public void registerTransport(String transportClazz) {
		if(transportClazz == null){
			return;
		}
		try {
			registerTransport((Class<Transport>)Class.forName(transportClazz));
		} catch (ClassNotFoundException e) {
			return;
		} catch(ClassCastException cce){
			throw new ApiTransportException(cce);
		}
	}

	/**
	 * <p>registerTransport.</p>
	 *
	 * @param transportClazz a {@link java.lang.Class} object.
	 */
	public void registerTransport(Class<Transport> transportClazz) {
		if(transportClazz == null){
			return;
		}
		
		HashSet<Class<Transport>> set = new HashSet<Class<Transport>>(availableTransports); 
		set.add(transportClazz);
		
		availableTransports = new LinkedList<Class<Transport>>(set);
	}
}
