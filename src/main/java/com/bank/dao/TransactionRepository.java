package com.bank.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Account;
import com.bank.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findByFromAccountOrToAccount(Account from, Account to);

}
