package com.redis;

import com.redis.model.Item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class SpringBootRedisExample {
	
	@Value("${redis.port}")
	private int port;
	@Value("${redis.host}")
	private String hostName;
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory(){
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(hostName);
		redisStandaloneConfiguration.setPort(port);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	RedisTemplate<String, Item> redisTemplate(){
		RedisTemplate<String,Item> redisTemplate = new RedisTemplate<String, Item>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		//redisTemplate.expire(key, timeout, unit)
		return redisTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRedisExample.class, args);
	}
}
