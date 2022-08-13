package com.pmb.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bank_account_id")
	private Long bankAccountId;
	
	private String iban;

	
	/* Constructors */
	
	public BankAccount() {
		
	}
	
	public BankAccount(Long bankAccountId, String iban) {
		this.bankAccountId = bankAccountId;
		this.iban = iban;
	}

	public BankAccount(String iban) {
		this.iban = iban;
	}

	
	/* Getters and setters */
	
	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	
}
