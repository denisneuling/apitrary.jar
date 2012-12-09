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
package com.apitrary.orm.core.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>TypeUtil class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class TypeUtil {

	@SuppressWarnings("unchecked")
	static <T> T as(Class<T> t, final Object obj) {
		return (T) Proxy.newProxyInstance(t.getClassLoader(),
				new Class[] { t }, new InvocationHandler() {
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						try {
							return obj
									.getClass()
									.getMethod(method.getName(),
											method.getParameterTypes())
									.invoke(obj, args);
						} catch (NoSuchMethodException nsme) {
							throw new NoSuchMethodError(nsme.getMessage());
						} catch (InvocationTargetException ite) {
							throw ite.getTargetException();
						}
					}
				});
	}
}
