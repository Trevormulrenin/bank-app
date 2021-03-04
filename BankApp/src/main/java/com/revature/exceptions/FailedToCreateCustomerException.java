package com.revature.exceptions;


public class FailedToCreateCustomerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7887063809227961028L;

	public FailedToCreateCustomerException() {
		
		super();
	}

	public FailedToCreateCustomerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FailedToCreateCustomerException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedToCreateCustomerException(String message) {
		super(message);
	}

	public FailedToCreateCustomerException(Throwable cause) {
		super(cause);
	}

}
