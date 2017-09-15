package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.BenefitDao;
import org.dragberry.era.domain.Benefit;
import org.springframework.stereotype.Repository;

@Repository
public class BenefitDaoImpl extends AbstractDao<Benefit> implements BenefitDao {

	public BenefitDaoImpl() {
		super(Benefit.class);
	}

}
