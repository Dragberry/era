package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.ReportTemplateDao;
import org.dragberry.era.domain.ReportTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReportTemplateDaoImpl extends AbstractDao<ReportTemplate> implements ReportTemplateDao {

	public ReportTemplateDaoImpl() {
		super(ReportTemplate.class);
	}

}
