package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.PrerogativeDao;
import org.dragberry.era.domain.Prerogative;
import org.springframework.stereotype.Repository;

@Repository
public class PrerogativeDaoImpl extends BenefitDaoImpl<Prerogative> implements PrerogativeDao {

	public PrerogativeDaoImpl() {
		super(Prerogative.class);
	}

}
