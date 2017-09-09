package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.Registration;

@Converter
public class FundsSourceConverter implements AttributeConverter<Registration.FundsSource, Character> {

	@Override
	public Character convertToDatabaseColumn(Registration.FundsSource attribute) {
		return attribute.value;
	}

	@Override
	public Registration.FundsSource convertToEntityAttribute(Character dbData) {
		return Registration.FundsSource.valueOf(dbData);
	}
}
