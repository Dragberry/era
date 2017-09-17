package org.dragberry.era.web.controller;

import org.dragberry.era.application.config.SecurityConfig;
import org.dragberry.era.application.config.TestContext;
import org.dragberry.era.application.config.WebConfig;
import org.dragberry.era.business.benefit.BenefitService;
import org.dragberry.era.common.benefit.BenefitListTO;
import org.dragberry.era.common.benefit.BenefitTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebConfig.class, SecurityConfig.class })
@WebAppConfiguration
public class RegistrationControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private BenefitService benefitService;
	@Autowired
	private RegistrationController registrationController;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
	}

	@Test
	public void getBenefitList() throws Exception {
		BenefitListTO list = new BenefitListTO();
		BenefitTO p = new BenefitTO();
		p.setId(1000L);
		p.setName("Prerogative");
		list.getPrerogatives().add(p);
		BenefitTO o = new BenefitTO();
		o.setId(1001L);
		o.setName("OutOfCompetition");
		list.getOutOfCompetitions().add(o);
		Mockito.when(benefitService.getActiveBenefits()).thenReturn(list);

		mockMvc.perform(get("/api/registrations/get-benefits"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.issues", hasSize(0)))
			.andExpect(jsonPath("$.value", notNullValue()))
			.andExpect(jsonPath("$.value.prerogatives", hasSize(1)))
			.andExpect(jsonPath("$.value.outOfCompetitions", hasSize(1)))
			.andExpect(jsonPath("$.value.prerogatives[0].id", is(1000))) 
			.andExpect(jsonPath("$.value.prerogatives[0].name", is("Prerogative")))
			.andExpect(jsonPath("$.value.outOfCompetitions[0].id", is(1001)))
			.andExpect(jsonPath("$.value.outOfCompetitions[0].name", is("OutOfCompetition")));

		verify(benefitService, times(1)).getActiveBenefits();
		verifyNoMoreInteractions(benefitService);
	}

}
