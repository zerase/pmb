package com.pmb.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.model.BankAccount;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.service.BankAccountService;
import com.pmb.demo.service.UserAccountService;

@Controller
public class HomeController {

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private BankAccountService bankAccountService;
	

	// TODO: Ajouter Javadoc comment
	@GetMapping({ "/", "/home" })
	public String showHomeView(Model model) {
		
		// TODO: Remplacer par vrai authentification par email avec Spring Security
		// Simule un utilisateur authentifié par son email
		UserAccount user = userAccountService.getUserByEmail("john@test.com");
		
		Optional<BankAccount> bank = Optional.ofNullable(user.getBankAccount());

		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " +user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", bank);

		return "home";
	}
	
	
	// TODO: Ajouter Javadoc comment
	@PostMapping("/addBankAccount")
	public String addBankAccount(Model model, @RequestParam String iban) {
		
		// TODO: Remplacer par vrai authentification par email avec Spring Security
		// Simule un utilisateur authentifié par son email
		UserAccount user = userAccountService.getUserByEmail("john@test.com");

		try {
			bankAccountService.saveBank(user.getEmail(), iban);
			return "redirect:/home?successAddBank";
		} catch (Exception e) {
			return "redirect:/home?errorAddBank";
		}
	}
}
