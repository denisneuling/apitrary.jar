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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.apitrary.orm.core.codec.Codec;

/**
 * <p>DateISO8601Codec class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 * @since 0.1.1
 */
public class DateISO8601Codec extends Codec<Date> {

	/** {@inheritDoc} */
	@Override
	public Date decode(String value) {
		Calendar calendar = GregorianCalendar.getInstance();
		String s = value.replace("Z", "+00:00");
		try {
			s = s.substring(0, 22) + s.substring(23);
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException(e);
		}
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
			calendar.setTime(date);
			return calendar.getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String encode(Date object) {
		String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(object);
		return formatted.substring(0, 22) + ":" + formatted.substring(22);
	}

}
