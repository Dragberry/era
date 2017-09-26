package org.dragberry.era.business.certificate;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.certificate.SubjectTO;
import org.dragberry.era.dao.SubjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceBean implements CertificateService {

	@Autowired
	private SubjectDao subjectDao;
	
	@Override
	public List<SubjectTO> getSubjectList() {
		return subjectDao.fetchList().stream().map(s -> {
			SubjectTO to = new SubjectTO();
			to.setId(s.getEntityKey());
			to.setTitle(s.getTitle());
			to.setBase(s.isBase());
			return to;
		}).collect(Collectors.toList());
	}

}
