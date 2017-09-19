package org.dragberry.era.business.registration.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.domain.Benefit;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.security.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationValidator implements Validator<Registration> {
	
	private static final String COMMA = ", ";
	private static final String BRACKET_L = "[";
	private static final String BRACKET_R = "]";
	private static final String BRACKETS= BRACKET_L + BRACKET_R;

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
		String PREROGATIVE_IS_DELETED = RegistrationCommon.errorCode("prerogative-is-deleted");
		String OUT_OF_COMPETITION_IS_DELETED = RegistrationCommon.errorCode("out-of-competition-is-deleted");
	}
	
	private interface FieldID {
		String EDUCATION_INSTITUTION = "educationInstitution";
		String SPECIALTY = "specialty";
		String FUNDS_SOURCE = "fundsSource";
		String EDUCATION_FORM = "educationForm";
		String EDUCATION_BASE = "educationBase";
		String PREROGATIVES = "prerogatives";
		String OUT_OF_COMPETITIONS = "outOfCompetitions";
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
		
		issues.addAll(validateBenefits(entity.getPrerogatives(), Errors.PREROGATIVE_IS_DELETED,  FieldID.PREROGATIVES));
		issues.addAll(validateBenefits(entity.getOutOfCompetitions(), Errors.OUT_OF_COMPETITION_IS_DELETED,  FieldID.OUT_OF_COMPETITIONS));
		
		return issues;
	}
	
	private static List<IssueTO> validateBenefits(List<? extends Benefit> benefits, String msg, String fieldID) {
		if (CollectionUtils.isNotEmpty(benefits)) {
			String deleted = benefits.stream()
				.filter(Benefit::getDeleted)
				.map(Benefit::getName)
				.collect(Collectors.joining(COMMA, BRACKET_L, BRACKET_R));
			if (!deleted.equals(BRACKETS)) {
				return Arrays.asList(Issues.create(msg, fieldID, deleted));
			}
		}
		return Collections.emptyList();
	}

}
