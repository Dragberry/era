package org.dragberry.era.business.registration.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.Registration;

public class AddressValidator implements Validator<Registration> {

	private interface Errors extends RegistrationCommon {
		String ADDRESS_IS_EMPTY = RegistrationCommon.errorCode("enrollee.address-details-is-empty");
		String COUNTRY_IS_EMPTY = RegistrationCommon.errorCode("enrollee.address-country-is-empty");
		String COUNTRY_IS_INVALID = RegistrationCommon.errorCode("enrollee.address-country-is-invalid");
		String CITY_IS_EMPTY = RegistrationCommon.errorCode("enrollee.address-city-is-empty");
		String CITY_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.address-city-is-too-long");
	}
	
	private interface FieldID {
		String COUNTRY = "aCountry";
		String CITY = "aCity";
	}
	
	private static final Set<String> COUNTRY_LIST = new HashSet<>();
	static {
		COUNTRY_LIST.add("BY");
		COUNTRY_LIST.add("RU");
		COUNTRY_LIST.add("UA");
	}
	
	@Override
	public List<IssueTO> validate(Registration entity) {
		if (entity.getEnrollee() == null) {
			return Collections.emptyList();
		}
		Address address = entity.getEnrollee().getAddress();
		if (address == null) {
			return issues(Issues.create(Errors.ADDRESS_IS_EMPTY));
		}
		List<IssueTO> issues = new ArrayList<>();
		// Country
		if (address.getCountry() == null) {
			issues.add(Issues.create(Errors.COUNTRY_IS_EMPTY, FieldID.COUNTRY));
		} else {
			if (!COUNTRY_LIST.contains(address.getCountry())) {
				issues.add(Issues.create(Errors.COUNTRY_IS_INVALID, FieldID.COUNTRY));
			}
		}
		// City
		if (StringUtils.isBlank(address.getCity())) {
			issues.add(Issues.create(Errors.CITY_IS_EMPTY, FieldID.CITY));
		} else {
			issues.addAll(validateFieldMaxLength(address.getCity(), 32, Errors.CITY_IS_TOO_LONG, FieldID.CITY));
		}
		return issues;
	}

}
