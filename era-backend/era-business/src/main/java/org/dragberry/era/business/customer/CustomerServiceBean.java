package org.dragberry.era.business.customer;

import org.dragberry.era.common.customer.CustomerDetailsTO;
import org.dragberry.era.dao.CustomerDao;
import org.dragberry.era.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceBean implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public CustomerDetailsTO getCustomerDetailsForUserAccount(Long customerKey) {
		Customer customer = customerDao.findOne(1000L);
		CustomerDetailsTO details = new CustomerDetailsTO();
		details.setId(customer.getEntityKey());
		details.setCustomerName(customer.getCustomerName());
		return details;
	}
}
