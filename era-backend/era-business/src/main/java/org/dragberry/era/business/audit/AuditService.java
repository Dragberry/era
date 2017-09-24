package org.dragberry.era.business.audit;

import org.dragberry.era.domain.AbstractEntity;
import org.dragberry.era.domain.AuditRecord;

public interface AuditService {
	
	<E extends AbstractEntity> AuditRecord logEvent(AuditRecord.Action action, Class<E> entityType);

	<E extends AbstractEntity> AuditRecord logEvent(AuditRecord.Action action, Class<E> entityType, Long entityKey);
	
	<E extends AbstractEntity> AuditRecord logEvent(AuditRecord.Action action, Class<E> entityType, E  old, E updated);
	
}
