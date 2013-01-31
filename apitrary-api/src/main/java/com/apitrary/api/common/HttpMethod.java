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
package com.apitrary.api.common;

/**
 * <p>
 * HttpMethod class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public enum HttpMethod {

	/**
	 * Http GET method descriptor.
	 * 
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Get
	 */
	GET,

	/**
	 * HTTP POST method descriptor.
	 * 
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Post
	 */
	POST,

	/**
	 * HTTP PUT method descriptor.
	 * 
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Put
	 */
	PUT,

	/**
	 * HTTP DELETE method descriptor.
	 * 
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Delete
	 */
	DELETE,

	/**
	 * HTTP HEAD method descriptor.
	 * 
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Head
	 */
	HEAD,

	/**
	 * HTTP TRACE method descriptor.
	 * 
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Trace
	 */
	TRACE,

	/**
	 * HTTP CONNECT method descriptor.
	 * 
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Connect
	 */
	CONNECT,

	/**
	 * HTTP OPTIONS method descriptor.
	 * 
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Options
	 */
	OPTIONS;

}
