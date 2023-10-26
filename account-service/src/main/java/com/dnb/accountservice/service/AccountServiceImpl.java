package com.dnb.accountservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.dto.User;
import com.dnb.accountservice.exceptions.AccountCloseException;
import com.dnb.accountservice.exceptions.AccountMappingException;
import com.dnb.accountservice.exceptions.BalanceNotSufficientException;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.repo.AccountRespository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRespository accountRespository;

	@Autowired
	private RestTemplate restTemplate;

	@org.springframework.beans.factory.annotation.Value("${api.auth}")
	private String authUrl;

	@Override
	public Account saveAccount(Account account) throws AccountMappingException, IdNotFoundException {

		try {
			ResponseEntity<User> user = restTemplate.getForEntity(authUrl + "/" + account.getCustomerId().toString(),
					User.class);
			System.out.println(user);

			Optional<Account> optional = accountRespository.findByCustomerId(account.getCustomerId());

			if (optional.isPresent())
				throw new AccountMappingException(
						"Provided User Already has Account. One user can have only one Account!!");

			return accountRespository.save(account);
		} catch (ResourceAccessException e) {
			// catch connection errors
			throw new ResourceAccessException("Problem connecting to server");
		} catch (HttpClientErrorException e) {
			// catch exceptions when http response code is 4xx or 5xx.
			throw new IdNotFoundException("UserId not Found");
		} catch (AccountMappingException e) {
			throw new AccountMappingException(
					"Provided User Already has Account. One user can have only one Account!!");
		}

	}

	@Override
	public Optional<Account> getAccount(String accountId) {
		return accountRespository.findById(accountId);
	}

	@Override
	public Optional<Account> getAccountByCustomerId(Integer customerId) {

		return accountRespository.findByCustomerId(customerId);
	}

	@Override
	public List<Account> getAccounts() {

		return (List<Account>) accountRespository.findAll();
	}

	@Override
	public Account depositAmount(String accountId, long balance) throws IdNotFoundException, AccountCloseException {

		Optional<Account> account = this.getAccount(accountId);

		if (account.isEmpty())
			throw new IdNotFoundException("Account Id Not found");

		Account retreivedAccount = account.get();

		if (retreivedAccount.isAccountStatus() == false)
			throw new AccountCloseException("Account is Closed or suspended");

		long accountBalance = retreivedAccount.getBalance();
		retreivedAccount.setBalance(accountBalance + balance);
		return accountRespository.save(retreivedAccount);

	}

	@Override
	public Account withdrawAmount(String accountId, long balance)
			throws IdNotFoundException, BalanceNotSufficientException, AccountCloseException {

		Optional<Account> account = this.getAccount(accountId);

		if (account.isEmpty())
			throw new IdNotFoundException("Account Id Not found");

		Account retreivedAccount = account.get();

		if (retreivedAccount.isAccountStatus() == false)
			throw new AccountCloseException("Account is Closed or suspended");

		if (retreivedAccount.getBalance() - balance < 10000)
			throw new BalanceNotSufficientException("minimum balance if 10000 has to be maintained");

		long accountBalance = retreivedAccount.getBalance();
		retreivedAccount.setBalance(accountBalance - balance);
		return accountRespository.save(retreivedAccount);
	}

	@Override
	public Account closeAccount(String accountId) throws IdNotFoundException, AccountCloseException {

		Optional<Account> account = this.getAccount(accountId);

		if (account.isEmpty())
			throw new IdNotFoundException("Account Id Not found");

		Account retreivedAccount = account.get();

		if (retreivedAccount.isAccountStatus() == false)
			throw new AccountCloseException("Provided Account is Already Closed!!");

		retreivedAccount.setAccountStatus(false);

		return accountRespository.save(retreivedAccount);

	}

	@Override
	public boolean deleteAccount(String accountId) throws IdNotFoundException {

		boolean isExists = accountRespository.existsById(accountId);
		if (!isExists)
			throw new IdNotFoundException("Account Id not found!");

		accountRespository.deleteById(accountId);
		if (accountRespository.existsById(accountId))
			return false;
		else
			return true;
	}

}
