package org.dragberry.era.business.institution;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.dragberry.era.common.institution.EducationInstitutionBaseTO;
import org.dragberry.era.dao.EducationInstitutionBaseDao;
import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.domain.EducationInstitutionBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EducationInstitutionServiceBeanTest {

	@Mock
	private EducationInstitutionBaseDao eInstitutionBaseDao;
	@Mock
	private EducationInstitutionDao eInstitutionDao;
	
	@InjectMocks
	private EducationInstitutionServiceBean service = new EducationInstitutionServiceBean();
	
	@Test
	public void testLookup() {
		EducationInstitutionBase base = new EducationInstitutionBase();
		base.setCountry("BY");
		base.setName("Name");
		base.setEntityKey(1L);
		Mockito.when(eInstitutionBaseDao.findByNameAndCountry("name", "BY", 5)).thenReturn(Arrays.asList(base));
		List<EducationInstitutionBaseTO> list = service.lookup("name", "BY", 5);
		assertEquals(1, list.size());
		EducationInstitutionBaseTO baseTO = list.get(0);
		assertEquals(base.getEntityKey(), baseTO.getId());
		assertEquals(base.getName(), baseTO.getName());
	}

}
