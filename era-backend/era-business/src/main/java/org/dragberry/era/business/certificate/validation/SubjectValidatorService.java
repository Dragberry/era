package org.dragberry.era.business.certificate.validation;

import org.dragberry.era.business.validation.AbstractValidationService;
import org.dragberry.era.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectValidatorService extends AbstractValidationService<Subject> {

	@Autowired
	public SubjectValidatorService(SubjectValidator subjectValidator) {
		addValidator(subjectValidator);
	}
}
