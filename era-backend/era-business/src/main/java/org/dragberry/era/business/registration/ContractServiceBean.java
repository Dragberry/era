package org.dragberry.era.business.registration;

import java.io.OutputStream;

import org.dragberry.era.business.reporting.ReportBuilder;
import org.dragberry.era.business.reporting.ReportBuilderFactory;
import org.dragberry.era.common.reporting.ReportTemplateInfoTO;
import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.ReportTemplateDao;
import org.dragberry.era.domain.EducationInstitution;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.ReportTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractServiceBean implements ContractService {
	
	@Autowired
	private ReportBuilderFactory factory;
	
	@Autowired
	private EducationInstitutionDao educationInstitutionDao;
	
	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private ReportTemplateDao reportTemplateDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void generateRegistrationContract(Long registrationKey, Long reportTemplateKey, OutputStream os) {
		ReportTemplate template = reportTemplateDao.findOne(reportTemplateKey);
		if (template != null) {
			ReportBuilder builder = factory.getBuilder(template.getType());
			builder.setDataProvider(new ContractDataProvider(registrationDao, registrationKey));
			builder.build(template, os);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ReportTemplateInfoTO findReportTemplate(Long registrationId, Long customerKey) {
		Registration registration = registrationDao.findOne(registrationId);
		EducationInstitution ins = educationInstitutionDao.findByCustomer(customerKey);
		if (registration != null) {
			if (ins.equals(registration.getInstitution())) {
				ReportTemplate report = ins.getRegistrationContracts().get(registration.getFundsSource());
				ReportTemplateInfoTO info = new ReportTemplateInfoTO();
				info.setId(report.getEntityKey());
				info.setFileName(report.getTitle() + "." + report.getType().fileExtension);
				info.setMime(report.getType().mime);
				return info;
			}
		}
		return null;
	}
	
}
