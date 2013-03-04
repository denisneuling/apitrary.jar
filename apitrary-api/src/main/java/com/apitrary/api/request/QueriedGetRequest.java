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
package com.apitrary.api.request;

import java.io.Serializable;

import com.apitrary.api.annotation.Method;
import com.apitrary.api.annotation.Path;
import com.apitrary.api.annotation.PathVariable;
import com.apitrary.api.annotation.Query;
import com.apitrary.api.annotation.Required;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.response.QueriedGetResponse;

/**
 * <p>
 * SearchRequest class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
@Method(HttpMethod.GET)
@Path("/${entity}")
/* a/id || a?q= */
public class QueriedGetRequest extends Request<QueriedGetResponse> implements Serializable {
	private static final long serialVersionUID = 7586680719718414828L;

	@Required
	@PathVariable("${entity}")
	private String entity;

	@Query("q")
	private String query;

	/**
	 * <p>
	 * Constructor for SearchRequest.
	 * </p>
	 */
	public QueriedGetRequest() {
	}

	/**
	 * <p>
	 * Getter for the field <code>entity</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * <p>
	 * Setter for the field <code>entity</code>.
	 * </p>
	 * 
	 * @param entity
	 *            a {@link java.lang.String} object.
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * <p>
	 * Getter for the field <code>query</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * <p>
	 * Setter for the field <code>query</code>.
	 * </p>
	 * 
	 * @param query
	 *            a {@link java.lang.String} object.
	 */
	public void setQuery(String query) {
		this.query = query;
	}
}
