package com.ants.file.samples.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-27
 **/
//@Component
public class TomcatConfig {
	//@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				///connector.setProperty("relaxedQueryChars", "|{}[]");
				connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}");
				connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}");
			}
		});
		return factory;
	}
}
