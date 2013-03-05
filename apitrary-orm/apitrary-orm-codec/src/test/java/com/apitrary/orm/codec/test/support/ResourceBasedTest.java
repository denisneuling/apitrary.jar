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
package com.apitrary.orm.codec.test.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class ResourceBasedTest {

	protected InputStream resolveResourceInputStream(String fileName){
		String base = this.getClass().getName().replace(this.getClass().getSimpleName(), "").replace(".", "/");
		URL url = ResourceBasedTest.class.getClassLoader().getResource(base+"/"+fileName);
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
