package org.dragberry.era.web.controller.registration;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dragberry.era.business.registration.ContractService;
import org.dragberry.era.business.reporting.ReportTemplateInfo;
import org.dragberry.era.business.reporting.ReportingService;
import org.dragberry.era.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("api/registrations/fetch-list")
	public ResponseEntity<List<RegistrationTO>> fetchRegistrationList() {
		RegistrationTO reg = new RegistrationTO();
		reg.setId(1000L);
		reg.setFirstName("Максим");
		reg.setLastName("Драгун");
		reg.setMiddleName("Леонидович");
		return ResponseEntity.ok(Arrays.asList(reg));
	}
	
	@GetMapping("/api/get-registration-contract/{contractId}/template/{templateId}")
	public void downloadRegistrationContract(
			HttpServletResponse response,
			@PathVariable("contractId") Long contractId,
			@PathVariable("templateId") Long templateId) {
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

class RegistrationTO implements Serializable {
	private static final long serialVersionUID = 1742267786414238594L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String middleName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
}
