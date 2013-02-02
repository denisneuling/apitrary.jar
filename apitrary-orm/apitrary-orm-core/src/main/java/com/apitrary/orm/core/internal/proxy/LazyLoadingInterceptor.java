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
package com.apitrary.orm.core.internal.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.apitrary.orm.core.ApitraryDaoSupport;

/**
 * <p>LazyLoadingInterceptor class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class LazyLoadingInterceptor implements MethodInterceptor {

	private String id;
	private ApitraryDaoSupport daoSupport;

	private Class<?> targetedLoad;
	private Object loadedTarget;

	private boolean loaded = false;

	/**
	 * <p>Constructor for LazyLoadingInterceptor.</p>
	 *
	 * @param loaded a {@link java.lang.Class} object.
	 * @param daoSupport a {@link com.apitrary.orm.core.ApitraryDaoSupport} object.
	 * @param id a {@link java.lang.String} object.
	 */
	public LazyLoadingInterceptor(Class<?> loaded, ApitraryDaoSupport daoSupport, String id) {
		this.daoSupport = daoSupport;
		this.id = id;
		this.targetedLoad = loaded;
	}
	
	/** {@inheritDoc} */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy arg3) throws Throwable {
		if (id == null) {
			throw new NullPointerException();
		}
		try {
			if (!loaded) {
				loadedTarget = daoSupport.findById(id, targetedLoad);
				loaded = true;
			}
			
			return method.invoke(loadedTarget, args);
		} catch (Throwable thr) {
			thr.printStackTrace();
			if (InvocationTargetException.class.isInstance(thr))
				thr = ((InvocationTargetException) thr).getTargetException();

			if (RuntimeException.class.isInstance(thr))
				throw thr;

			if (Error.class.isInstance(thr))
				throw thr;

			Class<?>[] declaredExceptions = method.getExceptionTypes();

			for (int i = 0; i < declaredExceptions.length; i++) {
				if (declaredExceptions[i].isInstance(thr))
					throw thr;
			}
			throw new UndeclaredThrowableException(thr);
		}
	}


}
