package com.revature.exceptions;

public class NoPendingAccountsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5046920512810405662L;

	public NoPendingAccountsException() {
		// TODO Auto-generated constructor stub
	}

	public NoPendingAccountsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoPendingAccountsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoPendingAccountsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoPendingAccountsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
