package org.dragberry.era.business.registration;

import java.io.OutputStream;

import org.dragberry.era.common.reporting.ReportTemplateInfoTO;

public interface ContractService {

	void generateRegistrationContract(Long registrationKey, Long reportTemplateKey, OutputStream os);

	ReportTemplateInfoTO findReportTemplate(Long registrationId, Long customerKey);
}
