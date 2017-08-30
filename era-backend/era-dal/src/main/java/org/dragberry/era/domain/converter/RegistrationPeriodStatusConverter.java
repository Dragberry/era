package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.RegistrationPeriod;

@Converter
public class RegistrationPeriodStatusConverter implements AttributeConverter<RegistrationPeriod.Status, Character> {

	@Override
	public Character convertToDatabaseColumn(RegistrationPeriod.Status attribute) {
		return attribute.value;
	}

	@Override
	public RegistrationPeriod.Status convertToEntityAttribute(Character dbData) {
		return RegistrationPeriod.Status.valueOf(dbData);
	}
}
