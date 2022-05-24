package com.ants.dubbo.samples.consumer.demo.controller;

import com.ants.dubbo.samples.api.demo.HelloService;
import com.ants.dubbo.samples.api.demo.HelloWordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-24
 **/
@RequestMapping(value = "hello")
@RestController
@Slf4j
public class HelloController {

	@DubboReference(version = "*", protocol = "dubbo", loadbalance = "random")
	private HelloService helloService;


	@Autowired
	private HelloWordService helloWordService;

	@GetMapping(value = "say")
	public String sayHello() {
		return helloService.sayHello();
	}
	@GetMapping(value = "word")
	public String sayHelloWord() {
		return helloWordService.helloWord();
	}
}
