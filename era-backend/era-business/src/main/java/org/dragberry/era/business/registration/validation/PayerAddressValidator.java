package org.dragberry.era.business.registration.validation;

import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class PayerAddressValidator extends AbstractAddressValidator {

	@Override
	protected boolean shouldValidate(Registration entity) {
		return super.shouldValidate(entity) && entity.getFundsSource() == FundsSource.PAYER;
	}
	
	@Override
	protected String errorPrefix() {
		return BASE_ERROR_CODE_PREFIX + "payer.address.";
	}

	@Override
	protected String fieldPrefix() {
		return "pa";
	}

	@Override
	protected Person getPerson(Registration registration) {
		return registration.getPayer();
	}

	@Override
	protected Address getAddress(Registration registration) {
		return registration.getPayer().getAddress();
	}
}
