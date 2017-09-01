package org.dragberry.era.business.registration;

import java.util.List;

import org.dragberry.era.common.registration.RegistrationPeriodTO;
import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.common.registration.RegistrationTO;

public interface RegistrationService {
	
	List<RegistrationTO> getRegistrationList(RegistrationSearchQuery query);

	RegistrationPeriodTO getActiveRegistrationPeriod(Long customerKey);
}
