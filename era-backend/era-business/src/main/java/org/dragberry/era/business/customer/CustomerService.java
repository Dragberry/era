package org.dragberry.era.business.customer;

import org.dragberry.era.common.customer.CustomerDetailsTO;

public interface CustomerService {
	
	CustomerDetailsTO getCustomerDetailsForUserAccount(Long customerKey);

}
