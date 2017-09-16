package org.dragberry.era.business.registration.validation;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dragberry.era.business.CountryService;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocumentValidatorTest {
	
	private final static Set<String> COUNTRIES = new HashSet<>();
	static {
		COUNTRIES.add("BY");
	}
	
	@Mock
	private CountryService countryService;
	
	@InjectMocks
	private DocumentValidator validator = new DocumentValidator();
	
	private Registration registration;
	
	@Before
	public void setUp() {
		registration = new Registration();
		registration.setEnrollee(new Person());
		Document doc = new Document();
		doc.setCitizenhip("BY");
		doc.setId("12345678901234");
		doc.setDocumentId("MP2179093");
		doc.setIssueDate(LocalDate.now().minusYears(1));
		doc.setIssuedBy("РУВД");
		doc.setType(Document.Type.PASSPORT);
		registration.getEnrollee().setDocument(doc);
		Mockito.when(countryService.getCountryCodes()).thenReturn(COUNTRIES);
	}

	@Test
	public void testValidateSuccessful() {
		List<IssueTO> result = validator.validate(registration);
		assertEquals(result.toString(), 0, result.size());
	}

	@Test
	public void testValidateCitizenshipEmpty() {
		registration.getEnrollee().getDocument().setCitizenhip(null);
		List<IssueTO> result = validator.validate(registration); 
		assertEquals(1, result.size());
		assertEquals("validation.registration.enrollee.document.citizenship-is-empty", result.get(0).getErrorCode());
		assertEquals("dCitizenship", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateCitizenshipInvalid() {
		registration.getEnrollee().getDocument().setCitizenhip("AA");
		List<IssueTO> result = validator.validate(registration); 
		assertEquals(1, result.size());
		assertEquals("validation.registration.enrollee.document.citizenship-is-invalid", result.get(0).getErrorCode());
		assertEquals("dCitizenship", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
}
