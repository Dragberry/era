package org.dragberry.era.web.controller;

import java.io.Serializable;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.dragberry.era.web.security.AccessContoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@Autowired
	private AccessContoll accessControl;
	
	@RequestMapping(value = "/customer/info", method = RequestMethod.GET)
	public ResponseEntity<CustomerInfo> getCustomerInfo(HttpServletRequest request, Principal principal) {
		accessControl.checkPermission("ROLE_REGISTRATIONSVIEW");
		return new ResponseEntity<CustomerInfo>(new CustomerInfo(), HttpStatus.OK);
	}
}

class CustomerInfo implements Serializable {

	private static final long serialVersionUID = -1894356060618930725L;
	
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
