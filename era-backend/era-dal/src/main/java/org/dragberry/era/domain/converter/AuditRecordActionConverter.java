package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.AuditRecord;

@Converter
public class AuditRecordActionConverter implements AttributeConverter<AuditRecord.Action, Character> {

	@Override
	public Character convertToDatabaseColumn(AuditRecord.Action attribute) {
		return attribute.value;
	}

	@Override
	public AuditRecord.Action convertToEntityAttribute(Character dbData) {
		return AuditRecord.Action.resolve(dbData);
	}
}
