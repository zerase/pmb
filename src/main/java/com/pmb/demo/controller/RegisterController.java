package com.pmb.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.service.UserAccountService;

@Controller
public class RegisterController {

	@Autowired
	UserAccountService userAccountService;
	
	@GetMapping("/register")
	public String showRegisterView() {
		return "register";
	}
	
	// TODO: Ajouter Javadoc comment
	@PostMapping("/register")
	public String postRegister(@RequestParam String firstName,
								@RequestParam String lastName,
								@RequestParam String email,
								@RequestParam String password) {
		
		String errorMail = null;
		boolean emailAlreadyExists = userAccountService.existsUserByEmail(email);
		
		if(emailAlreadyExists) {
			errorMail = "The email already exists";
		}
		
		if(errorMail == null) {
			userAccountService.addNewUser(firstName, lastName, email, password);
			return "redirect:/register?successRegister";
		} else {
			return "redirect:/register?errorRegister";
		}
	}
}
