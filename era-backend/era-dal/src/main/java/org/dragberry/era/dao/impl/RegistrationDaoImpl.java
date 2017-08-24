package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationDaoImpl extends AbstractDao<Registration> implements RegistrationDao {

	public RegistrationDaoImpl() {
		super(Registration.class);
	}

}
