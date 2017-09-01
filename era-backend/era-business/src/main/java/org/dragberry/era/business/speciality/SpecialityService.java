package org.dragberry.era.business.speciality;

import java.util.List;

import org.dragberry.era.common.speciality.SpecialitySimpleTO;

public interface SpecialityService {

	List<SpecialitySimpleTO> getListForRegistrations(Long customerKey, Long registrationPeriodId);

}
