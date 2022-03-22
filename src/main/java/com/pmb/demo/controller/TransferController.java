package com.pmb.demo.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.model.Transaction;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.service.TransactionService;
import com.pmb.demo.service.UserAccountService;

@Controller
public class TransferController {

	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	TransactionService transactionService;
	
	// TODO: Ajouter Security
	// Retourne la page des transactions d'un user (ici John Doe)
	@GetMapping("/transfer")
	public String showTransferView(Model model) {

		UserAccount user = userAccountService.getUserByEmail("john@test.com");
		Iterable<Transaction> transactions = transactionService.getTransactionsListOfUser(user);

		model.addAttribute("u", user);
		model.addAttribute("usertransactions", transactions);
		return "transfer" ;

	}
	
	
	// TODO: Ajouter Security
	// TODO: Faire un transfert d'argent (payement) à un user (friend)
	@PostMapping("/transfer")
	public String doTransferToUser(Model model,
									@RequestParam String connections,
									@RequestParam BigDecimal amount,
									@RequestParam String description) {
		
		UserAccount user = userAccountService.getUserByEmail("john@test.com");
		
		try {
			transactionService.doTransfer(user.getEmail(), connections, amount, description);
			return "redirect:/transfer?successTransfer";
		} catch (Exception e) {
			System.out.println(e);
			return "redirect:/transfer?errorTransfer";
		}
		//return "transfer";
	}
}
