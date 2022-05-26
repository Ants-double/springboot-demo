package com.ants.aspect.samples.controller;

import com.ants.aspect.samples.aop.WebLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Slf4j
@RestController
@RequestMapping("hello")
public class HelloController {

	@GetMapping("get")
	@WebLog(description = "test aspect aop")
	public String get(@RequestParam(value = "name")String name){
		return "hello"+name;
	}
}
