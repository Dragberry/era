package org.dragberry.era.business.institution;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.institution.EducationInstitutionBaseTO;
import org.dragberry.era.common.institution.EducationInstitutionTO;
import org.dragberry.era.dao.EducationInstitutionBaseDao;
import org.dragberry.era.dao.EducationInstitutionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationInstitutionServiceBean implements EducationInstitutionService {

	@Autowired
	private EducationInstitutionDao eInstitutionDao; 
	@Autowired
	private EducationInstitutionBaseDao eInstitutionBaseDao;
	
	@Override
	public List<EducationInstitutionTO> getInstitutionListForRegistration(Long customerId) {
		return eInstitutionDao.fetchList().stream().map(ei -> {
			EducationInstitutionTO to = new EducationInstitutionTO();
			to.setId(ei.getEntityKey());
			to.setName(ei.getName());
			to.setShortName(ei.getShortName());
			return to;
		}).collect(Collectors.toList());
	}

	@Override
	public List<EducationInstitutionBaseTO> lookup(String name, String country, int maxSize) {
		return eInstitutionBaseDao.findByNameAndCountry(name, country, maxSize).stream().map(ei -> {
			EducationInstitutionBaseTO to = new EducationInstitutionBaseTO();
			to.setId(ei.getEntityKey());
			to.setName(ei.getName());
			to.setCountry(ei.getCountry());
			return to;
		}).collect(Collectors.toList());
	}
}
