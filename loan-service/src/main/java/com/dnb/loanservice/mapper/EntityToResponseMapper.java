package com.dnb.loanservice.mapper;

import org.springframework.stereotype.Component;

import com.dnb.loanservice.dto.Loan;
import com.dnb.loanservice.response.payload.LoanResponse;

@Component
public class EntityToResponseMapper {

	public LoanResponse loanToAccountResponse(Loan loan) {

		LoanResponse loanResponse = new LoanResponse();
		
		loanResponse.setAccountId(loan.getAccountId());
		loanResponse.setAmount(loan.getAmount());
		loanResponse.setLoanId(loan.getLoanId());
		loanResponse.setStatus(loan.isStatus());
		loanResponse.setLoanType(loan.getLoanType());
		
		return loanResponse;
	}
}
