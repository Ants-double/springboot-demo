package com.ants.mybatis.samples.mapper;

import com.ants.mybatis.samples.pojo.Hello;

/**
 * @author lyy08
 */
public interface HelloServiceMapper {
	Hello getHello(String id);

	int addHello(Hello build);
}
