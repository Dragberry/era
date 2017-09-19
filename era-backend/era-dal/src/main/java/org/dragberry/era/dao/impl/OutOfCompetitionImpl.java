package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.OutOfCompetitionDao;
import org.dragberry.era.domain.OutOfCompetition;
import org.springframework.stereotype.Repository;

@Repository
public class OutOfCompetitionImpl extends BenefitDaoImpl<OutOfCompetition> implements OutOfCompetitionDao {

	public OutOfCompetitionImpl() {
		super(OutOfCompetition.class);
	}

}
