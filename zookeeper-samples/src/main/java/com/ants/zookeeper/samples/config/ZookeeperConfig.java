package com.ants.zookeeper.samples.config;

import com.ants.zookeeper.samples.watches.ZookeeperWatches;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lyy
 * @Description
 * @Date 2022-06-11
 **/
@Configuration
@Data
@ConfigurationProperties(prefix = "zookeeper.curator")
public class ZookeeperConfig {

	private String ip;
	private Integer connectionTimeoutMs;
	private Integer sessionTimeOut;
	private Integer sleepMsBetweenRetry;
	private Integer maxRetries;
	private String namespace;
	@Bean("curatorClient")
	public CuratorFramework curatorClient() throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder()
				//连接地址  集群用,隔开
				.connectString(ip)
				.connectionTimeoutMs(connectionTimeoutMs)
				//会话超时时间
				.sessionTimeoutMs(sessionTimeOut)
				//设置重试机制
				.retryPolicy(new ExponentialBackoffRetry(sleepMsBetweenRetry,maxRetries))
				//设置命名空间 在操作节点的时候，会以这个为父节点
				.namespace(namespace)
				.build();
		client.start();

		//注册监听器
		ZookeeperWatches watches = new ZookeeperWatches(client);
		watches.znodeWatcher();
		watches.znodeChildrenWatcher();
		return client;
	}
}
