package org.dragberry.era.common.person;

import java.io.Serializable;

public class ContactDetailsTO implements Serializable {

	private static final long serialVersionUID = 1360108102541240725L;

	private String phone;
	
	private String email;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
