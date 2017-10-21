package org.dragberry.era.business.registration.validation;

import org.dragberry.era.business.validation.AbstractValidationService;
import org.dragberry.era.domain.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationValidationService extends AbstractValidationService<Registration>{

	@Autowired
	public RegistrationValidationService(
			AddressValidator addressValidator,
			CertificateValidator certificateValidator,
			DocumentValidator documentValidator,
			EnrolleeValidator enrolleeValidator,
			PayerValidator payerValidator,
			PayerAddressValidator payerAddressValidator,
			PayerDocumentValidator payerDocumentValidator,
			RegistrationValidator registrationValidator) {
		addValidator(addressValidator);
		addValidator(documentValidator);
		addValidator(enrolleeValidator);
		addValidator(registrationValidator);
		addValidator(certificateValidator);
		addValidator(payerValidator);
		addValidator(payerAddressValidator);
		addValidator(payerDocumentValidator);
	}
}
