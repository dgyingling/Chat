package com.steel.test;

import org.junit.Test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;






public class TestEhcache {
		
		@Test
		public void test(){
			// 基于classpath下的配置文件创建CacheManager实例
			//URL url = getClass().getResource("src/main/resources/ehcache.xml");
			//CacheManager manager = CacheManager.newInstance("src/main/resources/ehcache.xml");
			CacheManager manager = CacheManager.create();
			 // 获得Cache的引用
	        Cache cache = manager.getCache("propertyMISCache");
	        //cache.put(new Element("11","22"));
	       System.out.println(cache.getSize());
	       /* Element element = cache.get("PostServiceImpl@415169817.selectPostById(2)");
	        System.out.println(element.getObjectValue());*/
		}
}
