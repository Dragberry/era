package org.dragberry.era.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import org.dragberry.era.domain.EducationBase;
import org.dragberry.era.domain.EducationForm;
import org.dragberry.era.domain.FundsSource;
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
		if (query.getSpecialtyId() != null) {
			where.add(cb.equal(regRoot.get("specialty").get("entityKey"), query.getSpecialtyId()));
		}
		if (query.getFundsSource() != null) {
			where.add(cb.equal(regRoot.get("fundsSource"), FundsSource.resolve(query.getFundsSource())));
		}
		if (query.getEducationBase() != null) {
			where.add(cb.equal(regRoot.get("educationBase"), EducationBase.resolve(query.getEducationBase())));
		}
		if (query.getEducationForm() != null) {
			where.add(cb.equal(regRoot.get("educationForm"), EducationForm.resolve(query.getEducationForm())));
		}
		if (query.getRegistrationId() != null) {
			where.add(cb.equal(regRoot.get("registrationId"), query.getRegistrationId()));
		} else {
			if (StringUtils.isNotEmpty(query.getName())) {
				where.add(cb.or(
					Arrays.stream(query.getName().split("\\s+")).distinct().map(word -> forLike(word)).flatMap(word -> {
						return Stream.of(
								cb.like(regRoot.get("enrollee").get("firstName"), word),
								cb.like(regRoot.get("enrollee").get("lastName"), word),
								cb.like(regRoot.get("enrollee").get("middleName"), word));
					}).collect(Collectors.toList()).toArray(new Predicate[] {})));
			}
		}
		cq.where(where.toArray(new Predicate[] {}));
		cq.select(regRoot).distinct(true);
		
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	@Override
	public long findMaxRegistrationId(Registration registration) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Registration> regRoot = cq.from(Registration.class);
		
		List<Predicate> where = new ArrayList<>();
		where.add(cb.equal(regRoot.get("registrationPeriod"), registration.getRegistrationPeriod()));
		where.add(cb.equal(regRoot.get("specialty"), registration.getSpecialty()));
		
		// Criteria
		cq.select(cb.max(regRoot.get("registrationId"))).where(where.toArray(new Predicate[] {}));
		return Optional.ofNullable(getEntityManager().createQuery(cq).getSingleResult()).orElse(0L);
	}
}
