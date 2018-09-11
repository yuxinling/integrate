package com.integrate.core.redis;


import redis.clients.jedis.BasicCommands;
import redis.clients.jedis.BinaryJedisClusterCommands;
import redis.clients.jedis.JedisCommands;

public interface RedisClusterInterface extends JedisCommands,BasicCommands,BinaryJedisClusterCommands{
	
}
