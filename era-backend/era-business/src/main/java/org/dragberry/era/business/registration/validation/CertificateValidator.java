package org.dragberry.era.business.registration.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.validation.AbstractValidator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Certificate;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class CertificateValidator extends AbstractValidator<Registration> implements RegistrationValidationHelper {
	
	private static final Integer LOW_MARK = 0;
	private static final Integer HIGH_MARK = 10;

	private class Errors {
		final String isEmpty = errorCode("is-empty");
		final String enrolleeIsEmpty = errorCode("enrollee-is-empty");
		final String institutionIsEmpty = errorCode("institution-is-empty");
		final String yearIsEmpty = errorCode("year-is-empty");
		final String yearInFuture = errorCode("year-is-in-future");
		final String markIsLess = errorCode("mark-is-less");
		final String markIsGreater = errorCode("mark-is-greater");
	}
	
	private final Errors errors = new Errors();
	
	@Override
	protected List<IssueTO> validateEntity(Registration entity) {
		Certificate cert = entity.getCertificate();
		if (cert == null) {
			return issues(Issues.warning(errors.isEmpty));
		}
		List<IssueTO> issues = new ArrayList<>();
		if (cert.getEnrollee() == null) {
			issues.add(Issues.error(errors.enrolleeIsEmpty));
		}
		if (cert.getInstitution() == null) {
			issues.add(Issues.warning(errors.institutionIsEmpty));
		}
		if (cert.getYear() == null) {
			issues.add(Issues.warning(errors.yearIsEmpty));
		} else if (cert.getYear() > LocalDate.now().getYear()) {
			issues.add(Issues.error(errors.yearInFuture));
		}
		cert.getMarks().entrySet().forEach(sm -> {
			Integer low = 0;
			Integer high = 10;
			if (sm.getValue() < low) {
				issues.add(Issues.error(errors.markIsLess, new Object[] {sm.getKey().getTitle(), LOW_MARK}));
			} else if (sm.getValue() > high) {
				issues.add(Issues.error(errors.markIsGreater, new Object[] {sm.getKey().getTitle(), HIGH_MARK}));
			}
		});
		return issues;
	}

	@Override
	protected String errorPrefix() {
		return BASE_ERROR_CODE_PREFIX + "certificate.";
	}

	@Override
	protected String fieldPrefix() {
		return StringUtils.EMPTY;
	}

}
