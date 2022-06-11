package com.ants.zookeeper.samples.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author lyy
 * @Description
 * @Date 2022-06-11
 **/
@RestController
@RequestMapping("dynamic")
@Scope("antsScope")
public class DynamicConfigController {

	@Resource
	private Environment environment;

	@Value("${name}")
	private String name;

	@PostConstruct
	public void init() {
		String fromEnvironment = environment.getProperty("name");
		String res = String.format("fromEnvironment: %s, fromValue: %s", fromEnvironment, name);
		System.out.println("ConfigController init\n" + res);
	}
	@GetMapping("/name")
	public String name() {
		String fromEnvironment = environment.getProperty("name");
		return String.format("fromEnvironment: %s, fromValue: %s", fromEnvironment, name);
	}
}
