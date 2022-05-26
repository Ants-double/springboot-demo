package com.ants.security.samples.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@RestController
@RequestMapping("hello")
public class HelloController {

	@GetMapping("get")
	public Object get(){
		return "hello word";
	}
}
