package org.dragberry.era.web.controller;

import org.dragberry.era.business.useraccount.UserAccountService;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.useraccount.RoleHolderTO;
import org.dragberry.era.common.useraccount.UserAccountCreateTO;
import org.dragberry.era.common.useraccount.UserAccountDeleteTO;
import org.dragberry.era.web.security.AccessContoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		userAccount.getRoles().stream().filter(RoleHolderTO::getEnabled).map(RoleHolderTO::getRole).forEach(accessContoll::checkPermission);
		return ResponseEntity.ok(userAccountService.create(userAccount));
	}
	
	@DeleteMapping("/delete/{userAccountId}")
	public ResponseEntity<?> deleteUserAccount(@PathVariable Long userAccountId) {
		accessContoll.checkPermission("ROLE_USERACCOUNTS_DELETE");
		UserAccountDeleteTO request = new UserAccountDeleteTO();
		request.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		request.setUserAccountId(accessContoll.getLoggedUser().getId());
		request.setId(userAccountId);
		return ResponseEntity.ok(userAccountService.delete(request));
	}

}
