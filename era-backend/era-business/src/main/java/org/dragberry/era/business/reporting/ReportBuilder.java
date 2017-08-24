package org.dragberry.era.business.reporting;

import java.io.OutputStream;

import org.dragberry.era.domain.ReportTemplate;

public interface ReportBuilder {

	void setDataProvider(DataProvider dataProvider);

	void build(ReportTemplate template, OutputStream os);

}
