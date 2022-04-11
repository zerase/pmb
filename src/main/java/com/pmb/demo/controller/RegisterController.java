package com.pmb.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.service.UserAccountService;

@Controller
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	UserAccountService userAccountService;
	
	
	@GetMapping("/register")
    public String showRegisterView(Model model, 
                                   @RequestParam(required = false) String error,
                                   @RequestParam(required = false) String message) {

		model.addAttribute("error", error);
		model.addAttribute("message", message);

		logger.info("The user is invited to register");
		return "register";
	}
	
	// TODO: Ajouter Javadoc comment
	@PostMapping("/register")
	public String postRegister(Model model,
							   @RequestParam String firstNameForm,
							   @RequestParam String lastNameForm,
							   @RequestParam String emailForm,
							   @RequestParam String passwordForm) {
		
		String error = null;
		String message = null;
		
		try {
			userAccountService.addNewUser(firstNameForm, lastNameForm, emailForm, passwordForm);
			
			message = "msg-register-success";
			
			model.addAttribute("message", message);
			
			logger.info("The user is registered and is invited to back to login");
			
		} catch (Exception e) {
			error = "Failed to register : " + e.getMessage();
			
			model.addAttribute("error", error);
			model.addAttribute("firstName", firstNameForm);
			model.addAttribute("lastName", lastNameForm);
			model.addAttribute("email", emailForm);
			model.addAttribute("password", passwordForm);

			logger.error("An error occur : " + e.getMessage());
		}
		return "register";

	}
}
