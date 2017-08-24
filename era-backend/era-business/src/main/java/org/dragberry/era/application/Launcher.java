package org.dragberry.era.application;

import java.io.File;
import java.io.FileOutputStream;

import org.dragberry.era.application.config.BusinessConfig;
import org.dragberry.era.business.registration.ContractService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Launcher {

	public static void main(String[] args) throws Exception {
			ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BusinessConfig.class);
			ContractService contractService = context.getBean(ContractService.class);
			try (FileOutputStream fos = new FileOutputStream(new File("y:\\result-template.docx"))) {
				contractService.generateRegistrationContract(1000L, 1000L, fos);
			}
			
	}

}
