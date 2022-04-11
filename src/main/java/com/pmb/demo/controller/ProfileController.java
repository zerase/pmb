package com.pmb.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.model.UserAccount;
import com.pmb.demo.service.UserAccountService;

@Controller
public class ProfileController {
	
	@Autowired
	private UserAccountService userAccountService;
	
	
	// TODO: Ajouter Javadoc comment
	@GetMapping("/profile")
	public String showProfileView(Model model) {
		
		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		
		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		
		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("mdp", user.getPassword());
		
		return "profile";
	}


	// TODO: Ajouter Javadoc comment
	@PostMapping("/profile")
	public String postProfile(Model model,
								@RequestParam String firstName,
								@RequestParam String lastName,
								@RequestParam String password) {
		
		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		
		try {
			userAccountService.editUser(firstName, lastName, password, user);
			return "redirect:profile?successUpdateAccount";
		} catch (Exception e) {
			return "redirect:profile?errorUpdateAccount";
		}
	}
}
