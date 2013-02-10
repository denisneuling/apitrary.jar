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
package com.apitrary.orm.core.exception;

/**
 * <p>
 * DaoSupportUninitializedException class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class DaoSupportUninitializedException extends ApitraryOrmException {
	private static final long serialVersionUID = -7712804435364904885L;

	/**
	 * <p>
	 * Constructor for DaoSupportUninitializedException.
	 * </p>
	 * 
	 * @param message
	 *            a {@link java.lang.String} object.
	 */
	public DaoSupportUninitializedException(String message) {
		super(message);
	}
}
