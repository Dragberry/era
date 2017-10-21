package org.dragberry.era.business.registration.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.validation.AbstractValidator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;

public abstract class AbstractPersonValidator extends AbstractValidator<Registration> implements RegistrationValidationHelper {

	protected class Errors {
		final String detailsIsEmpty = errorCode("details-is-empty");
		final String firstNameIsEmpty = errorCode("first-name-is-empty");
		final String firstNameIsTooLong = errorCode("first-name-is-too-long");
		final String lastNameIsEmpty = errorCode("last-name-is-empty");
		final String lastNameIsTooLong = errorCode("last-name-is-too-long");
		final String middleNameIsTooLong = errorCode("middle-name-is-too-long");
		final String birthdayIsEmpty = errorCode("birthdate-is-empty");
		final String birthdayIsTooYoung = errorCode("birthday-is-too-young");
		final String phoneIsEmpty = errorCode("contact-details.phone-is-empty");
	}
	
	private class FieldID {
		final String firstName = fieldId("FirstName");
		final String lastName = fieldId("LastName");
		final String middleName = fieldId("MiddleName");
		final String birthDate = fieldId("Birthdate");
		final String phone = fieldId("Phone");
	}
	
	private final Errors errors = new Errors();
	private final FieldID fields = new FieldID();
	
	protected abstract Person getPerson(Registration entity);
	
	@Override
	protected List<IssueTO> validateEntity(Registration entity) {
		Person person = getPerson(entity);
		if (person == null) {
			return issues(Issues.warning(errors.detailsIsEmpty));
		}
		List<IssueTO> issues = new ArrayList<>();
		// First name
		if (StringUtils.isBlank(person.getFirstName())) {
			issues.add(Issues.error(errors.firstNameIsEmpty, fields.firstName));
		} else {
			issues.addAll(validateFieldMaxLength(person.getFirstName(), 32, errors.firstNameIsTooLong, fields.firstName));
		}
		// Last name
		if (StringUtils.isBlank(person.getLastName())) {
			issues.add(Issues.error(errors.lastNameIsEmpty, fields.lastName));
		} else {
			issues.addAll(validateFieldMaxLength(person.getLastName(), 32, errors.lastNameIsTooLong, fields.lastName));
		}
		// Middle name
		issues.addAll(validateFieldMaxLength(person.getMiddleName(), 32, errors.middleNameIsTooLong, fields.middleName));
		// Birthdate
		if (person.getBirthdate() == null) {
			issues.add(Issues.error(errors.birthdayIsEmpty, fields.birthDate));
		} else {
			if (person.getBirthdate().isAfter(LocalDate.now().minusYears(age()))) {
				issues.add(Issues.error(errors.birthdayIsTooYoung, fields.birthDate, age()));
			}
		}
		// Phone
		if (StringUtils.isBlank(person.getPhone())) {
			issues.add(Issues.error(errors.phoneIsEmpty, fields.phone));
		}
		return issues;
	}

	protected abstract int age();
}
