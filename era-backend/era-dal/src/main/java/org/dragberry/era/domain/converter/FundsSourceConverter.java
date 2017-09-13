package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.FundsSource;

@Converter
public class FundsSourceConverter implements AttributeConverter<FundsSource, Character> {

	@Override
	public Character convertToDatabaseColumn(FundsSource attribute) {
		return attribute.value;
	}

	@Override
	public FundsSource convertToEntityAttribute(Character dbData) {
		return FundsSource.resolve(dbData);
	}
}
