package com.dnb.accountservice.exceptions;

public class BalanceNotSufficientException extends Exception {

	static final long serialVersionUID = 1L;

	public BalanceNotSufficientException(String msg) {
		super(msg);
	}

	public String toString() {
		return super.toString() + super.getMessage();
	}
}
