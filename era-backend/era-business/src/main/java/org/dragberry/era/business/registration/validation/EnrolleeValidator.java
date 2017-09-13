package org.dragberry.era.business.registration.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class EnrolleeValidator implements Validator<Registration> {

	private interface Errors extends RegistrationCommon {
		String ENROLLEE_DETAILS_IS_EMPTY = RegistrationCommon.errorCode("enrollee.details-is-empty");
		String ENROLLEE_FIRST_NAME_IS_EMPTY = RegistrationCommon.errorCode("enrollee.first-name-is-empty");
		String ENROLLEE_FIRST_NAME_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.first-name-is-too-long");
		String ENROLLEE_LAST_NAME_IS_EMPTY = RegistrationCommon.errorCode("enrollee.last-name-is-empty");
		String ENROLLEE_LAST_NAME_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.last-name-is-too-long");
		String ENROLLEE_MIDDLE_NAME_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.middle-name-is-too-long");
		String ENROLLEE_BIRTHDATE_IS_EMPTY = RegistrationCommon.errorCode("enrollee.birthdate-is-empty");
		String ENROLLEE_BIRTHDAY_IS_TOO_YOUNG = RegistrationCommon.errorCode("enrollee.birthday-is-too-young");
		String ENROLLEE_PHONE_IS_EMPTY = RegistrationCommon.errorCode("enrollee.contact-details.phone-is-empty");
	}
	
	private interface FieldID {
		String ENROLEE_FIRST_NAME = "eFirstName";
		String ENROLEE_LAST_NAME = "eLastName";
		String ENROLEE_MIDDLE_NAME = "eMiddleName";
		String ENROLEE_BIRTHDATE = "eBirthdate";
		String ENROLEE_PHONE = "ePhone";
	}
	
	@Override
	public List<IssueTO> validate(Registration entity) {
		Person enrollee = entity.getEnrollee();
		if (enrollee == null) {
			return issues(Issues.create(Errors.ENROLLEE_DETAILS_IS_EMPTY));
		}
		List<IssueTO> issues = new ArrayList<>();
		// First name
		if (StringUtils.isBlank(enrollee.getFirstName())) {
			issues.add(Issues.create(Errors.ENROLLEE_FIRST_NAME_IS_EMPTY, FieldID.ENROLEE_FIRST_NAME));
		} else {
			issues.addAll(validateFieldMaxLength(enrollee.getFirstName(), 32, Errors.ENROLLEE_FIRST_NAME_IS_TOO_LONG, FieldID.ENROLEE_FIRST_NAME));
		}
		// Last name
		if (StringUtils.isBlank(enrollee.getLastName())) {
			issues.add(Issues.create(Errors.ENROLLEE_LAST_NAME_IS_EMPTY, FieldID.ENROLEE_LAST_NAME));
		} else {
			issues.addAll(validateFieldMaxLength(enrollee.getLastName(), 32, Errors.ENROLLEE_LAST_NAME_IS_TOO_LONG, FieldID.ENROLEE_LAST_NAME));
		}
		// Middle name
		issues.addAll(validateFieldMaxLength(enrollee.getMiddleName(), 32, Errors.ENROLLEE_MIDDLE_NAME_IS_TOO_LONG, FieldID.ENROLEE_MIDDLE_NAME));
		// Birthdate
		if (enrollee.getBirthdate() == null) {
			issues.add(Issues.create(Errors.ENROLLEE_BIRTHDATE_IS_EMPTY, FieldID.ENROLEE_BIRTHDATE));
		} else {
			int age = 15;
			if (enrollee.getBirthdate().isAfter(LocalDate.now().minusYears(age))) {
				issues.add(Issues.create(Errors.ENROLLEE_BIRTHDAY_IS_TOO_YOUNG, FieldID.ENROLEE_BIRTHDATE, age));
			}
		}
		// Phone
		if (StringUtils.isBlank(enrollee.getPhone())) {
			issues.add(Issues.create(Errors.ENROLLEE_PHONE_IS_EMPTY, FieldID.ENROLEE_PHONE));
		}
		return issues;
	}

}
