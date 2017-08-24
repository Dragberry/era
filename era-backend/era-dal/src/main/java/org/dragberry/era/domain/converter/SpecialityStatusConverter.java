package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.Speciality;
import org.dragberry.era.domain.Speciality.Status;

@Converter
public class SpecialityStatusConverter implements AttributeConverter<Speciality.Status, Character> {

	@Override
	public Character convertToDatabaseColumn(Speciality.Status attribute) {
		return attribute.value;
	}

	@Override
	public Speciality.Status convertToEntityAttribute(Character dbData) {
		return Speciality.Status.valueOf(dbData);
	}
}
