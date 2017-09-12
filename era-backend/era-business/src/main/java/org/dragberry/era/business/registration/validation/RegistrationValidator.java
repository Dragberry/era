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
		String NO_ACTIVE_REGISTRATION_PERIOD = RegistrationCommon.errorCode("no-active-registration-period");
		String EDUCATION_INSTITUTION_IS_EMPTY = RegistrationCommon.errorCode("education-institution-is-empty");
		String SPECIALTY_IS_EMPTY = RegistrationCommon.errorCode("specialty-is-empty");
		String FUNDS_SOURCE_IS_EMPTY = RegistrationCommon.errorCode("funds-source-is-empty");
		String EDUCATION_FORM_IS_EMPTY = RegistrationCommon.errorCode("education-form-is-empty");
		String EDUCATION_INSTITUTION_IS_WRONG = RegistrationCommon.errorCode("education-institution-is-wrong");
	}
	
	private interface FieldID {
		String EDUCATION_INSTITUTION = "educationInstitution";
		String SPECIALTY = "specialty";
		String FUNDS_SOURCE = "fundsSource";
		String EDUCATION_FORM = "educationForm";
	}
	
	@Override
	public List<IssueTO> validate(Registration entity) {
		List<IssueTO> issues = new ArrayList<>();
		if (entity.getInstitution() == null) {
			issues.add(Issues.create(Errors.EDUCATION_INSTITUTION_IS_EMPTY, FieldID.EDUCATION_INSTITUTION));
		} else {
			RegistrationPeriod period = registrationPeriodDao.findActivePeriodForCustomer(accessControl.getLoggedUser().getCustomerId());
			if (period == null) {
				issues.add(Issues.create(Errors.NO_ACTIVE_REGISTRATION_PERIOD));
			} else {
				if (!period.getEducationInstitution().getEntityKey().equals(entity.getInstitution().getEntityKey())) {
					issues.add(Issues.create(Errors.EDUCATION_INSTITUTION_IS_WRONG, FieldID.EDUCATION_INSTITUTION));
				}
			}
		}
		if (entity.getSpecialty() == null) {
			issues.add(Issues.create(Errors.SPECIALTY_IS_EMPTY, FieldID.SPECIALTY));
		}
		if (entity.getFundsSource() == null) {
			issues.add(Issues.create(Errors.FUNDS_SOURCE_IS_EMPTY, FieldID.FUNDS_SOURCE));
		}
		if (entity.getEducationForm() == null) {
			issues.add(Issues.create(Errors.EDUCATION_FORM_IS_EMPTY, FieldID.EDUCATION_FORM));
		}
		return issues;
	}

}
