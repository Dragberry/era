package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.domain.RegisteredSpecialty;
import org.dragberry.era.domain.RegistrationPeriod;

public interface RegistrationPeriodDao extends DataAccessObject<RegistrationPeriod, Long> {

	RegistrationPeriod findActivePeriodForCustomer(Long customerKey);
	
	List<RegisteredSpecialty> findSpecialtiesForPeriod(Long customerKey, Long periodKey);

}
