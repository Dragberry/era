package org.dragberry.era.web.controller;

import org.dragberry.era.business.certificate.CertificateService;
import org.dragberry.era.common.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificate")
public class CertificationController {
	
	@Autowired
	private CertificateService certificateService;
	
	@GetMapping("/get-subject-list")
	public ResponseEntity<?> getSubjectList() {
		return ResponseEntity.ok(Results.create(certificateService.getSubjectList()));
	}

}
