package org.dragberry.era.business.benefit;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.dragberry.era.common.benefit.BenefitListTO;
import org.dragberry.era.dao.OutOfCompetitionDao;
import org.dragberry.era.dao.PrerogativeDao;
import org.dragberry.era.domain.OutOfCompetition;
import org.dragberry.era.domain.Prerogative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BenefitServiceBeanTest {

	private static final long _1000L = 1000L;
	private static final long _1001L = 1001L;
	private static final String OUT_OF_COMPETITION = "OutOfCompetition";
	private static final String PREROGATIVE = "Prerogative";

	@Mock
	private PrerogativeDao prerogativeDao;
	@Mock
	private OutOfCompetitionDao outOfCompetitionDao;
	
	@InjectMocks
	private BenefitServiceBean benefitServiceBean = new BenefitServiceBean();
	
	@Test
	public void testFetchActiveBenefits() {
		Prerogative p = new Prerogative();
		p.setEntityKey(_1000L);
		p.setName(PREROGATIVE);
		Mockito.when(prerogativeDao.fetchActiveBenefits()).thenReturn(Arrays.asList(p));
		OutOfCompetition o = new OutOfCompetition();
		o.setEntityKey(_1001L);
		o.setName(OUT_OF_COMPETITION);
		Mockito.when(outOfCompetitionDao.fetchActiveBenefits()).thenReturn(Arrays.asList(o));
			
		BenefitListTO listTO = benefitServiceBean.getActiveBenefits();
		assertNotNull(listTO);
		assertEquals(_1000L, listTO.getPrerogatives().get(0).getId().longValue());
		assertEquals(PREROGATIVE, listTO.getPrerogatives().get(0).getName());
		assertEquals(_1001L, listTO.getOutOfCompetitions().get(0).getId().longValue());
		assertEquals(OUT_OF_COMPETITION, listTO.getOutOfCompetitions().get(0).getName());
	}

}
