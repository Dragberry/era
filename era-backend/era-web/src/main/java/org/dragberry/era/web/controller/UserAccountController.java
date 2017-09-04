package org.dragberry.era.web.controller;

import org.dragberry.era.business.useraccount.UserAccountService;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.useraccount.UserAccountCreateTO;
import org.dragberry.era.web.security.AccessContoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-account")
public class UserAccountController {
	
	@Autowired
	private AccessContoll accessContoll;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@GetMapping("/get-list")
	public ResponseEntity<?> fetchList() {
		accessContoll.checkPermission("ROLE_USERACCOUNTS_VIEW");
		return ResponseEntity.ok(Results.create(userAccountService.getListForCustomer(accessContoll.getLoggedUser().getCustomerId())));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createUserAccount(@RequestBody UserAccountCreateTO userAccount) {
		accessContoll.checkPermission("ROLE_USERACCOUNTS_CREATE");
		userAccount.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		ResultTO<UserAccountCreateTO> result = userAccountService.create(userAccount);
		return ResponseEntity.ok(result);
	}
	

}
