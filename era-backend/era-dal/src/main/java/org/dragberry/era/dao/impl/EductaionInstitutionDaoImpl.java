package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.domain.EducationInstitution;
import org.springframework.stereotype.Repository;

@Repository
public class EductaionInstitutionDaoImpl extends AbstractDao<EducationInstitution> implements EducationInstitutionDao {

	public EductaionInstitutionDaoImpl() {
		super(EducationInstitution.class);
	}

}
