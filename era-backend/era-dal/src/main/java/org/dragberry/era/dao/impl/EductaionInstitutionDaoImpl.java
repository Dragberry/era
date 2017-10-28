package org.dragberry.era.dao.impl;

import javax.persistence.NoResultException;

import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.domain.EducationInstitution;
import org.springframework.stereotype.Repository;

@Repository
public class EductaionInstitutionDaoImpl extends AbstractDao<EducationInstitution> implements EducationInstitutionDao {

	public EductaionInstitutionDaoImpl() {
		super(EducationInstitution.class);
	}

	@Override
	public EducationInstitution findByCustomer(Long customerKey) {
		try {
			return getEntityManager().createNamedQuery(EducationInstitution.FIND_BY_CUSTOMER, EducationInstitution.class)
					.setParameter(CUSTOMER_KEY, customerKey)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
