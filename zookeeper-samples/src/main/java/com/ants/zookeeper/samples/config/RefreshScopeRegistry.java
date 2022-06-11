package com.ants.zookeeper.samples.config;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author lyy
 * @Description
 * @Date 2022-06-11
 **/
@Getter
@Component
public class RefreshScopeRegistry implements BeanDefinitionRegistryPostProcessor {

	private BeanDefinitionRegistry registry;

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		this.registry = registry;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		beanFactory.registerScope("antsScope", new RefreshScope());
	}
}
