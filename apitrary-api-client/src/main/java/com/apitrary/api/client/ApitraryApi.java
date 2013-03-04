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
package com.apitrary.api.client;

/**
 * <p>
 * ApitraryApi class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class ApitraryApi {

	private String apiId;
	private String apiKey;

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
}
