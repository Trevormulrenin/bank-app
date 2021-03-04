package com.revature.exceptions;

public class InvalidTransactionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8972531004171755268L;

	public InvalidTransactionException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidTransactionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidTransactionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidTransactionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidTransactionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
