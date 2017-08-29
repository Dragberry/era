package org.dragberry.era.business.reporting;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.reporting.ReportTemplateInfoTO;
import org.dragberry.era.dao.ReportTemplateDao;
import org.dragberry.era.domain.ReportTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingServiceBean implements ReportingService {

	@Autowired
	private ReportTemplateDao reportTemplateDao;
	
	@Override
	public ReportTemplateInfoTO getReportInfo(Long reportTemplateKey) {
		ReportTemplate entity = reportTemplateDao.findOne(reportTemplateKey);
		return entity != null ? convertFromEntity(entity) : null;
	}

	@Override
	public List<ReportTemplateInfoTO> getReportsForCustomer(Long customerKey) {
		return reportTemplateDao.fetchList().stream().map(ReportingServiceBean::convertFromEntity).collect(Collectors.toList());
	}

	private static ReportTemplateInfoTO convertFromEntity(ReportTemplate entity) {
		ReportTemplateInfoTO info = new ReportTemplateInfoTO();
		info.setId(entity.getEntityKey());
		info.setFileName(entity.getTitle() + "." + entity.getType().fileExtension);
		info.setMime(entity.getType().mime);
		return info;
	}
}
