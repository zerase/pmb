package com.pmb.demo.service;

import java.math.BigDecimal;

import com.pmb.demo.constants.TransactionType;
import com.pmb.demo.model.Transaction;
import com.pmb.demo.model.UserAccount;


public interface TransactionService {

	// Should return a list of transactions realized by the user
	public Iterable<Transaction> getTransactionsListOfUser(UserAccount sender);
	
	
	// Should return a list of transactions with specific type realized by the user
	public Iterable<Transaction> getTransactionsListOfUserWithTransactionType(UserAccount sender, TransactionType transactionType);
	
	
	//Should realize a balance transfer between a sender and a receiver
	Transaction doTransfer(String senderEmail, String receiverEmail, BigDecimal amount, String description);


	// Should realize a balance transfer between an user and his bank
	public Transaction doBankTransfer(String email, String operationType, BigDecimal amount);
}
