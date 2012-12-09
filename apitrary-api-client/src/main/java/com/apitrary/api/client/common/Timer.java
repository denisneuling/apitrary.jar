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
package com.apitrary.api.client.common;

import java.util.Date;

/**
 * <p>
 * Timer class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class Timer {

	private Date start;
	private long difference = 0;

	/**
	 * <p>tic.</p>
	 *
	 * @return a {@link com.apitrary.api.client.common.Timer} object.
	 */
	public static Timer tic() {
		Timer timer = new Timer();
		timer.start = new Date();
		return timer;
	}

	/**
	 * <p>toc.</p>
	 *
	 * @return a long.
	 */
	public long toc() {
		Date end = new Date();
		difference = end.getTime() - start.getTime();

		return difference;
	}

	/**
	 * <p>Getter for the field <code>difference</code>.</p>
	 *
	 * @return a long.
	 */
	public long getDifference() {
		return difference;
	}
}
