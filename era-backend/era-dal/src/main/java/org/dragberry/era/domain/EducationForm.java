package org.dragberry.era.domain;

public enum EducationForm implements BaseEnum<Character> {
	FULL_TIME('F'), EXTRAMURAL('E');
	
	public final Character value;
	
	private EducationForm(Character value) {
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
	
	@Override
	public Character getValue() {
		return value;
	}
}