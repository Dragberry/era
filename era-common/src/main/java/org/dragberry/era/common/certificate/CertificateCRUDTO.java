package org.dragberry.era.common.certificate;

import java.util.List;

import org.dragberry.era.common.AbstractCRUDTO;
import org.dragberry.era.common.institution.EducationInstitutionBaseCRUDTO;

public class CertificateCRUDTO extends AbstractCRUDTO {

	private static final long serialVersionUID = 817707147440284169L;
	
	private EducationInstitutionBaseCRUDTO institution;
	
	private Integer year;
	
	private List<SubjectMarkCRUDTO> marks;
	
	private List<SubjectMarkCRUDTO> extraMarks;

	public EducationInstitutionBaseCRUDTO getInstitution() {
		return institution;
	}

	public void setInstitution(EducationInstitutionBaseCRUDTO institution) {
		this.institution = institution;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<SubjectMarkCRUDTO> getMarks() {
		return marks;
	}

	public void setMarks(List<SubjectMarkCRUDTO> marks) {
		this.marks = marks;
	}

	public List<SubjectMarkCRUDTO> getExtraMarks() {
		return extraMarks;
	}

	public void setExtraMarks(List<SubjectMarkCRUDTO> extraMarks) {
		this.extraMarks = extraMarks;
	}
	
}
