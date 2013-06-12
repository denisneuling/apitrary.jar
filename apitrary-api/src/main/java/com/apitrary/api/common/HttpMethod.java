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
	 * @see <a
	 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Get">rfc2616
	 *      GET</a>
	 */
	GET,

	/**
	 * HTTP POST method descriptor.
	 * 
	 * @see <a
	 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Post">rfc2616
	 *      POST</a>
	 */
	POST,

	/**
	 * HTTP PUT method descriptor.
	 * 
	 * @see <a
	 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Put">rfc2616
	 *      PUT</a>
	 */
	PUT,

	/**
	 * HTTP DELETE method descriptor.
	 * 
	 * @see <a
	 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Delete">rfc2616
	 *      DELETE</a>
	 */
	DELETE,

	/**
	 * HTTP HEAD method descriptor.
	 * 
	 * @see <a
	 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Head">rfc2616
	 *      HEAD</a>
	 */
	HEAD,

	/**
	 * HTTP TRACE method descriptor.
	 * 
	 * @see <a
	 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Trace">rfc2616
	 *      TRACE</a>
	 */
	TRACE,

	/**
	 * HTTP CONNECT method descriptor.
	 * 
	 * @see <a
	 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Connect">rfc2616
	 *      CONNECT</a>
	 */
	CONNECT,

	/**
	 * HTTP OPTIONS method descriptor.
	 * 
	 * @see <a
	 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#Options">rfc2616
	 *      OPTIONS</a>
	 */
	OPTIONS;

}
