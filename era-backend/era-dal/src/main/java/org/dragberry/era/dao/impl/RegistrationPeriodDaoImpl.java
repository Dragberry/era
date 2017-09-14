package org.dragberry.era.dao.impl;

import java.util.List;

import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.domain.RegisteredSpecialty;
import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.domain.RegistrationPeriod.Status;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationPeriodDaoImpl extends AbstractDao<RegistrationPeriod> implements RegistrationPeriodDao {

	private static final String PERIOD_KEY = "periodKey";
	private static final String STATUS = "status";

	public RegistrationPeriodDaoImpl() {
		super(RegistrationPeriod.class);
	}

	@Override
	public List<RegisteredSpecialty> findSpecialtiesForPeriod(Long customerKey, Long periodKey) {
		return getEntityManager()
				.createNamedQuery(RegistrationPeriod.FIND_SPECIALTIES_FOR_PERIOD, RegisteredSpecialty.class)
				.setParameter(CUSTOMER_KEY, customerKey).setParameter(PERIOD_KEY, periodKey).getResultList();
	}

	@Override
	public RegistrationPeriod findActivePeriodForCustomer(Long customerKey) {
		List<RegistrationPeriod> result = getEntityManager()
				.createNamedQuery(RegistrationPeriod.FIND_ACTIVE_PERIOD_FOR_CUSTOMER, getEntityType())
				.setParameter(CUSTOMER_KEY, customerKey)
				.setParameter(STATUS, Status.OPENED)
				.getResultList();
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List<RegistrationPeriod> fetchList(Long customerKey) {
		return getEntityManager()
				.createNamedQuery(RegistrationPeriod.FIND_PERIODS_FOR_CUSTOMER, getEntityType())
				.setParameter(CUSTOMER_KEY, customerKey).getResultList();
	}

}
