package org.dragberry.era.web.controller;

import java.util.List;

import org.dragberry.era.business.specialty.SpecialtyService;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.specialty.SpecialtySimpleTO;
import org.dragberry.era.security.AccessControl;
import org.dragberry.era.web.security.AccessControlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {
	
	@Autowired
	private AccessControl accessContoll;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@GetMapping("/get-list-for-registrations")
	public ResponseEntity<ResultTO<List<SpecialtySimpleTO>>> getListForRegistrations(@RequestParam("periodId") Long periodId) {
		return ResponseEntity.ok(Results.create(specialtyService.getListForRegistrations(accessContoll.getLoggedUser().getCustomerId(), periodId)));
	}

}
