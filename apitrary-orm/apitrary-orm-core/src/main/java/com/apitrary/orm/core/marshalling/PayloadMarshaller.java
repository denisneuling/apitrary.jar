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
package com.apitrary.orm.core.marshalling;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.node.ObjectNode;

import com.apitrary.api.client.util.ClassUtil;
import com.apitrary.orm.core.ApitraryDaoSupport;
import com.apitrary.orm.core.codec.Codec;
import com.apitrary.orm.core.annotations.Column;
import com.apitrary.orm.core.annotations.Reference;
import com.apitrary.orm.core.annotations.cascade.Cascade;
import com.apitrary.orm.core.exception.ApitraryOrmIdException;
import com.apitrary.orm.core.json.filter.PropertyFilterMixIn;
import com.apitrary.orm.core.marshalling.api.Marshaller;

/**
 * <p>
 * PayloadMarshaller class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class PayloadMarshaller implements Marshaller {
	protected Logger log = Logger.getLogger(getClass());

	private ApitraryDaoSupport apitraryDaoSupport;

	/**
	 * <p>
	 * Constructor for PayloadMarshaller.
	 * </p>
	 * 
	 * @param apitraryDaoSupport
	 *            a {@link com.apitrary.orm.core.ApitraryDaoSupport} object.
	 */
	public PayloadMarshaller(ApitraryDaoSupport apitraryDaoSupport) {
		this.apitraryDaoSupport = apitraryDaoSupport;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> String marshall(T entity) {
		try {
			/*
			 * instantiate json object mapper
			 */
			ObjectMapper objectMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
			objectMapper.getSerializationConfig().addMixInAnnotations(Object.class, PropertyFilterMixIn.class);
			SimpleFilterProvider filters = new SimpleFilterProvider();

			/*
			 * Collect all column fields
			 */
			List<java.lang.reflect.Field> allFields = ClassUtil.getAnnotatedFields(entity, Column.class);
			
			/*
			 * Prepare property filter...
			 */
			List<java.lang.reflect.Field> fields = new LinkedList<java.lang.reflect.Field>();
			for(java.lang.reflect.Field field : allFields){
				if(!field.isAnnotationPresent(com.apitrary.orm.core.annotations.Codec.class)){
					fields.add(field);
				}
			}
			/*
			 *  ... and filter out all properties not referencing types or demand custom encoding
			 */
			String[] targetedFieldNames = new String[fields.size()];
			for (int i = 0; i < fields.size(); i++) {
				targetedFieldNames[i] = fields.get(i).getName();
			}
			filters.addFilter("PropertyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(targetedFieldNames));
			ObjectWriter objectWriter = objectMapper.writer(filters);
			String json = objectWriter.writeValueAsString(entity);

			/*
			 * Encode property the custom way
			 */
			List<java.lang.reflect.Field> customEncodedProperties = ClassUtil.getAnnotatedFields(entity, com.apitrary.orm.core.annotations.Codec.class);
			for (java.lang.reflect.Field field : customEncodedProperties) {
				
				Class<? extends Codec> codecClazz = ClassUtil.getFieldAnnotationValue("value", field, com.apitrary.orm.core.annotations.Codec.class, Class.class);
				Codec codec = (Codec) ClassUtil.newInstance(codecClazz);
				
				Object value = ClassUtil.getValueOfField(field, entity);
				String stringValue = codec.encode(value);
				json = addNode(json, field.getName(), stringValue);
			}
			
			/*
			 * Perform entity refs
			 */
			List<java.lang.reflect.Field> referencedEntities = ClassUtil.getAnnotatedFields(entity, Reference.class);
			for (java.lang.reflect.Field field : referencedEntities) {
				Cascade[] cascades = ClassUtil.getFieldAnnotationValue("cascade", field, Reference.class, Cascade[].class);
				if (cascades != null) {
					List<Cascade> cascadeList = Arrays.asList(cascades);
					Object referencedEntity = ClassUtil.getValueOfField(field, entity);
					if (referencedEntity != null) {
						String referencedEntityId = null;
						try {
							referencedEntityId = apitraryDaoSupport.resolveApitraryEntityId(referencedEntity);
						} catch (ApitraryOrmIdException aoie) {
							if (cascadeList.contains(Cascade.SAVE)) {
								referencedEntity = apitraryDaoSupport.save(referencedEntity);
								referencedEntityId = apitraryDaoSupport.resolveApitraryEntityId(referencedEntity);
							} else {
								continue;
							}
						}
						json = addNode(json, field.getName(), referencedEntityId);
					}
				}
			}
			return json;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String addNode(String json, String fieldName, String nodeValue) {

		JsonFactory jsonFactory = new JsonFactory();
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();

		try {
			JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(stringWriter);
			ObjectNode objectNode = objectMapper.readValue(json, ObjectNode.class);
			objectNode.put(fieldName, nodeValue);
			objectMapper.writeTree(jsonGenerator, objectNode);
			jsonGenerator.flush();
			jsonGenerator.close();
			return stringWriter.toString();
		} catch (Exception e) {
			log.error(e);
			return json;
		}
	}
}
