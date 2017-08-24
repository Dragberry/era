package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.CustomerDao;
import org.dragberry.era.domain.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl extends AbstractDao<Customer> implements CustomerDao {

	public CustomerDaoImpl() {
		super(Customer.class);
	}

}
