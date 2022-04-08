package com.pmb.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(HomeController.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

	//@InjectMocks
	//@Autowired
	//private HomeController homeController;
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	@DisplayName("Get /home request without authenticated user")
	@WithAnonymousUser
	void testHomeUrlWithoutAuthentication_shouldReturnRedirectionToLoginPage() throws Exception {
		mockMvc.perform(get("/home"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	@DisplayName("Get /home request with authenticated user")
	@WithMockUser("john@test.com")
	void testHomeUrlWithAuthentication_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/home"))
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Get /home request with data content of authenticated user")
	@WithMockUser("john@test.com")
	void testGetHomeView_shouldReturnViewWithData() throws Exception {
		mockMvc.perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"))
			.andExpect(model().attributeExists("username", "userbalance", "userbank"))
			.andExpect(model().attribute("username", "John Doe"));
	}
	
	@Test
	@DisplayName("Post /addBankAccount request without being authenticated")
	@WithAnonymousUser
	void testAddBankAccountWithoutAuthentication_shouldReturnRedirectionToLoginPage() throws Exception {
		mockMvc.perform(post("/addBankAccount"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@Test
	@DisplayName("Post /addBankAccount request with authenticated user")
	@WithMockUser("john@test.com")
	void testAddBankAccountWithAuthentication_shouldReturnRedirectionToHomePage() throws Exception {
		mockMvc.perform(post("/addBankAccount").param("iban", "FR 1111 2222 3333"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/home?successAddBank"));
	}

}
