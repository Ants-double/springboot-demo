package com.ants.swagger.samples.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-25
 **/
@RestController
@RequestMapping("hello")
@Api(value = "hello模块", tags = "demo 测试")
public class HelloController {

	@GetMapping(value = "word")
	@ApiOperation(value = "获取hello", notes = "测试")
	public String getHello(@RequestParam(value = "name")String name){
		return "hello"+name;
	}
}
