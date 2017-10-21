package org.dragberry.era.business.registration.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.CountryService;
import org.dragberry.era.business.validation.AbstractValidator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDocumentValidator extends AbstractValidator<Registration> implements RegistrationValidationHelper {
	
	@Autowired
	private CountryService countryService;

	private class Errors {
		final String isEmpty = errorCode("details-is-empty");
		final String typeIsInvalid = errorCode("type-is-invalid");
		final String idIsEmpty = errorCode("id-is-empty");
		final String idHasInvalidLength = errorCode("id-has-invalid-length");
		final String isIsEmpty = errorCode("document-id-is-empty");
		final String isHasInvalidLength = errorCode("document-id-has-invalid-length");
		final String issueDateIsEmpty = errorCode("issue-date-is-empty");
		final String issueDateIsInFuture = errorCode("issue-date-is-in-future");
		final String issuedByIsEmpty = errorCode("issued-by-is-empty");
		final String issuedByIsToLong = errorCode("issued-by-is-too-long");
		final String citizenshipIsEmpty = errorCode("citizenship-is-empty");
		final String citizenshipIsInvalid = errorCode("citizenship-is-invalid");
	}
	
	private class FieldID {
		final String type = fieldId("Type");
		final String id = fieldId("Id");
		final String documentId = fieldId("DocumentId");
		final String issueDate = fieldId("IssueDate");
		final String issuedBy = fieldId("IssuedBy");
		final String citienship = fieldId("Citizenship");
	}
	
	protected final Errors errors = new Errors();
	protected final FieldID fields = new FieldID();
	
	protected abstract Person getPerson(Registration registration);
	
	protected abstract Document getDocument(Registration registration);
	
	@Override
	protected boolean shouldValidate(Registration entity) {
		return getPerson(entity) != null;
	}
	
	@Override
	protected List<IssueTO> validateEntity(Registration entity) {
		List<IssueTO> issues = new ArrayList<>();
		// Document
		Document document = getDocument(entity);
		if (document == null) {
			issues.add(Issues.warning(errors.isEmpty));
		} else {
			// Document type
			if (document.getType() == null) {
				issues.add(Issues.error(errors.typeIsInvalid, fields.type));
			}
			// Id
			if (StringUtils.isBlank(document.getId())) {
				issues.add(Issues.warning(errors.idIsEmpty, fields.id));
			} else {
				issues.addAll(validateFieldLength(document.getId(), 14, errors.idHasInvalidLength, fields.id));
			}
			// Document id
			if (StringUtils.isBlank(document.getDocumentId())) {
				issues.add(Issues.warning(errors.isIsEmpty, fields.documentId));
			} else {
				issues.addAll(validateFieldLength(document.getDocumentId(), 9, errors.isHasInvalidLength, fields.documentId));
			}
			// Document issue date
			if (document.getIssueDate() == null) {
				issues.add(Issues.warning(errors.issueDateIsEmpty, fields.issueDate));
			} else {
				if (document.getIssueDate().isAfter(LocalDate.now())) {
					issues.add(Issues.error(errors.issueDateIsInFuture, fields.issueDate));
				}
			}
			// Document issued by
			if (StringUtils.isBlank(document.getIssuedBy())) {
				issues.add(Issues.warning(errors.issuedByIsEmpty, fields.issuedBy));
			} else {
				issues.addAll(validateFieldMaxLength(document.getIssuedBy(), 32, errors.issuedByIsToLong, fields.issuedBy));
			}
			// Document citizenship
			if (StringUtils.isBlank(document.getCitizenhip())) {
				issues.add(Issues.warning(errors.citizenshipIsEmpty, fields.citienship));
			} else {
				if (!countryService.getCountryCodes().contains(document.getCitizenhip())) {
					issues.add(Issues.error(errors.citizenshipIsInvalid, fields.citienship));
				}
			}
		}
		
		return issues;
	}

}
