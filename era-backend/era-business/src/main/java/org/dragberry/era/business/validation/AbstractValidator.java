package org.dragberry.era.business.validation;

import java.util.Collections;
import java.util.List;

import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;

public abstract class AbstractValidator<T> implements Validator<T> {

	protected String errorCode(String key) {
		return errorPrefix() + key;
	}
	
	protected abstract String errorPrefix(); 
	
	protected String fieldId(String key) {
		return fieldPrefix() + key;
	}
	
	protected abstract String fieldPrefix();
	
	@Override
	public List<IssueTO> validate(T entity) {
		if (shouldValidate(entity)) {
			return validateEntity(entity);
		}
		return Collections.emptyList();
	}

	protected abstract List<IssueTO> validateEntity(T entity);
	
	protected boolean shouldValidate(T entity) {
		return true;
	}

}
