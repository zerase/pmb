package com.pmb.demo.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.constants.TransactionType;
import com.pmb.demo.model.BankAccount;
import com.pmb.demo.model.Transaction;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.service.TransactionService;
import com.pmb.demo.service.UserAccountServiceImpl;

@Controller
public class TransferController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

	@Autowired
	UserAccountServiceImpl userAccountService;
	
	@Autowired
	TransactionService transactionService;
	
	
	@GetMapping("/transfer")
	public String showTransferView() {
		return "redirect:/transfers/buddy-transfer";
	}


    @GetMapping("/transfers/buddy-transfer")
    public String showTransfersWithBuddiesView(Model model) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentAuthenticatedUser().orElseThrow();
		Optional<BankAccount> userBank = Optional.ofNullable(user.getBankAccount());
		
		Iterable<Transaction> userToUserTransactions = transactionService.getTransactionsListOfUserWithTransactionType(user, TransactionType.USER_TO_USER);

		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", userBank);
		
		model.addAttribute("u", user);
		model.addAttribute("usertransactions", userToUserTransactions);
		
		logger.info("Load buddy-transfer view with request GET /transfers/buddy-transfer");
		return "buddy-transfer" ;
	}


	@PostMapping("/transfers/buddy-transfer")
    public String doTransferToUser(@RequestParam String connections,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam String description,
                                   Model model) {
		logger.info("Load buddy-transfer view with request POST /transfers/buddy-transfer");

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentAuthenticatedUser().orElseThrow();
		
		try {
			transactionService.doTransfer(user.getEmail(), connections, amount, description);
			logger.info("The user send money to his friend successfully");
			return "redirect:/transfers/buddy-transfer?successTransfer";
		} catch (Exception e) {
			logger.error("An error occurred : " + e.getMessage());
			return "redirect:/transfers/buddy-transfer?errorTransfer";
		}
	}


    @GetMapping("/transfers/bank-transfer")
    public String showTransfersWithBankView(Model model) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentAuthenticatedUser().orElseThrow();
		Optional<BankAccount> userBank = Optional.ofNullable(user.getBankAccount());

		Iterable<Transaction> userToBankTransactions = transactionService.getTransactionsListOfUserWithTransactionType(user, TransactionType.USER_TO_BANK);
		Iterable<Transaction> bankToUserTransactions = transactionService.getTransactionsListOfUserWithTransactionType(user, TransactionType.BANK_TO_USER);

		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", userBank);

		model.addAttribute("u", user);
		model.addAttribute("usertobanktransactions", userToBankTransactions);
		model.addAttribute("banktousertransactions", bankToUserTransactions);

		logger.info("Load bank-transfer view with request GET /transfers/bank-transfer");
		return "bank-transfer" ;
	}


    @PostMapping("/transfers/bank-transfer")
    public String doTransferBank(@RequestParam BigDecimal amount,
                                 @RequestParam String operationType,
                                 Model model) {
    	logger.info("Load bank-transfer view with request POST /transfers/bank-transfer");

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentAuthenticatedUser().orElseThrow();
		
		try {
			transactionService.doBankTransfer(user.getEmail(), operationType, amount);
			logger.info("The user transfer money successfully");
			return "redirect:/transfers/bank-transfer?successBankTransfer";
		} catch (Exception e) {
			logger.error("An error occurred : " + e.getMessage());
			return "redirect:/transfers/bank-transfer?errorBankTransfer";
		}
	}
}
