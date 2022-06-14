package com.ants.dirtributionlock.zookeeper.samples;

import com.ants.dirtributionlock.zookeeper.samples.config.ZookeeperConfig;
import com.ants.dirtributionlock.zookeeper.samples.lock.ZLock;
import com.ants.dirtributionlock.zookeeper.samples.lock.ZLockFactory;
import com.ants.dirtributionlock.zookeeper.samples.lock.ZLockFactoryImpl;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableConfigurationProperties({
        ZookeeperConfig.class
})
public class DirtributionLockZookeeperSamplesApplication {





    public static void main(String[] args) {
        SpringApplication.run(DirtributionLockZookeeperSamplesApplication.class, args);

    }

}
