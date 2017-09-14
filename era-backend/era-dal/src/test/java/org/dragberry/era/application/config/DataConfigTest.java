package org.dragberry.era.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({ @PropertySource("classpath:database-test.properties") })
public class DataConfigTest {
	
	@Configuration
    @Import(DataConfig.class)
    static class Config {}

}
