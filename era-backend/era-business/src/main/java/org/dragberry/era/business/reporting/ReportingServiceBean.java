package org.dragberry.era.business.reporting;

import org.dragberry.era.dao.ReportTemplateDao;
import org.dragberry.era.domain.ReportTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingServiceBean implements ReportingService {

	@Autowired
	private ReportTemplateDao reportTemplateDao;
	
	@Override
	public ReportTemplateInfo getReportInfo(Long reportTemplateKey) {
		ReportTemplate template = reportTemplateDao.findOne(reportTemplateKey);
		if (template != null) {
			ReportTemplateInfo info = new ReportTemplateInfo();
			info.setReportTemplateKey(template.getEntityKey());
			info.setFileName(template.getTitle() + "." + template.getType().fileExtension);
			info.setMime(template.getType().mime);
			return info;
		}
		return null;
	}

}
