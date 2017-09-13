package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.RegisteredSpecialtyDao;
import org.dragberry.era.domain.RegisteredSpecialty;
import org.springframework.stereotype.Repository;

@Repository
public class RegisteredSpecialtyDaoImpl extends AbstractDao<RegisteredSpecialty> implements RegisteredSpecialtyDao {

	public RegisteredSpecialtyDaoImpl() {
		super(RegisteredSpecialty.class);
	}

}
