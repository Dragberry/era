package org.dragberry.era.business;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountryServiceBeanTest {

	private CountryService countryService = new CountryServiceBean();
	
	@Test
	public void testCountries() {
		assertTrue(countryService.getCountryCodes().contains("BY"));
		assertTrue(countryService.getCountryCodes().contains("RU"));
		assertTrue(countryService.getCountryCodes().contains("UA"));
		assertTrue(countryService.getCountryCodes().contains("KZ"));
	}

}
