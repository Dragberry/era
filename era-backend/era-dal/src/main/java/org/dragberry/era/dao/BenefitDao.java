package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.domain.Benefit;

public interface BenefitDao<B extends Benefit> extends DataAccessObject<B, Long> {

	List<B> fetchActiveBenefits();
	
	List<B> fetchByKeys(List<Long> keys);
}
