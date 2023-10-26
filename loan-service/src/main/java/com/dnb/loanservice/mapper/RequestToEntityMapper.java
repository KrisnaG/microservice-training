package com.dnb.loanservice.mapper;

import org.springframework.stereotype.Component;

import com.dnb.loanservice.dto.Loan;
import com.dnb.loanservice.request.payload.LoanRequest;

@Component
public class RequestToEntityMapper {

	public Loan loanRequestToEntity(LoanRequest loanRequest) {

		Loan loan = new Loan();
		
		loan.setAccountId(loanRequest.getAccountId());
		loan.setAmount(loanRequest.getAmount());
		loan.setLoanType(loanRequest.getLoanType());
		return loan;
	}

}
