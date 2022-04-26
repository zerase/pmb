package com.pmb.demo.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.pmb.demo.repository.TransactionRepository;
import com.pmb.demo.repository.UserAccountRepository;
import com.pmb.demo.service.UserAccountService;

@SpringBootTest
@AutoConfigureMockMvc
class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserAccountRepository userAccountRepository;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		
		userAccountService.saveNewUserAccount("John", "Doe", "john@test.com", "testpwd1");
		userAccountService.saveNewUserAccount("Marie", "Curie", "marie@test.com", "testpwd2");
	}

	@AfterEach
	void tearDown() throws Exception {
		transactionRepository.deleteAll();
		userAccountRepository.deleteAll();
	}

	@DisplayName("Display Transfer view with anonymous user")
	@Test
	@WithAnonymousUser
	void testShowTransferView_shouldReturnRedirectionToLoginView_whenUserIsAnonymous() throws Exception {
		mockMvc.perform(get("/transfer"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@DisplayName("Display Transfer view with authenticated user")
	@Test
	@WithMockUser("john@test.com")
	void testShowTransferView_shouldReturnRedirectionToBuddyTransferView_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/transfer"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/transfers/buddy-transfer"));
	}

	@DisplayName("Display Buddy-transfer view with data content of authenticated user")
	@Test
	@WithMockUser("john@test.com")
	void testShowBuddyTransferView_shouldReturnUserData_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/transfers/buddy-transfer"))
			.andExpect(status().isOk())
			.andExpect(view().name("buddy-transfer"))
			.andExpect(model().attributeExists("username", "userbalance", "userbank"))
			.andExpect(model().attribute("username", "John Doe"));
	}
	
	@DisplayName("Display Bank-transfer view with data content of authenticated user")
	@Test
	@WithMockUser("john@test.com")
	void testShowBankTransferView_shouldReturnUserData_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/transfers/bank-transfer"))
			.andExpect(status().isOk())
			.andExpect(view().name("bank-transfer"))
			.andExpect(model().attributeExists("username", "userbalance", "userbank"))
			.andExpect(model().attribute("username", "John Doe"));
	}

	@DisplayName("Display Buddy-transfer view when user send money successfully to his friend")
	@Test
	@WithMockUser("john@test.com")
	void testShowBuddyTransferView_shouldDisplaySuccessMessage_whenUserSucceedToSendMoneyToHisConnection() throws Exception {
		mockMvc.perform(post("/transfers/buddy-transfer").param("connections", "marie@test.com")
														 .param("amount", "4.99")
														 .param("description", "lorem ipsum")).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/transfers/buddy-transfer?successTransfer"));
	}

	@DisplayName("Display Buddy-transfer view when user failed to send money to his friend")
	@Test
	@WithMockUser("john@test.com")
	void testShowBuddyTransferView_shouldDisplayErrorMessage_whenUserFailedToSendMoneyToHisConnection() throws Exception {
		mockMvc.perform(post("/transfers/buddy-transfer").param("connections", "")
														 .param("amount", "4.99")
														 .param("description", "lorem ipsum")).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/transfers/buddy-transfer?errorTransfer"));
	}
	
	@DisplayName("Display Bank-transfer view when user transfer money successfully on his account")
	@Test
	@WithMockUser("john@test.com")
	void testShowBankTransferView_shouldDisplaySuccessMessage_whenUserSucceedToTransferMoneyOnHisAccount() throws Exception {
		mockMvc.perform(post("/transfers/bank-transfer").param("amount", "2000.00")
														.param("operationType", "payment")).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/transfers/bank-transfer?successBankTransfer"));
	}

	@DisplayName("Display Bank-transfer view when user transfer money successfully from his account")
	@Test
	@WithMockUser("john@test.com")
	void testShowBankTransferView_shouldDisplaySuccessMessage_whenUserSucceedToTransferMoneyFromHisAccount() throws Exception {
		mockMvc.perform(post("/transfers/bank-transfer").param("amount", "1000.00")
														.param("operationType", "withdraw")).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/transfers/bank-transfer?successBankTransfer"));
	}

}
