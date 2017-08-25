package org.dragberry.era.application.config;

import org.dragberry.era.web.utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Import(value = { BusinessConfig.class })
@ComponentScan(basePackageClasses = { Utils.class })
@PropertySources({ @PropertySource("classpath:config.properties") })
public class AppConfig {

	@Bean
	public CommonAnnotationBeanPostProcessor postProcessor() {
		return new CommonAnnotationBeanPostProcessor();
	}
}
