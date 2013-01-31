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
package com.apitrary.orm.core.mapping.api;

import java.util.List;

import com.apitrary.api.response.Response;

/**
 * <p>
 * ListMapper interface.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public interface ListMapper<T extends Response<T>> {

	/**
	 * <p>
	 * unMarshall.
	 * </p>
	 * 
	 * @param response
	 *            a {@link com.apitrary.api.response.Response} object.
	 * @param entity
	 *            a {@link java.lang.Class} object.
	 * @return a {@link java.util.List} object.
	 */
	public List<Object> unMarshall(Response<T> response, Class<?> entity);
}
