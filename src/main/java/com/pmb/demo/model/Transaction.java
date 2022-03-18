package com.pmb.demo.model;


import java.math.BigDecimal;

import javax.persistence.*;

import com.pmb.demo.constants.TransactionType;


@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;
	
	private BigDecimal amount;
	
	private String description;
	
	private BigDecimal charges;
	
	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@ManyToOne
	@JoinColumn(name = "account_sender_id"/*, referencedColumnName = "user_account_id"*/)
	private UserAccount senderId;
	
	@ManyToOne
	@JoinColumn(name = "account_receiver_id"/*, referencedColumnName = "user_account_id"*/)
	private UserAccount receiverId;

	
	/* Getters and setters */
	
	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public UserAccount getSenderId() {
		return senderId;
	}

	public void setSenderId(UserAccount senderId) {
		this.senderId = senderId;
	}

	public UserAccount getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(UserAccount receiverId) {
		this.receiverId = receiverId;
	}
	
}
