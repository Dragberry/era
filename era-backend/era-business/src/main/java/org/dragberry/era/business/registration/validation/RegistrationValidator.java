package org.dragberry.era.business.registration.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.validation.AbstractValidator;
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
public class RegistrationValidator extends AbstractValidator<Registration> implements RegistrationValidationHelper {
	
	private static final String COMMA = ", ";
	private static final String BRACKET_L = "[";
	private static final String BRACKET_R = "]";
	private static final String BRACKETS= BRACKET_L + BRACKET_R;

	@Autowired
	private AccessControl accessControl;
	
	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	
	private class Errors {
		final String noRegistrationPeriod = errorCode("no-registration-period");
		final String registrationPeriodIsWrong = errorCode("registration-period-is-wrong");
		final String registrationPeriodIsNotOpened = errorCode("registration-period-is-not-opened");
		final String educationInstitutionIsEmpty = errorCode("education-institution-is-empty");
		final String specialtyIsEmpty = errorCode("specialty-is-empty");
		final String specialtyIsWrong = errorCode("specialty-is-wrong");
		final String fundsSourceIsEmpty = errorCode("funds-source-is-empty");
		final String fundsSourceIsNotAvailable = errorCode("funds-source-is-not-available");
		final String educationFormIsEmpty = errorCode("education-form-is-empty");
		final String educationFormIsNotAvailable  = errorCode("education-form-is-not-available");
		final String educationBaseIsEmpty = errorCode("education-base-is-empty");
		final String educationBaseIsNotAvailable  = errorCode("education-base-is-not-available");
		final String educationInstitutionIsWrong = errorCode("education-institution-is-wrong");
		final String prerogativeIsDeleted = errorCode("prerogative-is-deleted");
		final String outOfCompetitionIsDeleted = errorCode("out-of-competition-is-deleted");
	}
	
	private class FieldID {
		final String educationInstitution = "educationInstitution";
		final String specialty = "specialty";
		final String fundsSource = "fundsSource";
		final String educationForm = "educationForm";
		final String educationBase = "educationBase";
		final String prerogatives = "prerogatives";
		final String outOfCompetitions = "outOfCompetitions";
	}
	
	private final Errors errors = new Errors();
	private final FieldID fields = new FieldID();
	
	@Override
	protected List<IssueTO> validateEntity(Registration entity) {
		List<IssueTO> issues = new ArrayList<>();
		RegistrationPeriod period = entity.getRegistrationPeriod();
		if (entity.getInstitution() == null) {
			issues.add(Issues.error(errors.educationInstitutionIsEmpty, fields.educationInstitution));
		} else {
			List<RegistrationPeriod> periods = registrationPeriodDao.findActivePeriodsForCustomer(accessControl.getLoggedUser().getCustomerId());
			if (period == null) {
				issues.add(Issues.error(errors.noRegistrationPeriod));
			} else {
				if (!periods.stream().anyMatch(p -> p.getEntityKey().equals(period.getEntityKey()))) {
					issues.add(Issues.error(errors.registrationPeriodIsWrong));
				} else {
					if (period.getStatus() != RegistrationPeriod.Status.OPENED) {
						issues.add(Issues.error(errors.registrationPeriodIsNotOpened));
					}
				}
				if (!period.getEducationInstitution().getEntityKey().equals(entity.getInstitution().getEntityKey())) {
					issues.add(Issues.error(errors.educationInstitutionIsWrong, fields.educationInstitution));
				}
			}
		}
		if (entity.getSpecialty() == null) {
			issues.add(Issues.error(errors.specialtyIsEmpty, fields.specialty));
		} else {
			if (period != null && !period.getSpecialties().stream()
					.anyMatch(spec -> spec.getSpecialty().getEntityKey().equals(entity.getSpecialty().getEntityKey()))) {
				issues.add(Issues.error(errors.specialtyIsWrong, fields.specialty));
			}
		}
		if (entity.getFundsSource() == null) {
			issues.add(Issues.error(errors.fundsSourceIsEmpty, fields.fundsSource));
		} else {
			if (period != null && !period.getSpecialties().stream()
					.anyMatch(spec -> spec.getFundsSources().contains(entity.getFundsSource()))) {
				issues.add(Issues.error(errors.fundsSourceIsNotAvailable, fields.fundsSource));
			}
		}
		if (entity.getEducationForm() == null) {
			issues.add(Issues.error(errors.educationFormIsEmpty, fields.educationForm));
		} else {
			if (period != null && !period.getSpecialties().stream()
					.anyMatch(spec -> spec.getEducationForms().contains(entity.getEducationForm()))) {
				issues.add(Issues.error(errors.educationFormIsNotAvailable, fields.educationForm));
			}
		}
		if (entity.getEducationBase() == null) {
			issues.add(Issues.error(errors.educationBaseIsEmpty, fields.educationBase));
		} else {
			if (period != null && !period.getSpecialties().stream()
					.anyMatch(spec -> spec.getEducationBases().contains(entity.getEducationBase()))) {
				issues.add(Issues.error(errors.educationBaseIsNotAvailable, fields.educationBase));
			}
		}
		
		issues.addAll(validateBenefits(entity.getPrerogatives(), errors.prerogativeIsDeleted,  fields.prerogatives));
		issues.addAll(validateBenefits(entity.getOutOfCompetitions(), errors.outOfCompetitionIsDeleted,  fields.outOfCompetitions));
		
		return issues;
	}
	
	private static List<IssueTO> validateBenefits(List<? extends Benefit> benefits, String msg, String fieldID) {
		if (CollectionUtils.isNotEmpty(benefits)) {
			String deleted = benefits.stream()
				.filter(Benefit::getDeleted)
				.map(Benefit::getName)
				.collect(Collectors.joining(COMMA, BRACKET_L, BRACKET_R));
			if (!deleted.equals(BRACKETS)) {
				return Arrays.asList(Issues.error(msg, fieldID, deleted));
			}
		}
		return Collections.emptyList();
	}

	@Override
	protected String errorPrefix() {
		return BASE_ERROR_CODE_PREFIX;
	}

	@Override
	protected String fieldPrefix() {
		return StringUtils.EMPTY;
	}

}
