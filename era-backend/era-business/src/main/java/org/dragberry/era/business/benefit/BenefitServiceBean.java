package org.dragberry.era.business.benefit;

import java.util.stream.Collectors;

import org.dragberry.era.common.benefit.BenefitListTO;
import org.dragberry.era.common.benefit.BenefitTO;
import org.dragberry.era.dao.OutOfCompetitionDao;
import org.dragberry.era.dao.PrerogativeDao;
import org.dragberry.era.domain.Benefit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenefitServiceBean implements BenefitService {

	@Autowired
	private PrerogativeDao prerogativeDao;
	@Autowired
	private OutOfCompetitionDao outOfCompetitionDao;
	
	@Override
	public BenefitListTO getActiveBenefits() {
		BenefitListTO benefitList = new BenefitListTO();
		benefitList.setPrerogatives(prerogativeDao.fetchActiveBenefits().stream()
				.map(BenefitServiceBean::convert)
				.collect(Collectors.toList()));
		benefitList.setOutOfCompetitions(outOfCompetitionDao.fetchActiveBenefits().stream()
				.map(BenefitServiceBean::convert)
				.collect(Collectors.toList()));
		return benefitList;
	}

	private static BenefitTO convert(Benefit entity) {
		BenefitTO to = new BenefitTO();
		to.setId(entity.getEntityKey());
		to.setName(entity.getName());
		return to;
	}
}
