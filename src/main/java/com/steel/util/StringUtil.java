package com.steel.util;

import java.util.Date;
import java.util.UUID;

public class StringUtil {
	 public static String createActivateCode(){
	    return new Date().getTime() + UUID.randomUUID().toString().replace("-","");
	 }
	 public static void main(String[] args) {
		System.out.println(createActivateCode());
	}
}
