package com.ants.nap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
@MapperScan(basePackages = {"com.ants.nap.servers.api.mapper","com.ants.nap.annotation"})
public class NapApplication {

    public static void main(String[] args) {
        SpringApplication.run(NapApplication.class, args);
    }

}
