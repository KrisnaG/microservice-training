package com.dnb.accountservice.exceptions;

public class AccountMappingException extends Exception {

	static final long serialVersionUID = 1L;

	public AccountMappingException(String msg) {
		super(msg);
	}

	public String toString() {
		return super.toString() + super.getMessage();
	}
}
