package org.dragberry.era.web.controller.customer;

import org.dragberry.era.business.customer.CustomerService;
import org.dragberry.era.common.customer.CustomerDetailsTO;
import org.dragberry.era.web.security.AccessContoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	
	@Autowired
	private AccessContoll accessControl;
	@Autowired
	private CustomerService customerServie;
	
	@GetMapping("api/customer/fetch-details")
	public ResponseEntity<CustomerDetailsTO> getCustomerInfo() {
		return ResponseEntity.ok(customerServie.getCustomerDetailsForUserAccount(accessControl.getLoggedUser().getId()));
	}

}
