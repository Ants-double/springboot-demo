package com.ants.mybatis.samples.controller;

import com.ants.mybatis.samples.pojo.Hello;
import com.ants.mybatis.samples.server.api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@RestController
@RequestMapping("hello")
public class HelloController {

	@Autowired
	HelloService helloService;

	@GetMapping("get")
	public Object get(@RequestParam(name = "id") String id) {
		Hello hello = helloService.getHello(id);
		return hello;
	}

	@GetMapping("add")
	@PostMapping("add")
	public Object add(@RequestParam(name = "id",required = false) String id,
					  @RequestParam(name = "name") String name) {
		Hello build = Hello.builder().id(id).name(name).build();
		int result = helloService.addHello(build);
		return build.getId();
	}
}
