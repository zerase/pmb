package com.pmb.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.exception.MyCustomBusinessException;
import com.pmb.demo.service.UserAccountService;

@Controller
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	UserAccountService userAccountService;
	
	
	@GetMapping("/register")
    public String showRegisterView(@RequestParam(required = false) String error,
                                   @RequestParam(required = false) String message,
                                   Model model) {
		
		model.addAttribute("error", error);
		model.addAttribute("message", message);

		logger.info("Load register view with request GET /register");
		return "register";
	}


    @PostMapping("/register")
    public String submitRegisterView(@RequestParam String firstNameForm,
                                     @RequestParam String lastNameForm,
                                     @RequestParam String emailForm,
                                     @RequestParam String passwordForm,
                                     Model model) {

		String error = null;
		String message = null;

		try {
			userAccountService.saveNewUserAccount(firstNameForm, lastNameForm, emailForm, passwordForm);

			message = "msg-register-success";

			model.addAttribute("message", message);

			logger.info("Registered {} {} successfully with {}", firstNameForm, lastNameForm, emailForm);

		} catch (MyCustomBusinessException e) {
			error = "Failed to register : " + e.getMessage();

			model.addAttribute("error", error);
			model.addAttribute("firstName", firstNameForm);
			model.addAttribute("lastName", lastNameForm);
			model.addAttribute("email", emailForm);
			model.addAttribute("password", passwordForm);

			logger.error(error);
		}
		logger.info("Load register view with request POST /register");
		return "register";
	}
}
