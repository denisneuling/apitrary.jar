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
package com.apitrary.orm.core.unmarshalling;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.apitrary.api.response.GetResponse;
import com.apitrary.orm.core.ApitraryDaoSupport;
import com.apitrary.orm.core.marshalling.dumb.DumbEntity;

/**
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class GetResponseUnmarshallerTest {

	private GetResponseUnmarshaller getResponseUnmarshaller;
	private ApitraryDaoSupport apitraryDaoSupport;

	private String result = "{" + "\"result\": {" + "\"a\":\"a\",\"b\":\"encoded\"" + "}," + "\"statusMessage\": \"OK\"," + "\"statusCode\": 200" + "}";

	@Before
	public void setUp() {
		getResponseUnmarshaller = new GetResponseUnmarshaller(apitraryDaoSupport);
	}

	@Test
	public void test_unMarshall() {
		GetResponse getResponse = new GetResponse();
		getResponse.setResult(result);

		DumbEntity dumbEntity = (DumbEntity) getResponseUnmarshaller.unMarshall(getResponse, DumbEntity.class);
		Assert.assertEquals("decoded", dumbEntity.getB());
	}
}
