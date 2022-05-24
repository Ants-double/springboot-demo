package com.ants.dubbo.samples.provider.demo;

import com.ants.dubbo.samples.api.demo.HelloWordService;
import org.springframework.stereotype.Service;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-24
 **/
//@Service
public class HelloWordServiceImpl implements HelloWordService {
	public String helloWord() {
		return "hello word!!!";
	}
}
