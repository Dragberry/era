package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.domain.RegistrationPeriod;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationPeriodDaoImpl extends AbstractDao<RegistrationPeriod> implements RegistrationPeriodDao {

	public RegistrationPeriodDaoImpl() {
		super(RegistrationPeriod.class);
	}

}
