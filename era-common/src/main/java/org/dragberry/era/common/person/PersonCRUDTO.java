package org.dragberry.era.common.person;

import java.time.LocalDate;

import org.dragberry.era.common.AbstractCRUDTO;

public class PersonCRUDTO extends AbstractCRUDTO {

	private static final long serialVersionUID = -3719332346039339698L;

	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private LocalDate birthdate;
	
	private DocumentTO document;
	
	private AddressTO address;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public DocumentTO getDocument() {
		return document;
	}

	public void setDocument(DocumentTO document) {
		this.document = document;
	}

	public AddressTO getAddress() {
		return address;
	}

	public void setAddress(AddressTO address) {
		this.address = address;
	}
	
}
