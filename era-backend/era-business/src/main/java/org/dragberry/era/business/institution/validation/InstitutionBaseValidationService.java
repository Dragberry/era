package org.dragberry.era.business.institution.validation;

import org.dragberry.era.business.validation.AbstractValidationService;
import org.dragberry.era.domain.EducationInstitutionBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionBaseValidationService extends AbstractValidationService<EducationInstitutionBase> {

	@Autowired
	public InstitutionBaseValidationService(InstitutionNameValidator institutionNameValidator) {
		addValidator(institutionNameValidator);
	}
}
