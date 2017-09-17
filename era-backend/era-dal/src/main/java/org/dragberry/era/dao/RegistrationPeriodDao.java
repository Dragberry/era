package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.domain.RegisteredSpecialty;
import org.dragberry.era.domain.RegistrationPeriod;

public interface RegistrationPeriodDao extends DataAccessObject<RegistrationPeriod, Long> {

	List<RegistrationPeriod> findActivePeriodsForCustomer(Long customerKey);
	
	List<RegisteredSpecialty> findSpecialtiesForPeriod(Long customerKey, Long periodKey);

	List<RegistrationPeriod> fetchList(Long customerId);

}
