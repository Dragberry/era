package org.dragberry.era.web.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dragberry.era.business.registration.ContractService;
import org.dragberry.era.business.registration.RegistrationService;
import org.dragberry.era.business.reporting.ReportingService;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.registration.RegistrationPeriodTO;
import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.common.registration.RegistrationTO;
import org.dragberry.era.common.reporting.ReportTemplateInfoTO;
import org.dragberry.era.web.exception.ResourceNotFoundException;
import org.dragberry.era.web.security.AccessContoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/registrations")
public class RegistrationController {
	
	private static final String CONTENT_DISPOSITION_KEY = "Content-Disposition";
	private static final String CONTENT_DISPOSITION_VALUE = "attachment; filename={0}";
	
	@Autowired
	private AccessContoll accessContoll;
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private ReportingService reportingService;
	
	@GetMapping("/get-list")
	public ResponseEntity<ResultTO<List<RegistrationTO>>> getRegistrationList(
			@RequestParam("periodId") Long periodId,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "speciality", required = false) Long speciality,
			@RequestParam(name = "study-type", required = false) Character studyType) {
		RegistrationSearchQuery query = new RegistrationSearchQuery();
		query.setPeriodId(periodId);
		query.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		query.setName(name);
		query.setSpecialtyId(speciality);
		query.setStudyType(studyType);
		return ResponseEntity.ok(Results.create(registrationService.getRegistrationList(query)));
	}
	
	@GetMapping("/get-report-templates")
	public ResponseEntity<ResultTO<List<ReportTemplateInfoTO>>> getReportTemplatesForCustomer() {
		return ResponseEntity.ok(Results.create(reportingService.getReportsForCustomer(accessContoll.getLoggedUser().getId())));
	}
	
	@GetMapping("/get-active-period")
	public ResponseEntity<ResultTO<RegistrationPeriodTO>> getActivePeriod() {
		return ResponseEntity.ok(Results.create(registrationService.getActiveRegistrationPeriod(accessContoll.getLoggedUser().getCustomerId())));
	}
	
	@GetMapping("/get-contract/{contractId}/template/{templateId}")
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
