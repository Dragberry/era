package org.dragberry.era.application.config;

import org.dragberry.era.business.audit.AuditService;
import org.dragberry.era.business.benefit.BenefitService;
import org.dragberry.era.business.certificate.CertificateService;
import org.dragberry.era.business.customer.CustomerService;
import org.dragberry.era.business.institution.EducationInstitutionService;
import org.dragberry.era.business.registration.ContractService;
import org.dragberry.era.business.registration.RegistrationService;
import org.dragberry.era.business.reporting.ReportingService;
import org.dragberry.era.business.specialty.SpecialtyService;
import org.dragberry.era.business.useraccount.UserAccountService;
import org.dragberry.era.web.utils.TimeProvider;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.core.userdetails.UserDetailsService;

@PropertySources({ @PropertySource("classpath:config.properties") })
public class TestContext {
	
	@Bean
	public TimeProvider timeProvider() {
		return new TimeProvider();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return Mockito.mock(UserDetailsService.class);
	}
	
	@Bean
	public BenefitService benefiteService() {
		return Mockito.mock(BenefitService.class);
	}
	
	@Bean
	public CertificateService certificateService() {
		return Mockito.mock(CertificateService.class);
	}
	
	@Bean
	public CustomerService customerService() {
		return Mockito.mock(CustomerService.class);
	}
	
	@Bean
	public EducationInstitutionService eiService() {
		return Mockito.mock(EducationInstitutionService.class);
	}
	
	@Bean
	public ContractService contractService() {
		return Mockito.mock(ContractService.class);
	}
	
	@Bean
	public RegistrationService regService() {
		return Mockito.mock(RegistrationService.class);
	}
	
	@Bean
	public ReportingService repService() {
		return Mockito.mock(ReportingService.class);
	}
	
	@Bean
	public SpecialtyService specService() {
		return Mockito.mock(SpecialtyService.class);
	}
	
	@Bean
	public UserAccountService usService() {
		return Mockito.mock(UserAccountService.class);
	}
	
	@Bean
	public AuditService auditService() {
		return Mockito.mock(AuditService.class);
	}
}
