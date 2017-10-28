package org.dragberry.era.business.reporting;

import java.util.List;

import org.dragberry.era.common.reporting.ReportTemplateInfoTO;

public interface ReportingService {
	
	ReportTemplateInfoTO getReportInfo(Long customerKey, Long reportTemplateKey);
	
	List<ReportTemplateInfoTO> getReportsForCustomer(Long customerKey);

}
