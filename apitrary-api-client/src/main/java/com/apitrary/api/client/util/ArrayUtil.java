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
package com.apitrary.api.client.util;

import java.util.Arrays;

/**
 * <p>
 * ArrayUtil class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class ArrayUtil {

	/**
	 * <p>
	 * concat.
	 * </p>
	 * 
	 * @param first
	 *            an array of T objects.
	 * @param second
	 *            an array of T objects.
	 * @param <T>
	 *            a T object.
	 * @return an array of T objects.
	 */
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, (first.length + second.length));
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
