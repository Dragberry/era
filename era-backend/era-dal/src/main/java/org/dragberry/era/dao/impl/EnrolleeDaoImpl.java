package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.EnrolleeDao;
import org.dragberry.era.domain.Enrollee;
import org.springframework.stereotype.Repository;

@Repository
public class EnrolleeDaoImpl extends AbstractDao<Enrollee> implements EnrolleeDao {

	public EnrolleeDaoImpl() {
		super(Enrollee.class);
	}

}
