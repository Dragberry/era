package org.dragberry.era.common.certificate;

import java.io.Serializable;
import java.util.Map;


public class CertificateTO implements Serializable {

	private static final long serialVersionUID = 817707147440284169L;
	
	private String institution;
	
	private String country;
	
	private Integer year;
	
	private Map<String, Integer> marks;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Map<String, Integer> getMarks() {
		return marks;
	}

	public void setMarks(Map<String, Integer> marks) {
		this.marks = marks;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
