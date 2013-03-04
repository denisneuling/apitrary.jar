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
import java.util.Arrays;

/**
 * <p>
 * APIState class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class APIState implements Serializable {
	private static final long serialVersionUID = -1978346945071607803L;

	private Info info;
	private Status status;
	private String[] schema;

	/**
	 * <p>
	 * Constructor for APIState.
	 * </p>
	 */
	public APIState() {
	}

	/**
	 * <p>
	 * Getter for the field <code>info</code>.
	 * </p>
	 * 
	 * @return a {@link com.apitrary.api.common.status.Info} object.
	 */
	public Info getInfo() {
		return info;
	}

	/**
	 * <p>
	 * Setter for the field <code>info</code>.
	 * </p>
	 * 
	 * @param info
	 *            a {@link com.apitrary.api.common.status.Info} object.
	 */
	public void setInfo(Info info) {
		this.info = info;
	}

	/**
	 * <p>
	 * Getter for the field <code>status</code>.
	 * </p>
	 * 
	 * @return a {@link com.apitrary.api.common.status.Status} object.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * <p>
	 * Setter for the field <code>status</code>.
	 * </p>
	 * 
	 * @param status
	 *            a {@link com.apitrary.api.common.status.Status} object.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * <p>
	 * Getter for the field <code>schema</code>.
	 * </p>
	 * 
	 * @return an array of {@link java.lang.String} objects.
	 */
	public String[] getSchema() {
		return schema;
	}

	/**
	 * <p>
	 * Setter for the field <code>schema</code>.
	 * </p>
	 * 
	 * @param schema
	 *            an array of {@link java.lang.String} objects.
	 */
	public void setSchema(String[] schema) {
		this.schema = schema;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "APIState [info=" + info + ", status=" + status + ", schema=" + Arrays.toString(schema) + "]";
	}
}
