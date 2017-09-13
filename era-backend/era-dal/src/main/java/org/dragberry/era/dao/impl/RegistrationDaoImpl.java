package org.dragberry.era.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
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
		regRoot.fetch("specialty");
		regRoot.fetch("certificate", JoinType.LEFT);
		regRoot.fetch("registrationPeriod");
		
		List<Predicate> where = new ArrayList<>();
		where.add(cb.equal(regRoot.get("institution"), custRoot.get("institution")));
		where.add(cb.equal(custRoot.get("entityKey"), query.getCustomerId()));
		where.add(cb.equal(regRoot.get("registrationPeriod").get("entityKey"), query.getPeriodId()));
		if (StringUtils.isNotEmpty(query.getName())) {
			where.add(cb.or(
				Arrays.stream(query.getName().split("\\s+")).distinct().map(word -> forLike(word)).flatMap(word -> {
					return Stream.of(
							cb.like(regRoot.get("enrollee").get("firstName"), word),
							cb.like(regRoot.get("enrollee").get("lastName"), word),
							cb.like(regRoot.get("enrollee").get("middleName"), word));
				}).collect(Collectors.toList()).toArray(new Predicate[] {})));
		}
		if (query.getSpecialtyId() != null) {
			where.add(cb.equal(regRoot.get("specialty").get("entityKey"), query.getSpecialtyId()));
		}
		if (query.getFundsSource() != null) {
			where.add(cb.equal(regRoot.get("fundsSource"), Registration.FundsSource.valueOf(query.getFundsSource())));
		}
		cq.where(where.toArray(new Predicate[] {}));
		cq.select(regRoot).distinct(true);
		
		return getEntityManager().createQuery(cq).getResultList();
	}
}
