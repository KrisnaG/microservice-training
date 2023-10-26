package com.dnb.loanservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dnb.loanservice.dto.Loan;
import com.dnb.loanservice.exceptions.IdNotFoundException;
import com.dnb.loanservice.mapper.EntityToResponseMapper;
import com.dnb.loanservice.mapper.RequestToEntityMapper;
import com.dnb.loanservice.request.payload.LoanRequest;
import com.dnb.loanservice.response.payload.LoanResponse;
import com.dnb.loanservice.service.LoanService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/loan")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@Autowired
	private RequestToEntityMapper requestToEntityMapper;

	@Autowired
	private EntityToResponseMapper entityToResponseMapper;

	@PostMapping("/create")
	public ResponseEntity<?> createLoan(@Valid @RequestBody LoanRequest loanRequest) throws IdNotFoundException {

		Loan loan;
		loan = requestToEntityMapper.loanRequestToEntity(loanRequest);
		System.out.println(loan);

		LoanResponse createdLoan = entityToResponseMapper.loanToAccountResponse(loanService.createLoan(loan));
		return new ResponseEntity<LoanResponse>(createdLoan, HttpStatus.CREATED);

	}

	@GetMapping("/{loanId}")
	public ResponseEntity<?> getLoan(@PathVariable("loanId") String loanId) throws IdNotFoundException {

		Optional<Loan> loan = loanService.getLoan(loanId);

		if (loan.isEmpty())
			throw new IdNotFoundException("Loan Id Not Found!!");

		LoanResponse loanResponse = entityToResponseMapper.loanToAccountResponse(loan.get());

		return ResponseEntity.ok(loanResponse);
	}

	@GetMapping("/account/{accountId}")
	public ResponseEntity<?> getLoanByAccountId(@PathVariable("accountId") String accountId)
			throws IdNotFoundException {
		List<Loan> loans = loanService.getLoansByAccountId(accountId);

		List<LoanResponse> loanResponses;
		loanResponses = loans.stream().map(loan -> entityToResponseMapper.loanToAccountResponse(loan))
				.collect(Collectors.toList());

		return ResponseEntity.ok(loanResponses);
	}

	// http://localhost:9191/api/loan/all?status=?
	@GetMapping("/all")
	public ResponseEntity<?> getLoans(@RequestParam(name = "status", required = false) Boolean status) {

		List<Loan> loans;
		if (status != null) {
			loans = loanService.getLoans(status.booleanValue());
		} else {
			loans = loanService.getLoans();
		}

		List<LoanResponse> loanResponses;
		loanResponses = loans.stream().map(loan -> entityToResponseMapper.loanToAccountResponse(loan))
				.collect(Collectors.toList());

		return ResponseEntity.ok(loanResponses);
	}

	@PutMapping("/approve/{loanId}")
	public ResponseEntity<?> changeLoanStatus(@PathVariable("loanId") String loadId) throws IdNotFoundException {

		Loan loan = loanService.approveLoan(loadId);

		return ResponseEntity.ok(loan);
	}

	@DeleteMapping("/{loanId}")
	public ResponseEntity<?> deleteLoanById(@PathVariable("loanId") String loanId) throws IdNotFoundException {

		loanService.deleteLoan(loanId);
		return ResponseEntity.noContent().build();
	}

}
