package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.AuditRecordDao;
import org.dragberry.era.domain.AuditRecord;
import org.springframework.stereotype.Repository;

@Repository
public class AuditRecordDaoImpl extends AbstractDao<AuditRecord> implements AuditRecordDao {

	public AuditRecordDaoImpl() {
		super(AuditRecord.class);
	}
}
