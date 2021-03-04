package com.revature.exceptions;

public class InvalidDepositException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidDepositException() {
	}

	public InvalidDepositException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InvalidDepositException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidDepositException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidDepositException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
