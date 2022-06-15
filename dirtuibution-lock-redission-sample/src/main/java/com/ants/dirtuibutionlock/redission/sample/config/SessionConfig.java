package com.ants.dirtuibutionlock.redission.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import java.util.concurrent.Executor;

@Configuration
public class SessionConfig {


    @Autowired
    private RedisHttpSessionConfiguration redisHttpSessionConfiguration;
    /**
     * 用于spring session，防止每次创建一个线程
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor springSessionRedisTaskExecutor(){
        ThreadPoolTaskExecutor springSessionRedisTaskExecutor = new ThreadPoolTaskExecutor();
        springSessionRedisTaskExecutor.setCorePoolSize(8);
        springSessionRedisTaskExecutor.setMaxPoolSize(16);
        springSessionRedisTaskExecutor.setKeepAliveSeconds(10);
        springSessionRedisTaskExecutor.setQueueCapacity(1000);
        springSessionRedisTaskExecutor.setThreadNamePrefix("Spring session redis executor thread: ");
      //  redisHttpSessionConfiguration.setRedisTaskExecutor(springSessionRedisTaskExecutor);
        return springSessionRedisTaskExecutor;
    }
//    @Bean
//    public RedisHttpSessionConfiguration redisHttpSessionConfiguration(){
//        RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
//
//        return redisHttpSessionConfiguration;
//    }

//    @Qualifier("springSessionRedisTaskExecutor")
//    public void setRedisTaskExecutor(Executor redisTaskExecutor) {
//
//        redisHttpSessionConfiguration.setRedisTaskExecutor(redisTaskExecutor);
//
//    }
}
