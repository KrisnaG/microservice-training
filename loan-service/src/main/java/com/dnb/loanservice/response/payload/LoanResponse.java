package com.dnb.loanservice.response.payload;

import com.dnb.loanservice.enums.LoanType;

import lombok.Data;

@Data
public class LoanResponse {
	
	private String loanId;

	private LoanType loanType;
	
	private long amount;
	
	private boolean status;
	
	private String accountId;
}
