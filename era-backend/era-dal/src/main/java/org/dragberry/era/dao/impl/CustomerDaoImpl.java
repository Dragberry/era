package org.dragberry.era.dao.impl;

import java.util.List;

import org.dragberry.era.dao.CustomerDao;
import org.dragberry.era.domain.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl extends AbstractDao<Customer> implements CustomerDao {

	public CustomerDaoImpl() {
		super(Customer.class);
	}

	public Customer findByUserAccountKey(Long userAccountKey) {
		List<Customer> result = getEntityManager()
				.createNamedQuery(Customer.FIND_BY_USER_ACCOUNT_KEY_QUERY, getEntityType())
				.setParameter("userAccountKey", userAccountKey).getResultList();
		return result.size() > 0 ? result.get(0) : null;
	}

}
