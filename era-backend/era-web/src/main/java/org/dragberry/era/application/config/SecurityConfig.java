package org.dragberry.era.application.config;

import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.web.security.CustomCsrfHeaderFilter;
import org.dragberry.era.web.security.CustomerSecurityService;
import org.dragberry.era.web.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = { Security.class })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";
	private static final String SRAS_REMEMBER_TOKEN = "SRAS_REMEMBER_TOKEN";
	private static final String UTF_8 = "UTF-8";
	private static final String SHA_256_ALGORITHM = "SHA-256";

	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private CustomCsrfHeaderFilter customCsrfHeaderFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding(UTF_8);
		filter.setForceEncoding(true);
		http
			.csrf().csrfTokenRepository(customCsrfTokenRepository())
		.and()
			.addFilterAfter(customCsrfHeaderFilter, CsrfFilter.class)
			.rememberMe().key(SRAS_REMEMBER_TOKEN).rememberMeServices(rememberMeServices())
		.and()
			.httpBasic().authenticationEntryPoint(http403ForbiddenEntryPoint());
		// .and()
		// .authorizeRequests()
		// .antMatchers("/app/transactions/*").denyAll()
		// .antMatchers("/app/component/transactions/**").denyAll()
		// .antMatchers("/app/component/transactions/").denyAll()
		// .antMatchers("/app/component/transactions").denyAll()

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customerecurityService()).passwordEncoder(new StandardPasswordEncoder(SHA_256_ALGORITHM));
	}

	@Bean
	public CustomerSecurityService customerecurityService() {
		return new CustomerSecurityService(userAccountDao);
	}

	@Bean
	public CsrfTokenRepository customCsrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName(X_XSRF_TOKEN);
		return repository;
	}

	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {

		TokenBasedRememberMeServices service =
				new TokenBasedRememberMeServices(SRAS_REMEMBER_TOKEN, customerecurityService());
		service.setCookieName(SRAS_REMEMBER_TOKEN);
		service.setUseSecureCookie(false);
		service.setAlwaysRemember(false);
		return service;
	}

	@Bean
	public Http403ForbiddenEntryPoint http403ForbiddenEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
}