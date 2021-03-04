package com.revature.exceptions;

public class NoTransactionsFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 879755126480069344L;

	public NoTransactionsFoundException() {
		// TODO Auto-generated constructor stub
	}

	public NoTransactionsFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoTransactionsFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoTransactionsFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoTransactionsFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
