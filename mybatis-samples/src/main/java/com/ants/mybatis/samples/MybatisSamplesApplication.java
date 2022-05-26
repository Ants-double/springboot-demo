package com.ants.mybatis.samples;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.ants.mybatis.samples.mapper"})
public class MybatisSamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisSamplesApplication.class, args);
	}

}
