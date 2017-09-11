package org.dragberry.era.application.config;

import org.dragberry.era.business.BusinessServices;
import org.dragberry.era.business.registration.validation.EnrolleeValidator;
import org.dragberry.era.business.registration.validation.RegistrationValidationService;
import org.dragberry.era.business.validation.ValidationService;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.security.AccessControl;
import org.springframework.context.annotation.Bean;
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
		BusinessServices.class, AccessControl.class
})
public class BusinessConfig {

	@Bean
	public ValidationService<Registration> registrationValidationService() {
		ValidationService<Registration> service = new RegistrationValidationService();
		service.addValidator(enrolleeValidator());
		return service;
	}
	
	@Bean
	public Validator<Registration> enrolleeValidator() {
		return new EnrolleeValidator();
	}
}
