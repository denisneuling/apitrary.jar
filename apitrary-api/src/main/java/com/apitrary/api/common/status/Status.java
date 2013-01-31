/*
 * Copyright 2012 Denis Neuling 
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
 * Status class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class Status implements Serializable {
	private static final long serialVersionUID = -7581417160205836756L;

	private String db_status;
	private API api;

	/**
	 * <p>
	 * Constructor for Status.
	 * </p>
	 */
	public Status() {
	}

	/**
	 * <p>
	 * Getter for the field <code>db_status</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getDb_status() {
		return db_status;
	}

	/**
	 * <p>
	 * Setter for the field <code>db_status</code>.
	 * </p>
	 * 
	 * @param db_status
	 *            a {@link java.lang.String} object.
	 */
	public void setDb_status(String db_status) {
		this.db_status = db_status;
	}

	/**
	 * <p>
	 * Getter for the field <code>api</code>.
	 * </p>
	 * 
	 * @return a {@link com.apitrary.api.common.status.API} object.
	 */
	public API getApi() {
		return api;
	}

	/**
	 * <p>
	 * Setter for the field <code>api</code>.
	 * </p>
	 * 
	 * @param api
	 *            a {@link com.apitrary.api.common.status.API} object.
	 */
	public void setApi(API api) {
		this.api = api;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Status [db_status=" + db_status + ", api=" + api + "]";
	}
}
