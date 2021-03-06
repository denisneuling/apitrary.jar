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
package com.apitrary.api;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>
 * ApitraryApi class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 * @since 0.1.1
 */
public class ApitraryApi {

	private String protocol = "http";
	private static final String DOT = ".";
	
	private String apiId;
	private String apiKey;
	
	private String apiVersion= "apiv2";
	private String authority = "apitrary.com";

	/**
	 * <p>
	 * Constructor for ApitraryApi.
	 * </p>
	 *
	 * @param apiId
	 *            a {@link java.lang.String} object.
	 * @param apiKey
	 *            a {@link java.lang.String} object.
	 */
	public ApitraryApi(String apiId, String apiKey) {
		this.apiId = apiId;
		this.apiKey = apiKey;
	}

	/**
	 * <p>
	 * Getter for the field <code>apiId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getApiId() {
		return apiId;
	}

	/**
	 * <p>
	 * Setter for the field <code>apiId</code>.
	 * </p>
	 *
	 * @param apiId
	 *            a {@link java.lang.String} object.
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	/**
	 * <p>
	 * Getter for the field <code>apiKey</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * <p>
	 * Setter for the field <code>apiKey</code>.
	 * </p>
	 *
	 * @param apiKey
	 *            a {@link java.lang.String} object.
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * <p>Getter for the field <code>apiVersion</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getApiVersion() {
		return apiVersion;
	}

	/**
	 * <p>Setter for the field <code>apiVersion</code>.</p>
	 *
	 * @param apiVersion a {@link java.lang.String} object.
	 */
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	/**
	 * <p>Getter for the field <code>authority</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * <p>Setter for the field <code>authority</code>.</p>
	 *
	 * @param authority a {@link java.lang.String} object.
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	/**
	 * <p>getURL.</p>
	 *
	 * @return a {@link java.net.URL} object.
	 * @throws java.net.MalformedURLException if any.
	 */
	public URL getURL() throws MalformedURLException{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(apiId);
		if(apiVersion!=null){
			stringBuilder.append(DOT);
			stringBuilder.append(apiVersion);
		}
		stringBuilder.append(DOT);
		stringBuilder.append(authority);
		URL url = new URL((protocol!=null?protocol+"://":"")+stringBuilder.toString());
		return url;
	}
}
