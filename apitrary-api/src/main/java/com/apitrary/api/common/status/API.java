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
package com.apitrary.api.common.status;

import java.io.Serializable;

/**
 * <p>
 * API class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class API implements Serializable {
	private static final long serialVersionUID = 1795632555829461430L;

	private String api_id;
	private String api_version;

	/**
	 * <p>
	 * Constructor for API.
	 * </p>
	 */
	public API() {
	}

	/**
	 * <p>
	 * Getter for the field <code>api_id</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getApi_id() {
		return api_id;
	}

	/**
	 * <p>
	 * Setter for the field <code>api_id</code>.
	 * </p>
	 * 
	 * @param api_id
	 *            a {@link java.lang.String} object.
	 */
	public void setApi_id(String api_id) {
		this.api_id = api_id;
	}

	/**
	 * <p>
	 * Getter for the field <code>api_version</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getApi_version() {
		return api_version;
	}

	/**
	 * <p>
	 * Setter for the field <code>api_version</code>.
	 * </p>
	 * 
	 * @param api_version
	 *            a {@link java.lang.String} object.
	 */
	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "API [api_id=" + api_id + ", api_version=" + api_version + "]";
	}
}
