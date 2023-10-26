package com.dnb.accountservice.mapper;

import org.springframework.stereotype.Component;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.payload.response.AccountResponse;

@Component
public class EntityToResponseMapper {

	public AccountResponse accountToAccountResponse(Account account) {

		AccountResponse accountResponse = new AccountResponse();
		accountResponse.setAadharNumber(account.getAadharNumber());
		accountResponse.setAccountId(account.getAccountId());
		accountResponse.setAccountStatus(account.isAccountStatus());
		accountResponse.setAccountType(account.getAccountType());
		accountResponse.setBalance(account.getBalance());
		accountResponse.setCustomerId(account.getCustomerId());
		accountResponse.setPanNumber(account.getPanNumber());
		accountResponse.setContact(account.getContact());

		return accountResponse;
	}
}
