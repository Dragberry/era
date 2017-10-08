package org.dragberry.era.business.registration.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Certificate;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class CertificateValidator implements Validator<Registration> {
	
	private static final Integer LOW_MARK = 0;
	private static final Integer HIGH_MARK = 10;

	private interface Errors extends RegistrationCommon {
		String CERTIFICATE_IS_EMPTY = RegistrationCommon.errorCode("certificate.is-empty");
		String ENROLLEE_IS_EMPTY = RegistrationCommon.errorCode("certificate.enrollee-is-empty");
		String INSTITUTION_IS_EMPTY = RegistrationCommon.errorCode("certificate.institution-is-empty");
		String YEAR_IS_EMPTY = RegistrationCommon.errorCode("certificate.year-is-empty");
		String YEAS_IS_IN_FUTURE = RegistrationCommon.errorCode("certificate.year-is-in-future");
		String MARK_IS_LESS = RegistrationCommon.errorCode("certificate.mark-is-less");
		String MARK_IS_GREATER = RegistrationCommon.errorCode("certificate.mark-is-greater");
	}
	
	@Override
	public List<IssueTO> validate(Registration entity) {
		Certificate cert = entity.getCertificate();
		if (cert == null) {
			return issues(Issues.warning(Errors.CERTIFICATE_IS_EMPTY));
		}
		List<IssueTO> issues = new ArrayList<>();
		if (cert.getEnrollee() == null) {
			issues.add(Issues.error(Errors.ENROLLEE_IS_EMPTY));
		}
		if (cert.getInstitution() == null) {
			issues.add(Issues.warning(Errors.INSTITUTION_IS_EMPTY));
		}
		if (cert.getYear() == null) {
			issues.add(Issues.warning(Errors.YEAR_IS_EMPTY));
		} else if (cert.getYear() > LocalDate.now().getYear()) {
			issues.add(Issues.error(Errors.YEAS_IS_IN_FUTURE));
		}
		cert.getMarks().entrySet().forEach(sm -> {
			Integer low = 0;
			Integer high = 10;
			if (sm.getValue() < low) {
				issues.add(Issues.error(Errors.MARK_IS_LESS, new Object[] {sm.getKey().getTitle(), LOW_MARK}));
			} else if (sm.getValue() > high) {
				issues.add(Issues.error(Errors.MARK_IS_GREATER, new Object[] {sm.getKey().getTitle(), HIGH_MARK}));
			}
		});
		return issues;
	}

}
