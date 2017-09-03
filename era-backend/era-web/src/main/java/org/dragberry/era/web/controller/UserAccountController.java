package org.dragberry.era.web.controller;

import org.dragberry.era.business.useraccount.UserAccountService;
import org.dragberry.era.common.Results;
import org.dragberry.era.web.security.AccessContoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-account")
public class UserAccountController {
	
	@Autowired
	private AccessContoll accessContoll;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@RequestMapping("/get-list")
	public ResponseEntity<?> fetchList() {
		return ResponseEntity.ok(Results.create(userAccountService.getListForCustomer(accessContoll.getLoggedUser().getCustomerId())));
	}

}
