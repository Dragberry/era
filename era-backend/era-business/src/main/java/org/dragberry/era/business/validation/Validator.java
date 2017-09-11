package org.dragberry.era.business.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;

public interface Validator<T> {

	List<IssueTO> validate(T entity);

	default List<IssueTO> issues(IssueTO... issues) {
		return Arrays.asList(issues);
	}
	
	default List<IssueTO> validateFieldLength(String field, int length, String errMsg) {
		return validateFieldLength(field, length, errMsg, null);
	}
	
	default List<IssueTO> validateFieldLength(String field, int length, String errMsg, String fieldId) {
		if (field != null) {
			if (field.length() != length) {
				return issues(Issues.create(errMsg, fieldId, length));
			}
		}
		return Collections.emptyList();
	}
	
	default List<IssueTO> validateFieldLength(String field, int minLength, String errMsgForMin, int maxLength, String errMsgForMax) {
		return validateFieldLength(field, minLength, errMsgForMin, maxLength, errMsgForMax, null);
	}
	
	default List<IssueTO> validateFieldLength(String field, int minLength, String errMsgForMin, int maxLength, String errMsgForMax, String fieldId) {
		if (field != null) {
			if (minLength != 0 && errMsgForMin != null && field.length() < minLength) {
				return issues(Issues.create(errMsgForMin, fieldId, minLength));
			} 
			if (maxLength != 0 && errMsgForMax != null && field.length() > maxLength) {
				return issues(Issues.create(errMsgForMax, fieldId, maxLength));
			}
		}
		return Collections.emptyList();
	}
	
	default List<IssueTO> validateFieldMaxLength(String field, int maxLength, String errMsg) {
		return validateFieldMaxLength(field, maxLength, errMsg, null);
	}
	
	default List<IssueTO> validateFieldMaxLength(String field, int maxLength, String errMsg, String fieldId) {
		return validateFieldLength(field, 0, null, maxLength, errMsg, fieldId);
	}
	
	default List<IssueTO> validateFieldMinLength(String field, int minLength, String errMsg) {
		return validateFieldMinLength(field, minLength, errMsg, null);
	}
	
	default List<IssueTO> validateFieldMinLength(String field, int minLength, String errMsg, String fieldId) {
		return validateFieldLength(field, minLength, errMsg, 0, null, fieldId);
	}
}
