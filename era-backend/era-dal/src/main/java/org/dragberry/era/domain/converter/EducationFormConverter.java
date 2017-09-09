package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.Registration;

@Converter
public class EducationFormConverter implements AttributeConverter<Registration.EducationForm, Character> {

	@Override
	public Character convertToDatabaseColumn(Registration.EducationForm attribute) {
		return attribute.value;
	}

	@Override
	public Registration.EducationForm convertToEntityAttribute(Character dbData) {
		return Registration.EducationForm.valueOf(dbData);
	}
}
