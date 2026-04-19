package com.bank.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name = "from_account_id")
	private Account fromAccount;
	
	@ManyToOne
	@JoinColumn(name = "to_account_id")
	private Account toAccount;
	
	public enum TransactionType {
     
		DEPOSIT,WITHDRAW,TRANSFER
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public Transaction(long id, Double amount, TransactionType transactionType, LocalDateTime timestamp,
			Account fromAccount, Account toAccount) {
		super();
		this.id = id;
		this.amount = amount;
		this.transactionType = transactionType;
		this.timestamp = timestamp;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", transactionType=" + transactionType + ", timestamp="
				+ timestamp + ", fromAccount=" + fromAccount + ", toAccount=" + toAccount + "]";
	}
	
	
	
	
	
	
}
