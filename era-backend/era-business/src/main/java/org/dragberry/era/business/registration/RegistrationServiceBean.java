package org.dragberry.era.business.registration;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.registration.RegistrationPeriodTO;
import org.dragberry.era.common.registration.RegistrationTO;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.domain.RegistrationPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceBean implements RegistrationService {

	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private RegistrationPeriodDao registrationPeriodDao; 
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RegistrationTO> getRegistrationList(Long customerKey) {
		return registrationDao.fetchList().stream().map(entity -> {
			RegistrationTO to = new RegistrationTO();
			to.setFirstName(entity.getEnrollee().getFirstName());
			to.setLastName(entity.getEnrollee().getLastName());
			to.setMiddleName(entity.getEnrollee().getMiddleName());
			to.setId(entity.getEntityKey());
			to.setRegistrationDate(entity.getRegistrationDate());
			to.setRegistrationType(entity.getType().value);
			to.setSpeciality(entity.getSpeciality().getTitle());
			to.setAttestateAvg(entity.getCertificate().getMarks().values().stream()
					.mapToInt(Integer::intValue).average().orElse(0));
			return to;
		}).collect(Collectors.toList());
	}
	
	@Override
	public RegistrationPeriodTO getActiveRegistrationPeriod(Long customerKey) {
		RegistrationPeriod period = registrationPeriodDao.findActivePeriodForCustomer(customerKey);
		if (period != null) {
			RegistrationPeriodTO to = new RegistrationPeriodTO();
			to.setId(period.getEntityKey());
			to.setTitle(period.getTitle());
			return to;
		}
		return null;
	}

}
