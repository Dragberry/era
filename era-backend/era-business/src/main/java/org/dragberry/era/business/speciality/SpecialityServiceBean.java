package org.dragberry.era.business.speciality;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.speciality.SpecialitySimpleTO;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialityServiceBean implements SpecialityService {

	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	
	@Override
	public List<SpecialitySimpleTO> getListForRegistrations(Long customerKey, Long registrationPeriodId) {
		return registrationPeriodDao.findSpecialitiesForPeriod(customerKey, registrationPeriodId).stream().map(entity -> {
			SpecialitySimpleTO to = new SpecialitySimpleTO();
			to.setId(entity.getEntityKey());
			to.setName(entity.getTitle());
			return to;
		}).collect(Collectors.toList());
	}

}
