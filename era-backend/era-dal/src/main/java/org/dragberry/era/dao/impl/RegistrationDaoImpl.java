package org.dragberry.era.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.domain.Customer;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationDaoImpl extends AbstractDao<Registration> implements RegistrationDao {

	public RegistrationDaoImpl() {
		super(Registration.class);
	}

	public List<Registration> searchList(RegistrationSearchQuery query) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Registration> cq = cb.createQuery(Registration.class);
		Root<Registration> regRoot = cq.from(Registration.class);
		Root<Customer> custRoot = cq.from(Customer.class);
		regRoot.fetch("enrollee");
		regRoot.fetch("institution");
		regRoot.fetch("speciality");
		regRoot.fetch("certificate");
		regRoot.fetch("registrationPeriod");
		
		cq.where(
				cb.equal(regRoot.get("institution"), custRoot.get("institution")),
				cb.equal(custRoot.get("entityKey"), query.getCustomerId()),
				cb.equal(regRoot.get("registrationPeriod").get("entityKey"), query.getPeriodId()));
		cq.select(regRoot).distinct(true);
		
		return getEntityManager().createQuery(cq).getResultList();
	}
}
