package com.pmb.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmb.demo.constants.TransactionType;
import com.pmb.demo.model.Transaction;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private UserAccountServiceImpl userAccountService;
	@Autowired
	private TransactionRepository transactionRepository;
	
	private static final double charges = 0.5;
	
	// Should return a list of transactions realized by the user
	public Iterable<Transaction> getTransactionsListOfUser(UserAccount sender) {
		return transactionRepository.findTransactionsBySenderId(sender);
	}
	
	
	// Should return a list of transactions with specific type realized by the user
	public Iterable<Transaction> getTransactionsListOfUserWithTransactionType(UserAccount sender, TransactionType transactionType) {
		return transactionRepository.findAllBySenderIdAndType(sender, transactionType);
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


	// Should realize a balance transfer between an user and his bank
	public Transaction doBankTransfer(String email, String operationType, BigDecimal amount) {

		UserAccount senderUser = userAccountService.getUserByEmail(email);
		UserAccount receiverUser = userAccountService.getUserByEmail(email);
		
		// We persist the transaction
		Transaction bankTransfer = new Transaction();
		
		bankTransfer.setSenderId(senderUser);
		bankTransfer.setReceiverId(receiverUser);
		bankTransfer.setAmount(amount);
		
		if(operationType.equalsIgnoreCase("payment")) {
			bankTransfer.setCharges(0.00);
			bankTransfer.setDescription("Add money to my account");
			bankTransfer.setType(TransactionType.BANK_TO_USER);
			
			userAccountService.credit(receiverUser, amount);
			
		} else if(operationType.equalsIgnoreCase("withdraw")) {
			bankTransfer.setCharges(charges);
			bankTransfer.setDescription("Withdraw money from my account");
			bankTransfer.setType(TransactionType.USER_TO_BANK);
			
			// Calculate total amount to debit from sender balance account
			BigDecimal commission = amount.multiply(new BigDecimal("0.005"));
			BigDecimal amountWithCommission = commission.add(amount);
			userAccountService.debit(senderUser, amountWithCommission);
		}
		
		Transaction succeedBankTransfer = transactionRepository.save(bankTransfer);
		
		return succeedBankTransfer;
	}
}
