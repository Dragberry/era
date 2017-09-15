package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.Registration;

@Converter
public class RegistrationStatusConverter implements AttributeConverter<Registration.Status, Character> {

	@Override
	public Character convertToDatabaseColumn(Registration.Status attribute) {
		return attribute.value;
	}

	@Override
	public Registration.Status convertToEntityAttribute(Character dbData) {
		return Registration.Status.resolve(dbData);
	}
}
