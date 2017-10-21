package org.dragberry.era.business.registration.validation;

import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class PayerValidator extends AbstractPersonValidator {

	private final static String ERROR_CODE_PREFIX = BASE_ERROR_CODE_PREFIX + "payer.";
	
	@Override
	protected boolean shouldValidate(Registration entity) {
		return super.shouldValidate(entity) && entity.getFundsSource() == FundsSource.PAYER;
	}
	
	@Override
	protected String errorPrefix() {
		return ERROR_CODE_PREFIX;
	}

	@Override
	protected String fieldPrefix() {
		return "p";
	}

	@Override
	protected Person getPerson(Registration entity) {
		return entity.getPayer();
	}

	@Override
	protected int age() {
		return 18;
	}

}
