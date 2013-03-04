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
package com.apitrary.orm.core.internal.conf;

import java.util.HashMap;

/**
 * <p>
 * ApitraryOrmConfiguration class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class ApitraryOrmConfiguration {

	private static ApitraryOrmConfiguration INSTANCE;
	private HashMap<String, String> conf = new HashMap<String, String>();

	private ApitraryOrmConfiguration() {
		initialize();
	}

	private static ApitraryOrmConfiguration getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ApitraryOrmConfiguration();
		}
		return INSTANCE;
	}

	private void initialize() {
		// "conf.show.json"
	}

	/**
	 * <p>
	 * showJson.
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public static boolean showJson() {
		String result = getInstance().conf.get("conf.show.json");
		if ("true".equalsIgnoreCase(result) || "1".equals(result)) {
			return true;
		}
		return false;
		// return true;
	}

}
