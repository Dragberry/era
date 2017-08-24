package org.dragberry.era.web.controller.registration;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletResponse;

import org.dragberry.era.business.registration.ContractService;
import org.dragberry.era.business.reporting.ReportTemplateInfo;
import org.dragberry.era.business.reporting.ReportingService;
import org.dragberry.era.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RegistrationController {
	
	private static final String CONTENT_DISPOSITION_KEY = "Content-Disposition";
	private static final String CONTENT_DISPOSITION_VALUE = "inline; filename=\"{0}\"";
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private ReportingService reportingService;
	
	@GetMapping("/get-registration-contract/{contractId}/template/{templateId}")
	public void downloadRegistrationContract(HttpServletResponse response,
			@PathVariable("contractId") Long contractId, @PathVariable("templateId") Long templateId) {
		try {
			ReportTemplateInfo reportInfo = reportingService.getReportInfo(templateId);
			if (reportInfo == null) {
				throw new ResourceNotFoundException();
			}
			response.setContentType(reportInfo.getMime());
	        response.setHeader(CONTENT_DISPOSITION_KEY, MessageFormat.format(CONTENT_DISPOSITION_VALUE, reportInfo.getFileName()));
			contractService.generateRegistrationContract(contractId, templateId, response.getOutputStream());
			
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

}
