package com.revature.exceptions;

public class AccountNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7499856651910714628L;

	public AccountNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public AccountNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AccountNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public AccountNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccountNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
