package com.dnb.accountservice.payload.response;

import com.dnb.accountservice.enums.AccountType;

import lombok.Data;

@Data
public class AccountResponse {

	private String accountId;

	private boolean accountStatus;

	private AccountType accountType;

	private String panNumber;

	private String aadharNumber;

	private String contact;

	private long balance;

	private Integer customerId;
}
