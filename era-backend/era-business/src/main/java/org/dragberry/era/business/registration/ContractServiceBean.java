package org.dragberry.era.business.registration;

import java.io.OutputStream;

import org.dragberry.era.business.reporting.ReportBuilder;
import org.dragberry.era.business.reporting.ReportBuilderFactory;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.ReportTemplateDao;
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
	
}
