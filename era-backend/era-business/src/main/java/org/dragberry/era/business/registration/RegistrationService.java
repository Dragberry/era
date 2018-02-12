package org.dragberry.era.business.registration;

import java.util.List;

import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.registration.RegistrationCRUDTO;
import org.dragberry.era.common.registration.RegistrationPeriodTO;
import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.common.registration.RegistrationTO;

public interface RegistrationService {
	
	List<RegistrationTO> getRegistrationList(RegistrationSearchQuery query);

	ResultTO<RegistrationCRUDTO> createRegistration(RegistrationCRUDTO registration);
	
	List<RegistrationPeriodTO> getActiveRegistrationPeriods(Long customerKey);

	List<RegistrationPeriodTO> getRegistrationPeriodList(Long customerKey);

	RegistrationCRUDTO fetchDetails(Long id);

	ResultTO<RegistrationCRUDTO> approveRegistration(RegistrationCRUDTO registration);

	ResultTO<RegistrationCRUDTO> cancelRegistration(RegistrationCRUDTO crud);

	ResultTO<RegistrationCRUDTO> updateRegistration(RegistrationCRUDTO registration);
}
