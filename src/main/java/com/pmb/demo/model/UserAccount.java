package com.pmb.demo.model;

import java.math.BigDecimal;

import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "user_account", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_account_id")
	private Long userId;
	
	private String email;
	
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private BigDecimal balance;
	
	@OneToOne
	@JoinColumn(name = "bank_account_id")
	private BankAccount bankAccount;
	
	@ManyToMany
	@JoinTable(
			name = "connection",
		   	joinColumns = @JoinColumn(
		   			name = "user_account_id", referencedColumnName = "user_account_id"),
		   	inverseJoinColumns = @JoinColumn(
		   			name = "user_friend_id", referencedColumnName = "user_account_id"))
	private Set<UserAccount> friends;

	
	/* Getters and setters */
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Set<UserAccount> getFriends() {
		return friends;
	}

	public void setFriends(Set<UserAccount> friends) {
		this.friends = friends;
	}
	
}
