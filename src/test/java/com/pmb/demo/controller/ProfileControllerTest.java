package com.pmb.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pmb.demo.repository.BankAccountRepository;
import com.pmb.demo.repository.UserAccountRepository;
import com.pmb.demo.service.UserAccountService;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private BankAccountRepository bankAccountRepository;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		
		userAccountService.saveNewUserAccount("John", "Doe", "john@test.com", "testpwd");
	}

	@AfterEach
	void tearDown() throws Exception {
		userAccountRepository.deleteAll();
		bankAccountRepository.deleteAll();
	}

	@DisplayName("Display Profile view with anonymous user")
	@Test
	@WithAnonymousUser
	void testShowProfileView_shouldReturnRedirectionToLoginView_whenUserIsAnonymous() throws Exception {
		mockMvc.perform(get("/profile"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}

	@DisplayName("Display Profile view with authenticated user")
	@Test
	@WithMockUser("john@test.com")
	void testShowProfileView_shouldReturnRedirectionToPersonalInfoView_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/profile"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/profile/personal-info"));
	}

	@DisplayName("Display Personal-info view with data content of authenticated user")
	@Test
	@WithMockUser("john@test.com")
	void testShowPersonalInfoView_shouldReturnUserData_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/profile/personal-info"))
			.andExpect(status().isOk())
			.andExpect(view().name("personal-info"))
			.andExpect(model().attributeExists("username", "userbalance", "userbank"))
			.andExpect(model().attribute("username", "John Doe"));
	}

	@DisplayName("Display Bank-info view with data content of authenticated user")
	@Test
	@WithMockUser("john@test.com")
	void testShowBankInfoView_shouldReturnUserData_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/profile/bank-info"))
			.andExpect(status().isOk())
			.andExpect(view().name("bank-info"))
			.andExpect(model().attributeExists("username", "userbalance", "userbank"))
			.andExpect(model().attribute("username", "John Doe"));
	}

	@DisplayName("Display Personal-info view when user update his profile successfully")
	@Test
	@WithMockUser("john@test.com")
	void testShowPersonalInfoView_shouldDisplaySuccessMessage_whenUserSucceedToUpdateHisProfile() throws Exception {
		mockMvc.perform(post("/profile/personal-info").param("firstName", "John2")
													  .param("lastName", "Doe2")
													  .param("password", "testpwd2")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Profile updated successfully")));
	}

	@DisplayName("Display Bank-info view when user succeed to save his bank info")
	@Test
	@WithMockUser("john@test.com")
	void testShowBankInfoView_shouldDisplaySuccessMessage_whenUserSucceedToSaveHisBankInfo() throws Exception {
		mockMvc.perform(post("/profile/bank-info").param("iban", "123iban")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Add bank successfully")));
	}

}
