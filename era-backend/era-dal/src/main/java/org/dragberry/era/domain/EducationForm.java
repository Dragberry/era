package org.dragberry.era.domain;

public enum EducationForm implements BaseEnum {
	FULL_TIME('F'), EXTRAMURAL('E');
	
	public final char value;
	
	private EducationForm(char value) {
		this.value = value;
	}
	
	public static EducationForm resolve(Character value) {
		if (value == null) {
			throw BaseEnum.npeException(EducationForm.class);
		}
		for (EducationForm form : EducationForm.values()) {
			if (value.equals(form.value)) {
				return form;
			}
		}
		throw BaseEnum.unknownValueException(EducationForm.class, value);
	}
}