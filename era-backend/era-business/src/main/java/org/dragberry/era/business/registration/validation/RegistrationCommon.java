package org.dragberry.era.business.registration.validation;

public interface RegistrationCommon {
	String ERROR_CODE_PREFIX = "validation.registration.";
	
	static String errorCode(String errorCode) {
		return ERROR_CODE_PREFIX + errorCode;
	}
}
