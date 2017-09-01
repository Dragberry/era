package org.dragberry.era.business.speciality;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.speciality.SpecialitySimpleTO;
import org.dragberry.era.dao.SpecialityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialityServiceBean implements SpecialityService {

	@Autowired
	private SpecialityDao specialityDao;
	
	@Override
	public List<SpecialitySimpleTO> getListForRegistrations(Long customerId, long registrationPeriodId) {
		return specialityDao.fetchList().stream().map(entity -> {
			SpecialitySimpleTO to = new SpecialitySimpleTO();
			to.setId(entity.getEntityKey());
			to.setName(entity.getTitle());
			return to;
		}).collect(Collectors.toList());
	}

}
