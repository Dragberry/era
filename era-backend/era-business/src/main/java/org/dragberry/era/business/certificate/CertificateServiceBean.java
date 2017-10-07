package org.dragberry.era.business.certificate;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.business.validation.ValidationService;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.certificate.SubjectCRUDTO;
import org.dragberry.era.dao.SubjectDao;
import org.dragberry.era.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceBean implements CertificateService {

	@Autowired
	private SubjectDao subjectDao;
	
	@Autowired
	private ValidationService<Subject> validationService;
	
	@Override
	public List<SubjectCRUDTO> getSubjectList() {
		return subjectDao.fetchList().stream().map(s -> {
			SubjectCRUDTO to = new SubjectCRUDTO();
			to.setId(s.getEntityKey());
			to.setTitle(s.getTitle());
			to.setBase(s.isBase());
			to.setOrder(s.getOrder());
			return to;
		}).collect(Collectors.toList());
	}

	@Override
	public ResultTO<SubjectCRUDTO> createSubject(SubjectCRUDTO subject) {
		Subject entity = new Subject();
		entity.setTitle(subject.getTitle());
		entity.setOrder(subjectDao.getLastOrder() + 1);
		entity.setBase(subject.isBase());
		entity.setCode(null);
		List<IssueTO> issues = validationService.validate(entity);
		if (issues.isEmpty()) {
			entity = subjectDao.create(entity);
			subject.setId(entity.getEntityKey());
			subject.setOrder(entity.getOrder());
		}
		return Results.create(subject, issues);
	}

}
