package com.pmb.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
		List<Transaction> transactions = transactionService.getTransactionsListOfUser(user);
		
		model.addAttribute("usertransactions", transactions);
		return "transfer" ;

	}
}
