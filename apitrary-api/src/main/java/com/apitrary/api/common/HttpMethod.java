package com.apitrary.api.common;

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
