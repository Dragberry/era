package org.dragberry.era.application;

import org.dragberry.era.application.config.DataConfig;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Launcher {
	
	public static void main(String[] args) {
		init(DataConfig.class);
	}
	
	public static void init(Class<?> config) {
		try(ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(config)) {
			DummyDataBean bean = context.getBean(DummyDataBean.class);
			bean.createTestData();
		}
	}
	
}