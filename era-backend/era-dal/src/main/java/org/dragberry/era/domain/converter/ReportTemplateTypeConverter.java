package org.dragberry.era.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.dragberry.era.domain.ReportTemplate;

@Converter
public class ReportTemplateTypeConverter implements AttributeConverter<ReportTemplate.Type, String> {

	@Override
	public String convertToDatabaseColumn(ReportTemplate.Type attribute) {
		return attribute.fileExtension;
	}

	@Override
	public ReportTemplate.Type convertToEntityAttribute(String dbData) {
		return ReportTemplate.Type.valueOfFileExtension(dbData);
	}
}
