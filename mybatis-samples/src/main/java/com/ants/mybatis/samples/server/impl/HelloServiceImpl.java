package com.ants.mybatis.samples.server.impl;

import com.ants.mybatis.samples.mapper.HelloServiceMapper;
import com.ants.mybatis.samples.pojo.Hello;
import com.ants.mybatis.samples.server.api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Service("HelloService")
public class HelloServiceImpl implements HelloService {

	@Autowired
	HelloServiceMapper helloServiceMapper;

	@Override
	public Hello getHello(String id) {
		return helloServiceMapper.getHello(id);
	}

	@Override
	public int addHello(Hello build) {
		int i = helloServiceMapper.addHello(build);
		return i;
	}
}
