package com.dnb.accountservice.exceptions;

public class AccountCloseException extends Exception {
	static final long serialVersionUID = 1L;

	public AccountCloseException(String msg) {
		super(msg);
	}

	public String toString() {
		return super.toString() + super.getMessage();
	}
}
