package org.dragberry.era.business.institution;

import java.util.List;

import org.dragberry.era.common.institution.EducationInstitutionTO;

public interface EducationInstitutionService {

	List<EducationInstitutionTO> getInstitutionListForRegistration(Long customerId);

}