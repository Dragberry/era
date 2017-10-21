package org.dragberry.era.business.registration.validation;

import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class PayerDocumentValidator extends AbstractDocumentValidator {

	private final static String ERROR_CODE_PREFIX = BASE_ERROR_CODE_PREFIX + "payer.document.";
	
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
		return "pd";
	}
	
	@Override
	protected Person getPerson(Registration registration) {
		return registration.getPayer();
	}

	@Override
	protected Document getDocument(Registration registration) {
		return registration.getPayer().getDocument();
	}
}
