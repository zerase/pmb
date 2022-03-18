package com.pmb.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "bank_account")
public class BankAccount {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bank_account_id")
	private Long bankAccountId;
	
	private String iban;

	
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
