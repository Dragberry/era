package org.dragberry.era.dao.impl;

import java.util.List;

import org.dragberry.era.dao.BenefitDao;
import org.dragberry.era.domain.Benefit;
import org.springframework.stereotype.Repository;

@Repository
public class BenefitDaoImpl extends AbstractDao<Benefit> implements BenefitDao {

	public BenefitDaoImpl() {
		super(Benefit.class);
	}
	
	@Override
	public List<Benefit> fetchActiveBenefits() {
		return getEntityManager().createNamedQuery(Benefit.FETCH_ACTIVE_BENEFITS, Benefit.class)
				.getResultList();
	}

}
