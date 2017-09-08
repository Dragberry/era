package org.dragberry.era.business.certificate;

import java.util.List;

import org.dragberry.era.common.certificate.SubjectTO;

public interface CertificateService {
	
	List<SubjectTO> getSubjectList();

}
