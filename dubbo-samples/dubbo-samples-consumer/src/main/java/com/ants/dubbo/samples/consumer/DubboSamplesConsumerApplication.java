package com.ants.dubbo.samples.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author lyy08
 */
@SpringBootApplication
@EnableDubbo
@ImportResource(locations = "classpath:dubbo/**.xml")
public class DubboSamplesConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboSamplesConsumerApplication.class, args);
	}

}
