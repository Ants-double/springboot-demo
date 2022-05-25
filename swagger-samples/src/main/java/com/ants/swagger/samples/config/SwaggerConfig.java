package com.ants.swagger.samples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-25
 **/
@Configuration
@EnableSwagger2
@Profile({"dev","test"})
//@ConditionalOnProperty(prefix = "swagger2",value = {"enable"},havingValue = "true")
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				//.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);

	}
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"swagger-samples 项目接口文档",
				"项目地址：http://192.168.1.100:8889/swagger-samples。",
				"API V1.0",
				"swagger-samples",
				new Contact("swagger-samples ", "http://192.168.1.100:8889/swagger-samples", "ants_double@qq.com"),
				"Apache", "http://www.apache.org/", Collections.emptyList());
	}
}
