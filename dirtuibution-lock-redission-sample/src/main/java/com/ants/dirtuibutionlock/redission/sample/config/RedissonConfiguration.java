package com.ants.dirtuibutionlock.redission.sample.config;


import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author ants_
 */
@Slf4j
@Configuration
public class RedissonConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private ThreadPoolTaskExecutor springSessionRedisTaskExecutor;

    /**
     * 初始化RedissonClient客户端
     * 注意：
     * 此实例集群为3节点，各节点1主1从
     * 集群模式,集群节点的地址须使用“redis://”前缀，否则将会报错。
     *
     * @return {@link RedissonClient}
     */
    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        if (redisProperties.getCluster() != null) {
            //集群模式配置
            List<String> nodes = redisProperties.getCluster().getNodes();

            List<String> clusterNodes = new ArrayList<>();
            for (int i = 0; i < nodes.size(); i++) {
                clusterNodes.add("redis://" + nodes.get(i));
            }
            ClusterServersConfig clusterServersConfig = config.useClusterServers()
                    .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));

            if (!StringUtils.isEmpty(redisProperties.getPassword())) {
                clusterServersConfig.setPassword(redisProperties.getPassword());
            }
        } else {
            //单节点配置
            String address = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
            SingleServerConfig serverConfig = config.useSingleServer();
            serverConfig.setAddress(address);
            if (!StringUtils.isEmpty(redisProperties.getPassword())) {
                serverConfig.setPassword(redisProperties.getPassword());
            }
            serverConfig.setDatabase(redisProperties.getDatabase());
        }
        //看门狗的锁续期时间，默认30000ms，这里配置成15000ms
        config.setLockWatchdogTimeout(30000);
       // config.setExecutor(springSessionRedisTaskExecutor);
        return Redisson.create(config);
    }


}
