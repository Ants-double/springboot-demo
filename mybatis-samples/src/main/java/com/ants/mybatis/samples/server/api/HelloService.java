package com.ants.mybatis.samples.server.api;

import com.ants.mybatis.samples.pojo.Hello;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lyy08
 */
public interface HelloService {


	Hello getHello(String id);

	int addHello(Hello build);
}
