package org.dragberry.era.business.registration;

import java.io.OutputStream;

public interface ContractService {

	void generateRegistrationContract(Long registrationKey, Long reportTemplateKey, OutputStream os);
}
