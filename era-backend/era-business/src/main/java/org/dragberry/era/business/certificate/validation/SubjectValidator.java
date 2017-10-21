package org.dragberry.era.business.certificate.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.registration.validation.RegistrationValidationHelper;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.dao.SubjectDao;
import org.dragberry.era.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectValidator implements Validator<Subject> {
	
	@Autowired
	private SubjectDao subjectDao;
	
	private interface Errors extends RegistrationValidationHelper {
		String PREFIX = "validation.subject.";
		String TITLE_IS_EMPTY =  PREFIX + "title-is-empty";
		String TITLE_IS_TOO_SHORT = PREFIX + "title-is-too-short";
		String TITLE_IS_TOO_LONG = PREFIX + "title-is-too-long";
		String ORDER_IS_EMPTY = PREFIX + "order-is-empty";
		String BASE_IS_EMPTY = PREFIX + "base-is-empty";
		String CODE_LENGTH_IS_INVALID = PREFIX + "code-length-is-invalid";
		String SUBJECT_ALREADY_EXISTS =  PREFIX + "subject-already-exists";
	}
	
	@Override
	public List<IssueTO> validate(Subject entity) {
		List<IssueTO> issues = new ArrayList<>();
		// Title
		if (StringUtils.isBlank(entity.getTitle())) {
			issues.add(Issues.error(Errors.TITLE_IS_EMPTY));
		} else {
			issues.addAll(validateFieldLength(entity.getTitle(), 2, Errors.TITLE_IS_TOO_SHORT, 32, Errors.TITLE_IS_TOO_LONG));
		}
		if (issues.isEmpty()) {
			if (subjectDao.findByTitle(entity.getTitle()) != null) {
				issues.add(Issues.error(Errors.SUBJECT_ALREADY_EXISTS, entity.getTitle()));
			}
		}
		// Order
		if (entity.getOrder() == null) {
			issues.add(Issues.error(Errors.ORDER_IS_EMPTY));
		}
		// Base
		if (entity.isBase() == null) {
			issues.add(Issues.error(Errors.BASE_IS_EMPTY));
		}
		// Code
		if (entity.getCode() != null) {
			issues.addAll(validateFieldLength(entity.getCode(), 4, Errors.CODE_LENGTH_IS_INVALID));
		}
		return issues;
	}

}
