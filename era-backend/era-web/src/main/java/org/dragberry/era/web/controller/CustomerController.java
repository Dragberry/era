package org.dragberry.era.web.controller;

import org.dragberry.era.business.customer.CustomerService;
import org.dragberry.era.common.Results;
import org.dragberry.era.security.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private AccessControl accessControl;
	@Autowired
	private CustomerService customerServie;
	
	@GetMapping("/fetch-details")
	public ResponseEntity<?> getCustomerInfo() {
		return ResponseEntity.ok(Results.create(customerServie.getCustomerDetailsForUserAccount(accessControl.getLoggedUser().getCustomerId())));
	}

}
