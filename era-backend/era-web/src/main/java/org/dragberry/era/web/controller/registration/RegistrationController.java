package org.dragberry.era.web.controller.registration;

import java.io.IOException;
import java.security.Principal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dragberry.era.business.registration.ContractService;
import org.dragberry.era.business.reporting.ReportingService;
import org.dragberry.era.common.registration.RegistrationTO;
import org.dragberry.era.common.reporting.ReportTemplateInfoTO;
import org.dragberry.era.web.exception.ResourceNotFoundException;
import org.dragberry.era.web.security.AccessContoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RegistrationController {
	
	private static final String CONTENT_DISPOSITION_KEY = "Content-Disposition";
	private static final String CONTENT_DISPOSITION_VALUE = "attachment; filename={0}";
	
	@Autowired
	private AccessContoll accessContoll;
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private ReportingService reportingService;
	
	@GetMapping("api/registrations/fetch-list")
	public ResponseEntity<List<RegistrationTO>> fetchRegistrationList() {
		RegistrationTO reg = new RegistrationTO();
		reg.setId(1000L);
		reg.setFirstName("Максим");
		reg.setLastName("Драгун");
		reg.setMiddleName("Леонидович");
		reg.setSpeciality("1 011 0023 31");
		reg.setRegistrationDate(LocalDate.now());
		reg.setAttestateAvg(8.435);
		return ResponseEntity.ok(Arrays.asList(reg));
	}
	
	@GetMapping("/api/registrations/fetch-report-templates")
	public ResponseEntity<List<ReportTemplateInfoTO>> getReportTemplatesForCustomer(Principal principal) {
		return ResponseEntity.ok(reportingService.getReportsForCustomer(accessContoll.getLoggedUser().getId()));
	}
	
	@GetMapping("/api/get-registration-contract/{contractId}/template/{templateId}")
	public void downloadRegistrationContract(
			HttpServletResponse response,
			@PathVariable("contractId") Long contractId,
			@PathVariable("templateId") Long templateId) {
		try {
			ReportTemplateInfoTO reportInfo = reportingService.getReportInfo(templateId);
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
