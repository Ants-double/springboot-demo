package com.ants.security.samples.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
	@GetMapping("ha")
	@PreAuthorize("hasAnyAuthority('ADMIN','ROLE_ADMIN')")
	public Object getHa(){
		return "hello word  你是最牛逼的人";
	}
	@PostMapping("login")
	public Object login(@RequestParam(name = "userName") String userName,
						@RequestParam(name = "password") String password){
		return "登录成功";
	}
	@GetMapping("force_login")
	public Object force_login(){
		return "强制登录页";
	}
}
