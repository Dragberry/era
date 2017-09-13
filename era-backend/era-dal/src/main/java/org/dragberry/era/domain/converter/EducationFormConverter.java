package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.EducationForm;

@Converter
public class EducationFormConverter implements AttributeConverter<EducationForm, Character> {

	@Override
	public Character convertToDatabaseColumn(EducationForm attribute) {
		return attribute.value;
	}

	@Override
	public EducationForm convertToEntityAttribute(Character dbData) {
		return EducationForm.resolve(dbData);
	}
}
