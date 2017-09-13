package com.steel.test;

import redis.clients.jedis.Jedis;

public class TestRedis {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
	
		System.out.println("connect successly");
		System.out.println(jedis.ping());
	}
}
