package org.dragberry.era.business.specialty;

import java.util.List;

import org.dragberry.era.common.specialty.SpecialtySimpleTO;

public interface SpecialtyService {

	List<SpecialtySimpleTO> getListForRegistrations(Long customerKey, Long registrationPeriodId);

}
