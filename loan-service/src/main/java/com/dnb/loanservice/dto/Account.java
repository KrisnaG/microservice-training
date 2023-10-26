package com.dnb.loanservice.dto;

import com.dnb.loanservice.enums.AccountType;

import lombok.Data;

@Data
public class Account {

	private String accountId;

	private boolean accountStatus = true;

	private AccountType accountType;

	private String panNumber;

	private String aadharNumber;

	private String contact;

	private long balance;

	private Integer customerId;
}