package org.dragberry.era.dao.impl;

import java.util.List;

import org.dragberry.era.dao.EducationInstitutionBaseDao;
import org.dragberry.era.domain.EducationInstitutionBase;
import org.springframework.stereotype.Repository;

@Repository
public class EducationInstitutionBaseDaoImpl extends AbstractDao<EducationInstitutionBase> implements EducationInstitutionBaseDao {

	private static final String NAME = "name";
	private static final String COUNTRY = "country";

	public EducationInstitutionBaseDaoImpl() {
		super(EducationInstitutionBase.class);
	}

	@Override
	public List<EducationInstitutionBase> findByNameAndCountry(String name, String country, int count) {
		return getEntityManager().createNamedQuery(EducationInstitutionBase.FIND_BY_NAME_AND_COUNTRY, EducationInstitutionBase.class)
				.setParameter(COUNTRY, country)
				.setParameter(NAME, forLikeWrap(name))
				.setMaxResults(count)
				.getResultList();
	}

}
