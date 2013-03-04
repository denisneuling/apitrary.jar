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
package com.apitrary.api.response;

import java.io.Serializable;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class GeneralResponseTest {

	@Test
	public void test_isSerializable() {
		Assert.assertTrue(Serializable.class.isAssignableFrom(APIStateResponse.class));
		Assert.assertTrue(Serializable.class.isAssignableFrom(GetResponse.class));
		Assert.assertTrue(Serializable.class.isAssignableFrom(DeleteResponse.class));
		Assert.assertTrue(Serializable.class.isAssignableFrom(PostResponse.class));
		Assert.assertTrue(Serializable.class.isAssignableFrom(PutResponse.class));
		Assert.assertTrue(Serializable.class.isAssignableFrom(QueriedGetResponse.class));
		Assert.assertTrue(Serializable.class.isAssignableFrom(Response.class));
	}

}
