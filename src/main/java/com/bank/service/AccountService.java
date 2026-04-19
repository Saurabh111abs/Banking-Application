package com.bank.service;

import java.security.cert.TrustAnchor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.naming.TransactionRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import com.bank.BankingSystemApplication;
import com.bank.dao.AccountRepository;
import com.bank.dao.TransactionRepository;
import com.bank.dao.UserRepository;
import com.bank.dto.AccountRequest;
import com.bank.dto.DepositRequest;
import com.bank.dto.TransactionResponse;
import com.bank.dto.TransferRequest;
import com.bank.dto.WithdrawRequest;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;

@Service
public class AccountService {

	private final BankingSystemApplication bankingSystemApplication;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;


	AccountService(BankingSystemApplication bankingSystemApplication) {
		this.bankingSystemApplication = bankingSystemApplication;
	}


	public Account createAccount(AccountRequest accountRequest) {


		User user =    userRepository.findById(accountRequest.getUserId())
				.orElseThrow(() -> new RuntimeException("user not found"));

		Account account = new Account();
		account.setAccountNumber(generateAccountNumber());
		account.setAccountType(accountRequest.getAccountType());
		account.setBalance(0.0);
		account.setUser(user);

		return   accountRepository.save(account);


	}


	public  String generateAccountNumber() {

		return UUID.randomUUID().toString().substring(0, 12);
	}

	// DEPOSIT TO ACCOUNT

	public String deposit(DepositRequest depositRequest) {

		Account account =   accountRepository.findById(depositRequest.getAccountId())
				.orElseThrow(()-> new AccountNotFoundException("Account not found"));   

		account.setBalance(account.getBalance() + depositRequest.getBalance());
		accountRepository.save(account);

		Transaction transaction = new Transaction();
		transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
		transaction.setAmount(depositRequest.getBalance());
		transaction.setToAccount(account);
		transaction.setTimestamp(LocalDateTime.now());

		transactionRepository.save(transaction);


		return "Transaction Successfull";
	}

	// WITHDRAW TO ACCOUNT

	public String withdraw(WithdrawRequest withdrawRequest) {

		Account account =   accountRepository.findById(withdrawRequest.getAccountId())
				.orElseThrow(()-> new AccountNotFoundException("Account not found")); 
		
		if(account.getBalance() < withdrawRequest.getAmount()) {
			
			throw new InsufficientBalanceException("Insufficient Balance");
		}

		account.setBalance(account.getBalance() - withdrawRequest.getAmount());
		accountRepository.save(account);

		Transaction transaction = new Transaction();
		transaction.setTransactionType(Transaction.TransactionType.WITHDRAW);
		transaction.setAmount(withdrawRequest.getAmount());
		transaction.setToAccount(account);
		transaction.setTimestamp(LocalDateTime.now());

		transactionRepository.save(transaction);


		return "Transaction Successfull";
	}

	// TRANSFER BETWEEN ONE ACCOUNT TO OTHER ACCOUNT

	public String transfer(TransferRequest transferRequest) {

		System.out.println(transferRequest.getFromAccountId());
		System.out.println(transferRequest.getToAccountId());

		Account fromAccount =   accountRepository.findById(transferRequest.getFromAccountId())
				.orElseThrow(()-> new AccountNotFoundException(" From Account not found"));

		Account toAccount = accountRepository.findById(transferRequest.getToAccountId())
				.orElseThrow(()-> new AccountNotFoundException("To Account not found"));

		if(fromAccount.getBalance() < transferRequest.getAmount()) {

			throw new InsufficientBalanceException("Insufficient Balance");
		}

		fromAccount.setBalance(fromAccount.getBalance() - transferRequest.getAmount());
		toAccount.setBalance(toAccount.getBalance() + transferRequest.getAmount());

		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);

		Transaction transaction = new Transaction();
		transaction.setTransactionType(Transaction.TransactionType.TRANSFER);
		transaction.setFromAccount(fromAccount);
		transaction.setToAccount(toAccount);
		transaction.setAmount(transferRequest.getAmount());
		transaction.setTimestamp(LocalDateTime.now());

		transactionRepository.save(transaction);

		return "Transaction Successfull";		    		
	}
	
	// GET ALL TRANSACTION BY ACCOUNT ID

	public List<TransactionResponse> getTransactions(Long accountId) {

	    Account account = accountRepository.findById(accountId)
	            .orElseThrow(() -> new AccountNotFoundException("Account not found"));

	    List<Transaction> transactions = transactionRepository.findByFromAccountOrToAccount(account, account);

	    List<TransactionResponse> responses = new ArrayList<>();

	    for (Transaction transaction : transactions) {
	        TransactionResponse response = new TransactionResponse();
	        response.setId(transaction.getId());
	        response.setAmount(transaction.getAmount());
	        response.setTransactionType(transaction.getTransactionType().name());
	        response.setTimestamp(transaction.getTimestamp());

	        if (transaction.getFromAccount() != null) {
	            response.setFromAccountNumber(transaction.getFromAccount().getAccountNumber());
	        }
	        if (transaction.getToAccount() != null) {
	            response.setToAccountNumber(transaction.getToAccount().getAccountNumber());
	        }

	        responses.add(response);
	    }

	    return responses;
	}



}
