package com.dnb.accountservice.dto;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.dnb.accountservice.enums.AccountType;
import com.dnb.accountservice.utils.CustomIdGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
	@GenericGenerator(name = "account_seq", type = CustomIdGenerator.class, parameters = {
			@Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = CustomIdGenerator.VALUE_PREFIX_PARAMETER, value = "ACC_"),
			@Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String accountId;

	private boolean accountStatus = true;

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
