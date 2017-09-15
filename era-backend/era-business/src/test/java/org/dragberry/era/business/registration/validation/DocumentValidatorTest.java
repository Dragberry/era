package org.dragberry.era.business.registration.validation;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.dragberry.era.application.config.BusinessConfigTest;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BusinessConfigTest.class})
public class DocumentValidatorTest {
	
	@Autowired
	private DocumentValidator validator;
	
	private Registration registration;
	
	@Before
	public void setUp() {
		registration = new Registration();
		registration.setEnrollee(new Person());
		Document doc = new Document();
		doc.setCitizenhip("BY");
		doc.setId("12345678901234");
		doc.setIssueDate(LocalDate.now().minusYears(1));
		doc.setIssuedBy("РУВД");
		doc.setType(Document.Type.PASSPORT);
		registration.getEnrollee().setDocument(doc);
	}

	@Test
	public void testValidateSuccessful() {
		registration.getEnrollee().getDocument().setCitizenhip(null);
		assertEquals(0, validator.validate(registration));
	}

}
