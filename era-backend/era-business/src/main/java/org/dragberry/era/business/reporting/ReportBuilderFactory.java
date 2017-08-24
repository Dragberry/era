package org.dragberry.era.business.reporting;

import org.dragberry.era.domain.ReportTemplate.Type;
import org.springframework.stereotype.Component;

@Component
public class ReportBuilderFactory {
	
	public ReportBuilder getBuilder(Type type) {
		switch (type) {
			case DOCX:
				return new MSWordReportBuilder();
			default:
				throw new UnsupportedOperationException("Unsupported report type: " + type);
		}
	}
	
}
