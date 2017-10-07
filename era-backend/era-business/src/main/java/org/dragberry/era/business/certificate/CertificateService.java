package org.dragberry.era.business.certificate;

import java.util.List;

import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.certificate.SubjectCRUDTO;

public interface CertificateService {
	
	List<SubjectCRUDTO> getSubjectList();

	ResultTO<SubjectCRUDTO> createSubject(SubjectCRUDTO subject);

}
