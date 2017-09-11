package org.dragberry.era.business.validation;

import java.util.List;

import org.dragberry.era.common.IssueTO;

public interface ValidationService<E> {

	List<IssueTO> validate(E entity);

	void addValidator(Validator<E> validator);
}
