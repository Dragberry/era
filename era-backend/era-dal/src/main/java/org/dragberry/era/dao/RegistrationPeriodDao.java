package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.domain.Specialty;

public interface RegistrationPeriodDao extends DataAccessObject<RegistrationPeriod, Long> {

	RegistrationPeriod findActivePeriodForCustomer(Long customerKey);
	
	List<Specialty> findSpecialtiesForPeriod(Long customerKey, Long periodKey);

}
