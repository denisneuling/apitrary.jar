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
 * <p>
 * TypeUtil class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class TypeUtil {

	/**
	 * <p>as.</p>
	 *
	 * @param t a {@link java.lang.Class} object.
	 * @param obj a {@link java.lang.Object} object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T as(Class<T> t, final Object obj) {
		return (T) Proxy.newProxyInstance(t.getClassLoader(), new Class[] { t }, new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				try {
					return obj.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(obj, args);
				} catch (NoSuchMethodException nsme) {
					throw new NoSuchMethodError(nsme.getMessage());
				} catch (InvocationTargetException ite) {
					throw ite.getTargetException();
				}
			}
		});
	}
	
	/**
	 * <p>printGettersSetters.</p>
	 *
	 * @param aClass a {@link java.lang.Class} object.
	 */
	public static void printGettersSetters(Class<?> aClass) {
		Method[] methods = aClass.getMethods();

		for (Method method : methods) {
			if (isGetter(method))
				System.out.println("getter: " + method);
			if (isSetter(method))
				System.out.println("setter: " + method);
		}
	}

	/**
	 * <p>isGetter.</p>
	 *
	 * @param method a {@link java.lang.reflect.Method} object.
	 * @return a boolean.
	 */
	public static boolean isGetter(Method method){
		  if(!method.getName().startsWith("get")){
			  return false;
		  }
		  if(method.getParameterTypes().length != 0){
			  return false;  
		  }
		  if(void.class.equals(method.getReturnType())){
			  return false;
		  }
		  return true;
		}

	/**
	 * <p>isSetter.</p>
	 *
	 * @param method a {@link java.lang.reflect.Method} object.
	 * @return a boolean.
	 */
	public static boolean isSetter(Method method) {
		if (!method.getName().startsWith("set")){
			return false;
		}
		if (method.getParameterTypes().length != 1){
			return false;
		}
		return true;
	}
	
	/**
	 * <p>isToString.</p>
	 *
	 * @param method a {@link java.lang.reflect.Method} object.
	 * @return a boolean.
	 */
	public static boolean isToString(Method method) {
		if (method.getName().equals("toString")){
			return true;
		}
		return false;
	}
	
	/**
	 * <p>isHashCode.</p>
	 *
	 * @param method a {@link java.lang.reflect.Method} object.
	 * @return a boolean.
	 */
	public static boolean isHashCode(Method method) {
		if (method.getName().equals("hashCode")){
			return true;
		}
		return false;
	}
	
	/**
	 * <p>isEquals.</p>
	 *
	 * @param method a {@link java.lang.reflect.Method} object.
	 * @return a boolean.
	 */
	public static boolean isEquals(Method method) {
		if (method.getName().equals("equals")){
			return true;
		}
		return false;
	}
}
