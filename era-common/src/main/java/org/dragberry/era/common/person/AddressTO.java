package org.dragberry.era.common.person;

import java.io.Serializable;

public class AddressTO implements Serializable {

	private static final long serialVersionUID = 1121527703277352614L;

	private String country;
	
	private String city;
	
	private String street;

	private String house;

	private String housing;

	private String flat;

	private String zipCode;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getHousing() {
		return housing;
	}

	public void setHousing(String housing) {
		this.housing = housing;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}
