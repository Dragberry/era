package org.dragberry.era.web.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dragberry.era.business.benefit.BenefitService;
import org.dragberry.era.business.registration.ContractService;
import org.dragberry.era.business.registration.RegistrationService;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.registration.RegistrationCRUDTO;
import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.common.registration.RegistrationTO;
import org.dragberry.era.common.reporting.ReportTemplateInfoTO;
import org.dragberry.era.security.AccessControl;
import org.dragberry.era.security.Roles;
import org.dragberry.era.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
	
	private static final String CONTENT_DISPOSITION_KEY = "Content-Disposition";
	private static final String CONTENT_DISPOSITION_VALUE = "attachment";
	
	private static final Logger LOG = LogManager.getLogger(RegistrationController.class);
	
	@Autowired
	private AccessControl accessContoll;
	
	@Autowired
	private BenefitService benefitServiceBean;
	@Autowired
	private ContractService contractService;
	@Autowired
	private RegistrationService registrationService;
	
	@GetMapping("/get-list")
	public ResponseEntity<ResultTO<List<RegistrationTO>>> getRegistrationList(
			@RequestParam("period") Long periodId,
			@RequestParam("education-institution") Long institutionId,
			@RequestParam(name = "specialty", required = false) Long specialtyId,
			@RequestParam(name = "registration-id", required = false) Long registrationId,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "funds-source", required = false) Character fundsSource,
			@RequestParam(name = "education-form", required = false) Character educationForm,
			@RequestParam(name = "education-base", required = false) String educationBase) {
		accessContoll.checkPermission(Roles.Registrations.VIEW);
		RegistrationSearchQuery query = new RegistrationSearchQuery();
		query.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		query.setPeriodId(periodId);
		query.setEducationInstitutionId(institutionId);
		query.setSpecialtyId(specialtyId);
		query.setRegistrationId(registrationId);
		query.setName(name);
		query.setFundsSource(fundsSource);
		query.setEducationForm(educationForm);
		query.setEducationBase(StringUtils.trimToNull(educationBase));
		return ResponseEntity.ok(Results.create(registrationService.getRegistrationList(query)));
	}
	
	@GetMapping("/get-active-periods")
	public ResponseEntity<?> getActivePeriods() {
		return ResponseEntity.ok(Results.create(registrationService.getActiveRegistrationPeriods(accessContoll.getLoggedUser().getCustomerId())));
	}
	
	@GetMapping("/get-contract-info/{registrationId}")
	public ResponseEntity<ResultTO<ReportTemplateInfoTO>> getContractInfo(@PathVariable("registrationId") Long registrationId) {
		return ResponseEntity.ok(Results.create(
				contractService.findReportTemplate(registrationId, accessContoll.getLoggedUser().getCustomerId())));
	}
	
	@GetMapping("/download-contract/{registrationId}")
	public void downloadRegistrationContract(
			HttpServletResponse response,
			@PathVariable("registrationId") Long registrationId) {
		ReportTemplateInfoTO reportInfo = contractService.findReportTemplate(registrationId, accessContoll.getLoggedUser().getCustomerId());
		if (reportInfo == null) {
			new ResourceNotFoundException();
		}
		try {
			response.setContentType(reportInfo.getMime());
	        response.setHeader(CONTENT_DISPOSITION_KEY, CONTENT_DISPOSITION_VALUE);
			contractService.generateRegistrationContract(registrationId, reportInfo.getId(), response.getOutputStream());
			
		} catch (IOException exc) {
			LOG.error(MessageFormat.format("Report generation failed for registration: {0}", registrationId), exc);
		}
	}
	
	@GetMapping("/get-periods")
	public ResponseEntity<?> getPeriods() {
		return ResponseEntity.ok(Results.create(
				registrationService.getRegistrationPeriodList(accessContoll.getLoggedUser().getCustomerId())));
	}
	
	@GetMapping("/get-benefits")
	public ResponseEntity<?> getBenefitList() {
		return ResponseEntity.ok(Results.create(benefitServiceBean.getActiveBenefits()));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody RegistrationCRUDTO registration) {
		accessContoll.checkPermission(Roles.Registrations.CREATE);
		registration.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		registration.setUserAccountId(accessContoll.getLoggedUser().getId());
		return ResponseEntity.ok(registrationService.createRegistration(registration));
	}
	
	@GetMapping("get-details/{id}")
	public ResponseEntity<?> details(@PathVariable("id") Long id) {
		accessContoll.checkPermission(Roles.Registrations.VIEW);
		LOG.info("Get registration details for id: " + id);
		return ResponseEntity.ok(Results.create(registrationService.fetchDetails(id)));
	}
	
	@PostMapping("/approve")
	public ResponseEntity<?> approve(@RequestBody RegistrationCRUDTO registration) {
		accessContoll.checkPermission(Roles.Registrations.APPROVE);
		registration.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		registration.setUserAccountId(accessContoll.getLoggedUser().getId());
		return ResponseEntity.ok(registrationService.approveRegistration(registration));
	}
	
	@PostMapping("/cancel")
	public ResponseEntity<?> cancel(@RequestBody RegistrationCRUDTO registration) {
		accessContoll.checkPermission(Roles.Registrations.CANCEL);
		registration.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		registration.setUserAccountId(accessContoll.getLoggedUser().getId());
		return ResponseEntity.ok(registrationService.cancelRegistration(registration));
	}
}
