package com.pmb.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmb.demo.model.Transaction;
import com.pmb.demo.model.UserAccount;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	 List<Transaction> findTransactionsBySenderId(UserAccount sender);
	
}
