package org.dragberry.era.business.registration.validation;

import java.util.ArrayList;
import java.util.List;

import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.security.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationValidator implements Validator<Registration> {

	@Autowired
	private AccessControl accessControl;
	
	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	
	private interface Errors extends RegistrationCommon {
		String NO_REGISTRATION_PERIOD = RegistrationCommon.errorCode("no-registration-period");
		String REGISTRATION_PERIOD_IS_WRONG = RegistrationCommon.errorCode("registration-period-is-wrong");
		String REGISTRATION_PERIOD_IS_NOT_OPENED = RegistrationCommon.errorCode("registration-period-is-not-opened");
		String EDUCATION_INSTITUTION_IS_EMPTY = RegistrationCommon.errorCode("education-institution-is-empty");
		String SPECIALTY_IS_EMPTY = RegistrationCommon.errorCode("specialty-is-empty");
		String SPECIALTY_IS_WRONG = RegistrationCommon.errorCode("specialty-is-wrong");
		String FUNDS_SOURCE_IS_EMPTY = RegistrationCommon.errorCode("funds-source-is-empty");
		String FUNDS_SOURCE_IS_NOT_AVAILABLE = RegistrationCommon.errorCode("funds-source-is-not-available");
		String EDUCATION_FORM_IS_EMPTY = RegistrationCommon.errorCode("education-form-is-empty");
		String EDUCATION_FORM_IS_NOT_AVAILABLE  = RegistrationCommon.errorCode("education-form-is-not-available");
		String EDUCATION_BASE_IS_EMPTY = RegistrationCommon.errorCode("education-base-is-empty");
		String EDUCATION_BASE_IS_NOT_AVAILABLE  = RegistrationCommon.errorCode("education-base-is-not-available");
		String EDUCATION_INSTITUTION_IS_WRONG = RegistrationCommon.errorCode("education-institution-is-wrong");
	}
	
	private interface FieldID {
		String EDUCATION_INSTITUTION = "educationInstitution";
		String SPECIALTY = "specialty";
		String FUNDS_SOURCE = "fundsSource";
		String EDUCATION_FORM = "educationForm";
		String EDUCATION_BASE = "educationBase";
	}
	
	@Override
	public List<IssueTO> validate(Registration entity) {
		List<IssueTO> issues = new ArrayList<>();
		RegistrationPeriod period = entity.getRegistrationPeriod();
		if (entity.getInstitution() == null) {
			issues.add(Issues.create(Errors.EDUCATION_INSTITUTION_IS_EMPTY, FieldID.EDUCATION_INSTITUTION));
		} else {
			List<RegistrationPeriod> periods = registrationPeriodDao.findActivePeriodsForCustomer(accessControl.getLoggedUser().getCustomerId());
			if (period == null) {
				issues.add(Issues.create(Errors.NO_REGISTRATION_PERIOD));
			} else {
				if (!periods.stream().anyMatch(p -> p.getEntityKey().equals(period.getEntityKey()))) {
					issues.add(Issues.create(Errors.REGISTRATION_PERIOD_IS_WRONG));
				} else {
					if (period.getStatus() != RegistrationPeriod.Status.OPENED) {
						issues.add(Issues.create(Errors.REGISTRATION_PERIOD_IS_NOT_OPENED));
					}
				}
				if (!period.getEducationInstitution().getEntityKey().equals(entity.getInstitution().getEntityKey())) {
					issues.add(Issues.create(Errors.EDUCATION_INSTITUTION_IS_WRONG, FieldID.EDUCATION_INSTITUTION));
				}
			}
		}
		if (entity.getSpecialty() == null) {
			issues.add(Issues.create(Errors.SPECIALTY_IS_EMPTY, FieldID.SPECIALTY));
		} else {
			if (period != null && !period.getSpecialties().stream()
					.anyMatch(spec -> spec.getSpecialty().getEntityKey().equals(entity.getSpecialty().getEntityKey()))) {
				issues.add(Issues.create(Errors.SPECIALTY_IS_WRONG, FieldID.SPECIALTY));
			}
		}
		if (entity.getFundsSource() == null) {
			issues.add(Issues.create(Errors.FUNDS_SOURCE_IS_EMPTY, FieldID.FUNDS_SOURCE));
		} else {
			if (period != null && !period.getSpecialties().stream()
					.anyMatch(spec -> spec.getFundsSources().contains(entity.getFundsSource()))) {
				issues.add(Issues.create(Errors.FUNDS_SOURCE_IS_NOT_AVAILABLE, FieldID.FUNDS_SOURCE));
			}
		}
		if (entity.getEducationForm() == null) {
			issues.add(Issues.create(Errors.EDUCATION_FORM_IS_EMPTY, FieldID.EDUCATION_FORM));
		} else {
			if (period != null && !period.getSpecialties().stream()
					.anyMatch(spec -> spec.getEducationForms().contains(entity.getEducationForm()))) {
				issues.add(Issues.create(Errors.EDUCATION_FORM_IS_NOT_AVAILABLE, FieldID.EDUCATION_FORM));
			}
		}
		if (entity.getEducationBase() == null) {
			issues.add(Issues.create(Errors.EDUCATION_BASE_IS_EMPTY, FieldID.EDUCATION_BASE));
		} else {
			if (period != null && !period.getSpecialties().stream()
					.anyMatch(spec -> spec.getEducationBases().contains(entity.getEducationBase()))) {
				issues.add(Issues.create(Errors.EDUCATION_BASE_IS_NOT_AVAILABLE, FieldID.EDUCATION_BASE));
			}
		}
		return issues;
	}

}
