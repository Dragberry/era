package org.dragberry.era.business.registration;

import java.util.List;

import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.registration.RegistrationCRUDTO;
import org.dragberry.era.common.registration.RegistrationPeriodTO;
import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.common.registration.RegistrationTO;

public interface RegistrationService {
	
	List<RegistrationTO> getRegistrationList(RegistrationSearchQuery query);

	RegistrationPeriodTO getActiveRegistrationPeriod(Long customerKey);

	ResultTO<RegistrationCRUDTO> createRegistration(RegistrationCRUDTO registration);
}
