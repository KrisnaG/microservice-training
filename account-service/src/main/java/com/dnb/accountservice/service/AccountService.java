package com.dnb.accountservice.service;

import java.util.List;
import java.util.Optional;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.exceptions.AccountCloseException;
import com.dnb.accountservice.exceptions.AccountMappingException;
import com.dnb.accountservice.exceptions.BalanceNotSufficientException;

public interface AccountService {

	// create
	public Account saveAccount(Account account) throws IdNotFoundException, AccountMappingException;

	// get
	public Optional<Account> getAccount(String accountId);
	
	// get by userId or customerId
	public Optional<Account> getAccountByCustomerId(Integer customerId);

	// get All
	public List<Account> getAccounts();

	// deposit
	public Account depositAmount(String accountId, long balance) throws IdNotFoundException, AccountCloseException;

	// withdraw
	public Account withdrawAmount(String accountId, long balance)
			throws IdNotFoundException, BalanceNotSufficientException, AccountCloseException;

	// close account
	public Account closeAccount(String accountId) throws IdNotFoundException, AccountCloseException;

	// delete
	public boolean deleteAccount(String accountId) throws IdNotFoundException;

}
