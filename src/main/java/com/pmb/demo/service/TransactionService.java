package com.pmb.demo.service;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmb.demo.constants.TransactionType;
import com.pmb.demo.model.Transaction;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.TransactionRepository;



@Service
public class TransactionService {

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private TransactionRepository transactionRepository;
	
	private static final double charges = 0.5;
	
	// Should return a list of transactions realized by the user
	public Iterable<Transaction> getTransactionsListOfUser(UserAccount sender) {
		return transactionRepository.findTransactionsBySenderId(sender);
	}
	
	
	//Should realize a balance transfer between a sender and a receiver
	public Transaction doTransfer(String senderEmail, 
								  String receiverEmail,
								  BigDecimal amount,
								  String description) {
		
		UserAccount senderUser = userAccountService.getUserByEmail(senderEmail);
		UserAccount receiverUser = userAccountService.getUserByEmail(receiverEmail);
		
		// Calculate total amount to debit from sender balance account
		BigDecimal commission = amount.multiply(new BigDecimal("0.005"));
		BigDecimal amountWithCommission = commission.add(amount);		
		
		// We debit sender and credit receiver
		userAccountService.debit(senderUser, amountWithCommission);
		userAccountService.credit(receiverUser, amount);
		
		// We persist the transaction
		Transaction transfer = new Transaction();
		
		transfer.setSenderId(senderUser);
		transfer.setReceiverId(receiverUser);
		transfer.setAmount(amount);
		transfer.setCharges(charges);
		transfer.setDescription(description);
		transfer.setType(TransactionType.USER_TO_USER);
		
		Transaction succeedTransfer = transactionRepository.save(transfer);
		
		return succeedTransfer;
	}
}
