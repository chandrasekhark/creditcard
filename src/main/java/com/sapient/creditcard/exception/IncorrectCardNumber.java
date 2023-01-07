package com.sapient.creditcard.exception;

public class IncorrectCardNumber extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectCardNumber() {
		super();
	}

	public IncorrectCardNumber(String errorMessage) {
		super(errorMessage);
	}
	
}
