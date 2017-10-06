package org.dragberry.era.business.institution.validation;

import java.util.List;

import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.domain.EducationInstitutionBase;
import org.springframework.stereotype.Component;

@Component
public class InstitutionNameValidator implements Validator<EducationInstitutionBase> {

	@Override
	public List<IssueTO> validate(EducationInstitutionBase entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
