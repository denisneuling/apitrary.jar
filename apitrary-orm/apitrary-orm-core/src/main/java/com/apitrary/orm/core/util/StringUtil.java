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
package com.apitrary.orm.core.util;

/**
 * <p>
 * StringUtil class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class StringUtil {

	/**
	 * <p>
	 * toVerb.
	 * </p>
	 *
	 * @param string
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String toVerb(String string) {
		if (string == null) {
			return string;
		}
		if (string.length() == 1) {
			return string.toLowerCase();
		}
		return string.substring(0, 1).toLowerCase() + string.substring(1, string.length());
	}
}
