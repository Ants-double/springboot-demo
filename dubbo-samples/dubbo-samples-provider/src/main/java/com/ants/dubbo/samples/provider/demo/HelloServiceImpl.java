package com.ants.dubbo.samples.provider.demo;


import com.ants.dubbo.samples.api.demo.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-24
 **/
@Slf4j
@Component
@DubboService
public class HelloServiceImpl implements HelloService {

	public String sayHello() {
		return "hello ok";
	}
}
