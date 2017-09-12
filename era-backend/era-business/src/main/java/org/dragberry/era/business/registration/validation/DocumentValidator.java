package org.dragberry.era.business.registration.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.Registration;

public class DocumentValidator implements Validator<Registration> {

	private interface Errors extends RegistrationCommon {
		String DOCUMENT_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document.details-is-empty");
		String DOCUMENT_TYPE_IS_INVALID = RegistrationCommon.errorCode("enrollee.document.type-is-invalid");
		String ID_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document.id-is-empty");
		String ID_HAS_INVALID_LENGTH = RegistrationCommon.errorCode("enrollee.document.id-has-invalid-length");
		String DOCUMENT_ID_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document.document-id-is-empty");
		String DOCUMENT_ID_HAS_INVALID_LENGTH = RegistrationCommon.errorCode("enrollee.document.document-id-has-invalid-length");
		String DOCUMENT_ISSUE_DATE_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document.issue-date-is-empty");
		String DOCUMENT_ISSUE_DATE_IS_IN_FUTURE = RegistrationCommon.errorCode("enrollee.document.issue-date-is-in-future");
		String DOCUMENT_ISSUED_BY_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document.issued-by-is-empty");
		String DOCUMENT_ISSUED_BY_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.document.issued-by-is-too-long");
	}
	
	private interface FieldID {
		String DOCUMENT_TYPE = "dType";
		String DOCUMENT_ID = "dId";
		String DOCUMENT_DOCUMENT_ID = "dDocumentId";
		String DOCUMENT_ISSUE_DATE = "dIssueDate";
		String DOCUMENT_ISSUED_BY = "dIssuedBy";
	}
	
	@Override
	public List<IssueTO> validate(Registration entity) {
		if (entity.getEnrollee() == null) {
			Collections.emptyList();
		}
		List<IssueTO> issues = new ArrayList<>();
		// Document
		Document document = entity.getEnrollee().getDocument();
		if (document == null) {
			issues.add(Issues.create(Errors.DOCUMENT_IS_EMPTY));
		} else {
			// Document type
			if (document.getType() == null) {
				issues.add(Issues.create(Errors.DOCUMENT_TYPE_IS_INVALID, FieldID.DOCUMENT_TYPE));
			}
			// Id
			if (StringUtils.isBlank(document.getId())) {
				issues.add(Issues.create(Errors.ID_IS_EMPTY, FieldID.DOCUMENT_ID));
			} else {
				issues.addAll(validateFieldLength(document.getId(), 14, Errors.ID_HAS_INVALID_LENGTH, FieldID.DOCUMENT_ID));
			}
			// Document id
			if (StringUtils.isBlank(document.getDocumentId())) {
				issues.add(Issues.create(Errors.DOCUMENT_ID_IS_EMPTY, FieldID.DOCUMENT_DOCUMENT_ID));
			} else {
				issues.addAll(validateFieldLength(document.getDocumentId(), 9, Errors.DOCUMENT_ID_HAS_INVALID_LENGTH, FieldID.DOCUMENT_DOCUMENT_ID));
			}
			// Document issue date
			if (document.getIssueDate() == null) {
				issues.add(Issues.create(Errors.DOCUMENT_ISSUE_DATE_IS_EMPTY, FieldID.DOCUMENT_ISSUE_DATE));
			} else {
				if (document.getIssueDate().isAfter(LocalDate.now())) {
					issues.add(Issues.create(Errors.DOCUMENT_ISSUE_DATE_IS_IN_FUTURE, FieldID.DOCUMENT_ISSUE_DATE));
				}
			}
			// Document issued by
			if (StringUtils.isBlank(document.getIssuedBy())) {
				issues.add(Issues.create(Errors.DOCUMENT_ISSUED_BY_IS_EMPTY, FieldID.DOCUMENT_ISSUED_BY));
			} else {
				issues.addAll(validateFieldMaxLength(document.getIssuedBy(), 32, Errors.DOCUMENT_ISSUED_BY_IS_TOO_LONG, FieldID.DOCUMENT_ISSUED_BY));
			}
		}
		
		return issues;
	}

}
