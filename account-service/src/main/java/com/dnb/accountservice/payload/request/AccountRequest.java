package com.dnb.accountservice.payload.request;

import com.dnb.accountservice.enums.AccountType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountRequest {
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "accountType cannot be null")
	private AccountType accountType;

	@NotBlank(message = "Pan Number cannot be blank")
	private String panNumber;

	@NotBlank(message = "Aadhaar Number cannot be blank")
	private String aadharNumber;

	@NotBlank(message = "Contact Details cannot be blank")
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid Contact Number")
	private String contact;

	@Min(value = 10000, message = "Minimum Balance is 10000")
	private long balance;

	@NotNull
	private Integer customerId;
}
