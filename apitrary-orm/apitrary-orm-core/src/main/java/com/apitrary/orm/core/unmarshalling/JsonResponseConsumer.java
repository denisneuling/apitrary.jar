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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import com.apitrary.orm.core.internal.conf.ApitraryOrmConfiguration;

/**
 * <p>
 * Abstract JsonResponseConsumer class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public abstract class JsonResponseConsumer {
	protected Logger log = Logger.getLogger(getClass());

	/**
	 * <p>
	 * consume.
	 * </p>
	 *
	 * @param json
	 *            a {@link java.lang.String} object.
	 * @throws org.codehaus.jackson.JsonParseException
	 *             if any.
	 * @throws java.io.IOException
	 *             if any.
	 */
	public void consume(String json) throws JsonParseException, IOException {
		if (ApitraryOrmConfiguration.showJson()) {
			showJson(json);
		}

		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createJsonParser(json);

		JsonToken token = jp.nextToken();
		String fieldName = jp.getCurrentName();
		while (!(token == null && fieldName == null)) {
			on(token, fieldName, jp);

			token = jp.nextToken();
			fieldName = jp.getCurrentName();
		}
	}

	/**
	 * <p>
	 * on.
	 * </p>
	 *
	 * @param token
	 *            a {@link org.codehaus.jackson.JsonToken} object.
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 * @throws org.codehaus.jackson.JsonParseException
	 *             if any.
	 * @throws java.io.IOException
	 *             if any.
	 */
	protected void on(JsonToken token, String fieldName, JsonParser jp) throws JsonParseException, IOException {
		switch (token) {
			case START_OBJECT:
				onStartObject(fieldName, jp);
				break;
			case END_OBJECT:
				onEndObject(fieldName, jp);
				break;
			case START_ARRAY:
				onStartArray(fieldName, jp);
				break;
			case END_ARRAY:
				onEndArray(fieldName, jp);
				break;
			case FIELD_NAME:
				onFieldName(fieldName, jp);
				break;
			case VALUE_EMBEDDED_OBJECT:
				// TODO
				break;

			case VALUE_STRING:
				onString(jp.getText(), fieldName, jp);
				break;
			case VALUE_NUMBER_INT:
				onInt(jp.getValueAsInt(), fieldName, jp);
				break;
			case VALUE_NUMBER_FLOAT:
				onDouble(jp.getValueAsDouble(), fieldName, jp);
				break;
			case VALUE_TRUE:
				onBoolean(true, fieldName, jp);
				break;
			case VALUE_FALSE:
				onBoolean(false, fieldName, jp);
				break;
			case VALUE_NULL:
				onNull(fieldName, jp);
				break;
			case NOT_AVAILABLE:
				break;
			default:
				log.warn("Unhandled Token " + token + " found for field " + fieldName);
		}
	}

	/**
	 * <p>
	 * onFieldName.
	 * </p>
	 *
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onFieldName(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/**
	 * <p>
	 * onEndArray.
	 * </p>
	 *
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onEndArray(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/**
	 * <p>
	 * onStartArray.
	 * </p>
	 *
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onStartArray(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/**
	 * <p>
	 * onEndObject.
	 * </p>
	 *
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onEndObject(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/**
	 * <p>
	 * onStartObject.
	 * </p>
	 *
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onStartObject(String fieldName, JsonParser jp) {
		log.trace(fieldName);
	}

	/**
	 * <p>
	 * onBoolean.
	 * </p>
	 *
	 * @param bool
	 *            a {@link java.lang.Boolean} object.
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onBoolean(Boolean bool, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + bool);
	}

	/**
	 * <p>
	 * onInt.
	 * </p>
	 *
	 * @param val
	 *            a {@link java.lang.Integer} object.
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onInt(Integer val, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + val);
	}

	/**
	 * <p>
	 * onDouble.
	 * </p>
	 *
	 * @param floating
	 *            a {@link java.lang.Double} object.
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onDouble(Double floating, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + floating);
	}

	/**
	 * <p>
	 * onString.
	 * </p>
	 *
	 * @param text
	 *            a {@link java.lang.String} object.
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onString(String text, String fieldName, JsonParser jp) {
		log.trace(fieldName + " " + text);
	}

	/**
	 * <p>
	 * onNull.
	 * </p>
	 *
	 * @param fieldName
	 *            a {@link java.lang.String} object.
	 * @param jp
	 *            a {@link org.codehaus.jackson.JsonParser} object.
	 */
	protected void onNull(String fieldName, JsonParser jp) {
		log.trace("Got null value for field " + fieldName);
	}

	/**
	 * <p>
	 * showJson.
	 * </p>
	 *
	 * @param json
	 *            a {@link java.lang.String} object.
	 */
	public void showJson(String json) {
		try {
			JsonFactory jsonFactory = new JsonFactory();
			ObjectMapper objectMapper = new ObjectMapper();
			StringWriter stringWriter = new StringWriter();

			JsonParser jsonParser = jsonFactory.createJsonParser(new StringReader(json));
			JsonNode jsonNode = objectMapper.readTree(jsonParser);
			JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(stringWriter);
			jsonGenerator.useDefaultPrettyPrinter();

			objectMapper.writeTree(jsonGenerator, jsonNode);

			jsonGenerator.flush();
			jsonGenerator.close();

			/*
			 * TODO decide upon logger usage
			 */
			// String lineSeparator = System.getProperty("line.separator");
			// log.info((lineSeparator != null ? lineSeparator : "") +
			// stringWriter.toString());

			System.out.println(stringWriter.toString());
		} catch (Throwable throwable) {
			// hmm, somewhat accidentally crashed?
		}
	}
}
