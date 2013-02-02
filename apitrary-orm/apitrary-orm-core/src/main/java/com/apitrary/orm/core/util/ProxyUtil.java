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
package com.apitrary.orm.core.util;

import net.sf.cglib.proxy.Enhancer;

import com.apitrary.orm.core.ApitraryDaoSupport;
import com.apitrary.orm.core.internal.proxy.LazyLoadingInterceptor;

/**
 * <p>ProxyUtil class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class ProxyUtil {

	/**
	 * <p>createLazyProxy.</p>
	 *
	 * @param target a {@link java.lang.Class} object.
	 * @param apitraryDaoSupport a {@link com.apitrary.orm.core.ApitraryDaoSupport} object.
	 * @param id a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createLazyProxy(Class<T> target, ApitraryDaoSupport apitraryDaoSupport, String id){
		if(id == null){
			return null;
		}
		try{
			Enhancer e = new Enhancer();
	        e.setSuperclass(target);
	        e.setCallback(new LazyLoadingInterceptor(target, apitraryDaoSupport, id));
	        return (T) e.create();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
