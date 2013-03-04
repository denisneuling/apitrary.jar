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

import org.codehaus.jackson.JsonParser;

import com.apitrary.api.response.PutResponse;
import com.apitrary.api.response.Response;
import com.apitrary.orm.core.exception.MappingException;
import com.apitrary.orm.core.unmarshalling.api.Unmarshaller;

/**
 * <p>
 * PutResponseMapper class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class PutResponseUnmarshaller extends JsonResponseConsumer implements Unmarshaller<PutResponse> {

	private String statusMessage = new String();

	/** {@inheritDoc} */
	@Override
	public Object unMarshall(Response<PutResponse> response, Object entity) {
		return unMarshall(response, entity.getClass());
	}

	/** {@inheritDoc} */
	@Override
	public Object unMarshall(Response<PutResponse> response, Class<?> entity) {
		try {
			consume(response.getResult());

			return statusMessage;
		} catch (Exception e) {
			throw new MappingException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void onString(String text, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + text);

		if ("statusMessage".equals(fieldName)) {
			this.statusMessage = text;
		}
	}
}
