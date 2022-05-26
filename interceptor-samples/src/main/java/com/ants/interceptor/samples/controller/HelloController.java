package com.ants.interceptor.samples.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@RestController
@RequestMapping(value = "hello")
public class HelloController {

	@GetMapping("get")
	public String get(){
		return "hello";
	}
	@GetMapping("login")
	public String login(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		session.setAttribute("user","ants");
		return "login success";
	}
}
