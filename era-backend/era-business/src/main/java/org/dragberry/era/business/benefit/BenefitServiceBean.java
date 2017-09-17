package org.dragberry.era.business.benefit;

import org.dragberry.era.common.benefit.BenefitListTO;
import org.dragberry.era.common.benefit.BenefitTO;
import org.dragberry.era.dao.BenefitDao;
import org.dragberry.era.domain.OutOfCompetition;
import org.dragberry.era.domain.Prerogative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenefitServiceBean implements BenefitService {

	@Autowired
	private BenefitDao benefitDao;
	
	@Override
	public BenefitListTO getActiveBenefits() {
		BenefitListTO benefitList = new BenefitListTO();
		benefitDao.fetchActiveBenefits().forEach(entity -> {
			BenefitTO to = new BenefitTO();
			to.setId(entity.getEntityKey());
			to.setName(entity.getName());
			if (entity instanceof Prerogative) {
				benefitList.getPrerogatives().add(to);
			} else if (entity instanceof OutOfCompetition) {
				benefitList.getOutOfCompetitions().add(to);
			}
		});
		return benefitList;
	}

}
