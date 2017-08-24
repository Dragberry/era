package org.dragberry.era.application.config;

import org.dragberry.era.business.BusinessServices;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
@Import({ DataConfig.class })
@ComponentScan(basePackageClasses = { 
		BusinessServices.class
})
public class BusinessConfig {

}
