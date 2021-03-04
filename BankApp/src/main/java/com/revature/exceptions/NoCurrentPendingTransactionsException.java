package com.revature.exceptions;

public class NoCurrentPendingTransactionsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1752822414988863896L;

	public NoCurrentPendingTransactionsException() {
	
	}

	public NoCurrentPendingTransactionsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NoCurrentPendingTransactionsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoCurrentPendingTransactionsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoCurrentPendingTransactionsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
