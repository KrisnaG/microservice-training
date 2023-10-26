package com.dnb.loanservice.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dnb.loanservice.dto.Loan;

@Repository
public interface LoanRepository extends CrudRepository<Loan, String> {

	// select * from loan where loan.accountId = ?
	public List<Loan> getByAccountId(String accountId);
	
	//select * from loan where loan.status = ?
	public List<Loan> findByStatus(boolean status);
}
