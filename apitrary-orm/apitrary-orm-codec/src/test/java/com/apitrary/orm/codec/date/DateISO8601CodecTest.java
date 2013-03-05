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
package com.apitrary.orm.codec.date;

import java.util.Date;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class DateISO8601CodecTest {

	private DateISO8601Codec codec;
	private Date date;

	@Before
	public void setUp() {
		date = new Date();
		codec = new DateISO8601Codec();
	}

	@Test
	public void test_codec() {
		String encoded = codec.encode(date);
		Date result = codec.decode(encoded);

		Assert.assertTrue((date.getTime()/1000) == (result.getTime()/1000));
	}

}
