package com.ants.logback.samples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LogbackSamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogbackSamplesApplication.class, args);
		log.info("log context");
	}

}
