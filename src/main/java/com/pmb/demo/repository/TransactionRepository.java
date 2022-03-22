package com.pmb.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmb.demo.model.Transaction;
import com.pmb.demo.model.UserAccount;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	Iterable<Transaction> findTransactionsBySenderId(UserAccount sender);
	
}
