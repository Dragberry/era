package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.domain.Benefit;

public interface BenefitDao extends DataAccessObject<Benefit, Long> {

	List<Benefit> fetchActiveBenefits();
}
