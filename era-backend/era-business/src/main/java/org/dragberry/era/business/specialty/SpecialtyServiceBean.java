package org.dragberry.era.business.specialty;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.specialty.SpecialtySimpleTO;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyServiceBean implements SpecialtyService {

	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	
	@Override
	public List<SpecialtySimpleTO> getListForRegistrations(Long customerKey, Long registrationPeriodId) {
		return registrationPeriodDao.findSpecialtiesForPeriod(customerKey, registrationPeriodId).stream().map(entity -> {
			SpecialtySimpleTO to = new SpecialtySimpleTO();
			to.setId(entity.getEntityKey());
			to.setName(entity.getTitle());
			return to;
		}).collect(Collectors.toList());
	}

}
