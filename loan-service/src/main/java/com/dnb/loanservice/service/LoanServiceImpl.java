package com.dnb.loanservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.dnb.loanservice.dto.Account;
import com.dnb.loanservice.dto.Loan;
import com.dnb.loanservice.exceptions.IdNotFoundException;
import com.dnb.loanservice.repo.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${api.account}")
	private String accountUrl;

	@Override
	public Loan createLoan(Loan loan) throws IdNotFoundException {

		try {
			restTemplate.getForEntity(accountUrl + "/" + loan.getAccountId(), Account.class);

			return loanRepository.save(loan);

		} catch (ResourceAccessException e) {
			// catch connection errors
			throw new ResourceAccessException("Problem connecting to server");
		} catch (HttpClientErrorException e) {
			// catch exceptions when http response code is 4xx or 5xx.
			throw new IdNotFoundException("AccountId not Found");
		}

	}

	@Override
	public Optional<Loan> getLoan(String loanId) {
		return loanRepository.findById(loanId);
	}

	@Override
	public List<Loan> getLoans() {

		return (List<Loan>) loanRepository.findAll();
	}

	@Override
	public List<Loan> getLoans(boolean status) {

		List<Loan> loans = (List<Loan>) loanRepository.findAll();

		return loans.stream().filter(loan -> loan.isStatus() == status).toList();
	}

	@Override
	public boolean deleteLoan(String loanId) throws IdNotFoundException {
		boolean isExists = loanRepository.existsById(loanId);
		if (!isExists)
			throw new IdNotFoundException("Account Id not found!");

		loanRepository.deleteById(loanId);
		if (loanRepository.existsById(loanId))
			return false;
		else
			return true;
	}

	@Override
	public List<Loan> getLoansByAccountId(String accountId) {
		return loanRepository.getByAccountId(accountId);
	}

	@Override
	public Loan approveLoan(String loadId) throws IdNotFoundException {

		Optional<Loan> loan = this.getLoan(loadId);

		if (loan.isEmpty())
			throw new IdNotFoundException("Loan Id Not Found!!");

		Loan fetchedLoan = loan.get();

		fetchedLoan.setStatus(true);

		return loanRepository.save(fetchedLoan);
	}

}
