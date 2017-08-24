package org.dragberry.era.business.reporting;

import java.io.OutputStream;

import org.dragberry.era.domain.ReportTemplate;

public abstract class AbstractReportBuilder implements ReportBuilder {
	
	protected DataProvider dataProvider;
	
	public void setDataProvider(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	public void build(ReportTemplate template, OutputStream os) {
		if (template == null && os == null) {
			throw new IllegalStateException();
		}
		buildReport(template, os);
	}

	protected abstract void buildReport(ReportTemplate template, OutputStream os);

}
