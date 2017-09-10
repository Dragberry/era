package org.dragberry.era.web.controller;

import org.dragberry.era.business.institution.EducationInstitutionService;
import org.dragberry.era.common.Results;
import org.dragberry.era.security.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/education-institution")
public class EducationInstitutionController {
	
	@Autowired
	private AccessControl accessContoll;
	
	@Autowired
	private EducationInstitutionService eInstitutionService;
	
	@GetMapping("/get-list-for-registration")
	public ResponseEntity<?> getListForRegistrations() {
		return ResponseEntity.ok(Results.create(eInstitutionService.getInstitutionListForRegistration(accessContoll.getLoggedUser().getCustomerId())));
	}

}
