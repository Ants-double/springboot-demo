package com.ants.zookeeper.samples.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lyy
 * @Description
 * @Date 2022-06-11
 **/
public class RefreshScope implements Scope {
	private ConcurrentHashMap map = new ConcurrentHashMap();
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		if (map.containsKey(name)) {
			return map.get(name);
		} else {
			Object obj = objectFactory.getObject();
			map.put(name, obj);
			return obj;
		}
	}

	@Override
	public Object remove(String name) {
		return map.remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {

	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return null;
	}
}
