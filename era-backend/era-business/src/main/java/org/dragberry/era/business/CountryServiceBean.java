package org.dragberry.era.business;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class CountryServiceBean implements CountryService {

	private final static Set<String> COUNTRIES = new HashSet<>();
	static {
		COUNTRIES.add("BY");
		COUNTRIES.add("RU");
		COUNTRIES.add("UA");
		COUNTRIES.add("KZ");
	}
	
	@Override
	public Set<String> getCountryCodes() {
		return COUNTRIES;
	}

}
