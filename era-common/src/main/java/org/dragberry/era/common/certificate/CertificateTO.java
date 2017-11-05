package org.dragberry.era.common.certificate;

import java.io.Serializable;
import java.util.List;

public class CertificateTO implements Serializable {

	private static final long serialVersionUID = 817707147440284169L;

	private String institution;

	private String country;

	private Integer year;

	private List<SubjectMarkCRUDTO<SubjectCRUDTO>> marks;

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

	public List<SubjectMarkCRUDTO<SubjectCRUDTO>> getMarks() {
		return marks;
	}

	public void setMarks(List<SubjectMarkCRUDTO<SubjectCRUDTO>> marks) {
		this.marks = marks;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
