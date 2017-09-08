package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.Specialty;

@Converter
public class SpecialtyStatusConverter implements AttributeConverter<Specialty.Status, Character> {

	@Override
	public Character convertToDatabaseColumn(Specialty.Status attribute) {
		return attribute.value;
	}

	@Override
	public Specialty.Status convertToEntityAttribute(Character dbData) {
		return Specialty.Status.valueOf(dbData);
	}
}
