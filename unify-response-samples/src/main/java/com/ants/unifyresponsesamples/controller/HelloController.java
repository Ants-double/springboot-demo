package com.ants.unifyresponsesamples.controller;

import com.ants.unifyresponsesamples.annotation.ResponseResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@RestController
@RequestMapping(value = "hello")
//@ResponseResultBody
public class HelloController {

	@GetMapping("get")
	public Object getHello(){
		return "hello word!";
	}
	@GetMapping("word")
	public Object getHelloWord() throws Exception {
		throw  new Exception("测试异常");
	}
}
