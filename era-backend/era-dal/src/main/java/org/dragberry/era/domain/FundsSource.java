package org.dragberry.era.domain;

public enum FundsSource implements BaseEnum {
	BUDGET('B'), PAYER('P');
	
	public final char value;
	
	private FundsSource(char value) {
		this.value = value;
	}
	
	public static FundsSource resolve(Character value) {
		if (value == null) {
			throw BaseEnum.npeException(FundsSource.class);
		}
		for (FundsSource src : FundsSource.values()) {
			if (value.equals(src.value)) {
				return src;
			}
		}
		throw BaseEnum.unknownValueException(FundsSource.class, value);
	}
}