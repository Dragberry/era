package org.dragberry.era.business.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.IssueTO;

public abstract class AbstractValidationService<E> implements ValidationService<E> {

	protected List<Validator<E>> validators = new ArrayList<>();
	
	@Override
	public List<IssueTO> validate(E entity) {
		return validators.stream().flatMap(validator -> validator.validate(entity).stream()).collect(Collectors.toList());
	}

	@Override
	public void addValidator(Validator<E> validator) {
		validators.add(validator);
	}

}
