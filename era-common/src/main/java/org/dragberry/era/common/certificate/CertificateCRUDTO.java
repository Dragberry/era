package org.dragberry.era.common.certificate;

import java.util.List;

import org.dragberry.era.common.AbstractCRUDTO;
import org.dragberry.era.common.institution.EducationInstitutionBaseCRUDTO;

public class CertificateCRUDTO extends AbstractCRUDTO {

	private static final long serialVersionUID = 817707147440284169L;
	
	private EducationInstitutionBaseCRUDTO institution;
	
	private Integer year;
	
	private List<SubjectMarkCRUDTO<SubjectCRUDTO>> marks;
	
	private List<SubjectMarkCRUDTO<SubjectCRUDTO>> extraMarks;

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

	public List<SubjectMarkCRUDTO<SubjectCRUDTO>> getMarks() {
		return marks;
	}

	public void setMarks(List<SubjectMarkCRUDTO<SubjectCRUDTO>> marks) {
		this.marks = marks;
	}

	public List<SubjectMarkCRUDTO<SubjectCRUDTO>> getExtraMarks() {
		return extraMarks;
	}

	public void setExtraMarks(List<SubjectMarkCRUDTO<SubjectCRUDTO>> extraMarks) {
		this.extraMarks = extraMarks;
	}

}
