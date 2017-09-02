package org.dragberry.era.web.controller;

import java.util.List;

import org.dragberry.era.business.speciality.SpecialityService;
import org.dragberry.era.common.speciality.SpecialitySimpleTO;
import org.dragberry.era.web.security.AccessContoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specialities")
public class SpecialityController {
	
	@Autowired
	private AccessContoll accessContoll;
	
	@Autowired
	private SpecialityService specialityService;
	
	@GetMapping("/get-list-for-registrations")
	public ResponseEntity<List<SpecialitySimpleTO>> getListForRegistrations(@RequestParam("periodId") Long periodId) {
		return ResponseEntity.ok(specialityService.getListForRegistrations(accessContoll.getLoggedUser().getCustomerId(), periodId));
	}

}
