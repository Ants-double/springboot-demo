package com.ants.redis.samples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-24
 **/
@RestController
@RequestMapping("redis")
public class RedisController {

	@Autowired
	RedisTemplate redisTemplate;

	@GetMapping("get")
	public Object getRedis(@RequestParam(name = "key")String key){
		return redisTemplate.opsForValue().get(key);
	}
	@GetMapping("set")
	public Object  getRedis(@RequestParam(name = "key")String key,
						   @RequestParam(name = "value")String value){
		 redisTemplate.opsForValue().set(key,value);
		 return "ok";

	}
}
