package org.dragberry.era.business.institution;

import java.util.List;

import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.institution.EducationInstitutionBaseCRUDTO;
import org.dragberry.era.common.institution.EducationInstitutionBaseTO;
import org.dragberry.era.common.institution.EducationInstitutionTO;

public interface EducationInstitutionService {

	List<EducationInstitutionTO> getInstitutionListForRegistration(Long customerId);

	List<EducationInstitutionBaseTO> lookup(String name, String country, int maxSize);
	
	ResultTO<EducationInstitutionBaseCRUDTO> create(EducationInstitutionBaseCRUDTO crudto);

}