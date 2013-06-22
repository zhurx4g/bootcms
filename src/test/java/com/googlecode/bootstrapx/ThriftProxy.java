package com.googlecode.bootstrapx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ThriftProxy {

	@SuppressWarnings("unchecked")
	public static <T> T getProxy(final T instance){
		return (T)Proxy.newProxyInstance(ThriftProxy.class.getClassLoader(), instance.getClass().getInterfaces(), new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) {
					try {
						return method.invoke(instance, args);
					} catch (IllegalArgumentException e) {
						throw e;
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					} catch(Exception e){
						return null;
					}
					return null;
			}
		});
	}
	
	interface X{
		public void m(int arg);
	}
	public static class C implements X {
		public void m(int arg){
			if(arg==1){
				System.out.println("success");
			}else{
				throw new RuntimeException("");
			}
		}
	}
	public static void main(String[] args) {
		X x = new C();
		X client = ThriftProxy.getProxy(x);
		client.m(1);
		client.m(2);
		client.m(3);
		client.m(4);
	}
}
