package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.Document;

@Converter
public class DocumentTypeConverter implements AttributeConverter<Document.Type, Character> {

	@Override
	public Character convertToDatabaseColumn(Document.Type attribute) {
		return attribute.value;
	}

	@Override
	public Document.Type convertToEntityAttribute(Character dbData) {
		return Document.Type.valueOf(dbData);
	}
}
