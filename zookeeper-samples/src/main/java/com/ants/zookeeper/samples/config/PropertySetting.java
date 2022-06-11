package com.ants.zookeeper.samples.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lyy
 * @Description
 * @Date 2022-06-11
 **/
@Component
public class PropertySetting {

	private static final String PATH = "/config";

	/**
	 * 用来找对应的propertySource
	 */
	private static final String ZKPROPERTY = "config resource zookeeper";

	private static final String SCOPE = "antsScope";

	private static ConcurrentHashMap<String, String> sourceMap = new ConcurrentHashMap();

	@Resource
	private ConfigurableApplicationContext applicationContext;

	

	@Resource
	private CuratorFramework curatorFramework;

	@Resource
	private RefreshScopeRegistry registry;

	@PostConstruct
	public void configProperty() {
		try {
			Stat stat = curatorFramework.checkExists().forPath(PATH);
			if (stat == null) {
				throw new RuntimeException("zookeeper上没有配置参数");
			}
			addPropertiesToSpringEnvironment();
			childNodeListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加子节点的监听
	 */
	private void childNodeListener() {
		try {
			PathChildrenCache cache = new PathChildrenCache(curatorFramework, PATH, false);
			cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
			// Normal / BUILD_INITIAL_CACHE /POST_INITIALIZED_EVENT

			cache.getListenable().addListener(new PathChildrenCacheListener() {
				@Override
				public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) {
					switch (pathChildrenCacheEvent.getType()) {
						case CHILD_ADDED:
							System.out.println("增加子节点");
							//添加新属性到环境中
							addPropertyToSpringEnvironment(pathChildrenCacheEvent.getData());
							//刷新相关引用的Bean实例
							refreshBeans();
							break;
						case CHILD_REMOVED:
							System.out.println("删除子节点");
							deletePropertyFromSpringEnvironment(pathChildrenCacheEvent.getData());
							refreshBeans();
							break;
						case CHILD_UPDATED:
							System.out.println("更新子节点");
							addPropertyToSpringEnvironment(pathChildrenCacheEvent.getData());
							refreshBeans();
							break;
						default:
							break;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 属性变动，通知刷新Bean
	 */
	private void refreshBeans() {
		//找出需要刷新的Bean
		BeanDefinitionRegistry beanDefinitionRegistry = registry.getRegistry();
		Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(beanDefinitionName -> {
			BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);
			if (beanDefinition.getScope().equals(SCOPE)) {
				//从scope中删除原有的Bean
				applicationContext.getBeanFactory().destroyScopedBean(beanDefinitionName);
				//重新创建Bean
				applicationContext.getBean(beanDefinitionName);
			}
		});
	}

	/**
	 * 删除一个属性
	 *
	 * @param data
	 */
	private void deletePropertyFromSpringEnvironment(ChildData data) {
		sourceMap.remove(data.getPath().substring(PATH.length() + 1));
	}

	/**
	 * 添加一个属性
	 *
	 * @param data
	 */
	private void addPropertyToSpringEnvironment(ChildData data) {
		try {
			byte[] value = curatorFramework.getData().forPath(data.getPath());
			sourceMap.put(data.getPath().substring(PATH.length() + 1), new String(value));
			//sourceMap.put(data.getPath().substring(8), new String(value));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化属性-从zk加载
	 */
	private void addPropertiesToSpringEnvironment() {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		MutablePropertySources mutablePropertySources = environment.getPropertySources();
		//创建属性
		OriginTrackedMapPropertySource originTrackedMapPropertySource = new OriginTrackedMapPropertySource(ZKPROPERTY, sourceMap);
		//遍历zk节点下的配置
		try {
			List<String> paths = curatorFramework.getChildren().forPath(PATH);
			for (String path : paths) {
				sourceMap.put(path, new String(curatorFramework.getData().forPath(PATH + "/" + path)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mutablePropertySources.addLast(originTrackedMapPropertySource);
	}
}
