package com.apitrary.orm.core.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
