package com.dnb.loanservice.dto;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.dnb.loanservice.enums.LoanType;
import com.dnb.loanservice.utils.CustomIdGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_seq")
	@GenericGenerator(name = "loan_seq", type = CustomIdGenerator.class, parameters = {
			@Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = CustomIdGenerator.VALUE_PREFIX_PARAMETER, value = "LOAN_"),
			@Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String loanId;

	@NotNull(message = "loan type should not be null")
	@Enumerated(EnumType.STRING)
	private LoanType loanType;

	@Min(value = 10000, message = "Loan Amount cannot be less than 10000")
	private long amount;

	// initial it will be pending
	private boolean status = false;

	private String accountId;

}
