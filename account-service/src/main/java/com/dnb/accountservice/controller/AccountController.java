package com.dnb.accountservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.dto.AmountRequest;
import com.dnb.accountservice.exceptions.AccountCloseException;
import com.dnb.accountservice.exceptions.AccountMappingException;
import com.dnb.accountservice.exceptions.BalanceNotSufficientException;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.mapper.EntityToResponseMapper;
import com.dnb.accountservice.mapper.RequestToEntityMapper;
import com.dnb.accountservice.payload.request.AccountRequest;
import com.dnb.accountservice.payload.response.AccountResponse;
import com.dnb.accountservice.service.AccountService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private RequestToEntityMapper requestToEntityMapper;

	@Autowired
	private EntityToResponseMapper entityToResponseMapper;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest accountRequest)
			throws IdNotFoundException, AccountMappingException {

		Account account;
		account = requestToEntityMapper.accountRequestToEntity(accountRequest);
		System.out.println(account);

		AccountResponse createdProfile = entityToResponseMapper
				.accountToAccountResponse(accountService.saveAccount(account));
		return new ResponseEntity<AccountResponse>(createdProfile, HttpStatus.CREATED);

	}

	@GetMapping("/{accountId}")
	public ResponseEntity<?> getAccount(@PathVariable("accountId") String accountId) throws IdNotFoundException {

		Optional<Account> account = accountService.getAccount(accountId);

		if (account.isEmpty())
			throw new IdNotFoundException("ProfileId Not Found!!");

		AccountResponse accountResponse = entityToResponseMapper.accountToAccountResponse(account.get());

		return ResponseEntity.ok(accountResponse);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAccountByUserId(@PathVariable("userId") Integer userId) throws IdNotFoundException {

		Optional<Account> account = accountService.getAccountByCustomerId(userId);

		if (account.isEmpty())
			throw new IdNotFoundException("User does not have an account!!");

		AccountResponse accountResponse = entityToResponseMapper.accountToAccountResponse(account.get());

		return ResponseEntity.ok(accountResponse);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAccounts() {

		List<Account> accounts = accountService.getAccounts();

		List<AccountResponse> accountResponses;
		accountResponses = accounts.stream().map(account -> entityToResponseMapper.accountToAccountResponse(account))
				.collect(Collectors.toList());

		return ResponseEntity.ok(accountResponses);
	}

	@PostMapping("/deposit/{accountId}")
	public ResponseEntity<?> deposit(@PathVariable("accountId") String accountId,
			@RequestBody AmountRequest amountRequest) throws IdNotFoundException, AccountCloseException {

		Account account = accountService.depositAmount(accountId, amountRequest.getAmount());
		return ResponseEntity.ok(account);
	}

	@PostMapping("/withdraw/{accountId}")
	public ResponseEntity<?> withdraw(@PathVariable("accountId") String accountId,
			@RequestBody AmountRequest amountRequest)
			throws IdNotFoundException, BalanceNotSufficientException, AccountCloseException {

		Account account = accountService.withdrawAmount(accountId, amountRequest.getAmount());
		return ResponseEntity.ok(account);
	}

	@PostMapping("/close/{accountId}")
	public ResponseEntity<?> closeAccount(@PathVariable("accountId") String accountId)
			throws IdNotFoundException, AccountCloseException {

		Account account = accountService.closeAccount(accountId);
		return ResponseEntity.ok(account);
	}

	@DeleteMapping("/{accountId}")
	public ResponseEntity<?> deleteAcountById(@PathVariable("accountId") String accountId) throws IdNotFoundException {

		accountService.deleteAccount(accountId);
		return ResponseEntity.noContent().build();
	}

}
