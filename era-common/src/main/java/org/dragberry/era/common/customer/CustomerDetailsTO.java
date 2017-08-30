package org.dragberry.era.common.customer;

import java.io.Serializable;

public class CustomerDetailsTO implements Serializable {

	private static final long serialVersionUID = -3619333547434207860L;
	
	private Long id;
	
	private String customerName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
