package com.dnb.loanservice.service;

import java.util.List;
import java.util.Optional;

import com.dnb.loanservice.dto.Loan;
import com.dnb.loanservice.exceptions.IdNotFoundException;

public interface LoanService {

	public Loan createLoan(Loan credit) throws IdNotFoundException;

	public Optional<Loan> getLoan(String loanId);

	public List<Loan> getLoans();

	public List<Loan> getLoans(boolean status);

	public boolean deleteLoan(String loanId) throws IdNotFoundException;

	public List<Loan> getLoansByAccountId(String accountId);

	public Loan approveLoan(String loadId) throws IdNotFoundException;
}
