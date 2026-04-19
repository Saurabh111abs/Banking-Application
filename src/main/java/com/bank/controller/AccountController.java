package com.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dto.AccountRequest;
import com.bank.dto.DepositRequest;
import com.bank.dto.TransactionResponse;
import com.bank.dto.TransferRequest;
import com.bank.dto.WithdrawRequest;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
		return ResponseEntity.ok(accountService.createAccount(request));
	}


	@PostMapping("/deposit")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<String> deposit(@RequestBody DepositRequest depositRequest) {

		System.out.println("Account ID: " + depositRequest.getAccountId());
		System.out.println("Balance: " + depositRequest.getBalance());

		System.out.println(depositRequest.getBalance());

		String response  =  accountService.deposit(depositRequest);

		return ResponseEntity.ok(response);
	}


	@PostMapping("/withdraw")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<String> withdraw(@RequestBody WithdrawRequest withdrawRequest) {

		System.out.println("Account ID: " + withdrawRequest.getAccountId());
		System.out.println("Balance: " + withdrawRequest.getAmount());

		System.out.println(withdrawRequest.getAmount());

		String response  =  accountService.withdraw(withdrawRequest);

		return ResponseEntity.ok(response);
	}


	@PostMapping("/transfer")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
		String result = accountService.transfer(request);
		return ResponseEntity.ok(result);
	}


	@GetMapping("/transactions/{accountId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long accountId) {
		
		List<TransactionResponse> transactions = accountService.getTransactions(accountId);
		
		return ResponseEntity.ok(transactions);
	}
}
