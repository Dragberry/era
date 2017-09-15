package org.dragberry.era.application.config;

import org.dragberry.era.application.config.BusinessConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class BusinessConfigTest {

	@Configuration
    @Import(BusinessConfig.class)
    static class Config {}
	
}
