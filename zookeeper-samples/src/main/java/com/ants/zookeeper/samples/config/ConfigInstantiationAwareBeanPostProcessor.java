package com.ants.zookeeper.samples.config;

import com.ants.zookeeper.samples.controller.DynamicConfigController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;

/**
 * @Author lyy
 * @Description
 * @Date 2022-06-11
 **/
public class ConfigInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

	@Resource
	private ConfigurableApplicationContext applicationContext;

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		if (beanClass == DynamicConfigController.class) {
			applicationContext.getBean(PropertySetting.class);
		}
		return null;
	}
}
