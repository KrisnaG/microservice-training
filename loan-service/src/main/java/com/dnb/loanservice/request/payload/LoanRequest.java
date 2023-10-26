package com.dnb.loanservice.request.payload;

import com.dnb.loanservice.enums.LoanType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanRequest {

	@NotNull(message = "loan type should not be null")
	@Enumerated(EnumType.STRING)
	private LoanType loanType;

	@Min(value = 10000, message = "Loan Amount cannot be less than 10000")
	private long amount;

	private String accountId;
}
