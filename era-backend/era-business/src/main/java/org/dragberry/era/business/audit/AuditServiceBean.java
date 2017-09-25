package org.dragberry.era.business.audit;

import java.time.LocalDateTime;

import org.dragberry.era.dao.AuditRecordDao;
import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.AbstractEntity;
import org.dragberry.era.domain.AuditRecord;
import org.dragberry.era.domain.AuditRecord.Action;
import org.dragberry.era.security.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceBean implements AuditService {

	@Autowired
	private AccessControl accessControl;
	
	@Autowired
	private AuditRecordDao auditRecordDao;
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Override
	public <E extends AbstractEntity> AuditRecord logEvent(AuditRecord.Action action, Class<E> entityType) {
		AuditRecord record = new AuditRecord();
		record.setUserAccount(userAccountDao.findOne(accessControl.getLoggedUser().getId()));
		record.setDate(LocalDateTime.now());
		record.setIp(accessControl.getRemoteAddress());
		record.setEntityType(entityType.getName());
		record.setAction(action);
		return auditRecordDao.create(record);
	}

	@Override
	public <E extends AbstractEntity> AuditRecord logEvent(Action action, Class<E> entityType, Long entityKey) {
		AuditRecord record = new AuditRecord();
		record.setUserAccount(userAccountDao.findOne(accessControl.getLoggedUser().getId()));
		record.setDate(LocalDateTime.now());
		record.setIp(accessControl.getRemoteAddress());
		record.setEntityType(entityType.getName());
		record.setReferencedEntityKey(entityKey);
		record.setAction(action);
		return auditRecordDao.create(record);
	}
	
	@Override
	public <E extends AbstractEntity> AuditRecord logEvent(Action action, Class<E> entityType, E old, E updated) {
		AuditRecord record = new AuditRecord();
		record.setUserAccount(userAccountDao.findOne(accessControl.getLoggedUser().getId()));
		record.setDate(LocalDateTime.now());
		record.setIp(accessControl.getRemoteAddress());
		record.setEntityType(entityType.getName());
		record.setReferencedEntityKey(old.getEntityKey());
		record.setAction(action);
		return auditRecordDao.create(record);
	}

}
