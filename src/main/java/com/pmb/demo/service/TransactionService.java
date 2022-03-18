package com.pmb.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmb.demo.model.Transaction;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.TransactionRepository;



@Service
public class TransactionService {

	//@Autowired
	//private TransactionRepository transactionRepository;
	
	//@Autowired
	//private UserAccountService userAccountService;
	@Autowired
	private TransactionRepository transactionRepository;
	
	// Should return a list of transactions realized by the user
	public List<Transaction> getTransactionsListOfUser(UserAccount sender){
		return transactionRepository.findTransactionsBySenderId(sender);
	}
	
}
