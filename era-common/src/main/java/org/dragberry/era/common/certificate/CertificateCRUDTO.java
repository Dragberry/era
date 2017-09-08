package org.dragberry.era.common.certificate;

import java.util.List;

import org.dragberry.era.common.AbstractCRUDTO;

public class CertificateCRUDTO extends AbstractCRUDTO {

	private static final long serialVersionUID = 817707147440284169L;
	
	private Long id;

	private String institution;
	
	private Integer year;
	
	private List<SubjectMarkTO> marks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<SubjectMarkTO> getMarks() {
		return marks;
	}

	public void setMarks(List<SubjectMarkTO> marks) {
		this.marks = marks;
	}
	
}
