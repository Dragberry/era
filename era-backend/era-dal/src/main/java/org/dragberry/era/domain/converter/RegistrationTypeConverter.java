package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.Registration;

@Converter
public class RegistrationTypeConverter implements AttributeConverter<Registration.Type, Character> {

	@Override
	public Character convertToDatabaseColumn(Registration.Type attribute) {
		return attribute.value;
	}

	@Override
	public Registration.Type convertToEntityAttribute(Character dbData) {
		return Registration.Type.valueOf(dbData);
	}
}
