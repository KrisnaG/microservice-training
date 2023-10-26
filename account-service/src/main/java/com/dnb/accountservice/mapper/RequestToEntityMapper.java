package com.dnb.accountservice.mapper;

import org.springframework.stereotype.Component;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.payload.request.AccountRequest;

@Component
public class RequestToEntityMapper {

	public Account accountRequestToEntity(AccountRequest accountRequest) {

		Account account = new Account();
		account.setAadharNumber(accountRequest.getAadharNumber());
		account.setAccountType(accountRequest.getAccountType());
		account.setBalance(accountRequest.getBalance());
		account.setPanNumber(accountRequest.getPanNumber());
		account.setCustomerId(accountRequest.getCustomerId());
		account.setContact(accountRequest.getContact());

		return account;
	}

}
