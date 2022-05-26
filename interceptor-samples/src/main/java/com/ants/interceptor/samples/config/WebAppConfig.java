package com.ants.interceptor.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Autowired
	SessionInterceptor sessionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 设置拦截的路径、不拦截的路径、优先级等等
		registry.addInterceptor(sessionInterceptor)
				.addPathPatterns("/**")
				.addPathPatterns("/*")
				.excludePathPatterns("/favicon.ico")
				.excludePathPatterns("/hello/login");
	}
}
