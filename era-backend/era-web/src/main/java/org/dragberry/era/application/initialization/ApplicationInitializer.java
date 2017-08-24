package org.dragberry.era.application.initialization;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.dragberry.era.application.config.AppConfig;
import org.dragberry.era.application.config.SecurityConfig;
import org.dragberry.era.application.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	private static final String THROW_EXCEPTION_IF_NO_HANDLER_FOUND = "throwExceptionIfNoHandlerFound";
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { AppConfig.class, SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setInitParameter(THROW_EXCEPTION_IF_NO_HANDLER_FOUND, Boolean.TRUE.toString());
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}
	
	
	
}
