package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.EducationBase;

@Converter
public class EducationBaseConverter implements AttributeConverter<EducationBase, String> {

	@Override
	public String convertToDatabaseColumn(EducationBase attribute) {
		return attribute.value;
	}

	@Override
	public EducationBase convertToEntityAttribute(String dbData) {
		return EducationBase.resolve(dbData);
	}
}
