package com.steel.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

public class PropertyMISKeyGenerator implements KeyGenerator{

	@Override
	public Object generate(Object arg0, Method arg1, Object... arg2) {
		String className = arg0.getClass().getSimpleName();
		String mname = arg1.getName();
		String params = arr2Str(arg2);
		String key = className + "@" +arg0.hashCode() + "." + mname + "("+params+")" ;
		System.out.println(key);
		return key;

	}
	private String arr2Str(Object []arr){
		String temp = "" ;
		if(arr!=null&&arr.length!=0){
			for(Object s : arr){
				temp = temp + s + "," ;
			}
			return temp.substring(0,temp.length() - 1);
		}
		return temp;
	}
}
