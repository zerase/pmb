package com.pmb.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pmb.demo.model.UserAccount;
import com.pmb.demo.service.UserAccountService;

@Controller
public class HomeController {

	@Autowired
	private UserAccountService userAccountService;
	

	// TODO: OK :-) Reste à ajouter Javadoc comment
	@GetMapping({ "/", "/home" })
	public String showHomeView(Model model) {
		
		// TODO: Remplacer par vrai authentification par email avec Spring Security
		// Simule un utilisateur authentifié par son email
		UserAccount user = userAccountService.getUserByEmail("john@test.com");

		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " +user.getLastName());
		model.addAttribute("userbalance", user.getBalance());

		return "home";
	}
}
