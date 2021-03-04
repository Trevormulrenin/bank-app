package com.revature.model;

public class Transaction {

	private int transactionId;
	private double transactionAmount;
	private int accountSenderAccountId;
	private int accountSenderCustomerId;
	private int accountReceiverAccountId;
	private int accountReceiverCustomerId;
	private boolean isPendingTransaction;

	public Transaction() {
	}
	
	public Transaction(int transactionId, double transactionAmount, int accountSenderAccountId,
			int accountSenderCustomerId, int accountReceiverAccountId, int accountReceiverCustomerId,
			 boolean isPendingTransaction) {
		super();
		this.transactionId = transactionId;
		this.transactionAmount = transactionAmount;
		this.accountSenderAccountId = accountSenderAccountId;
		this.accountSenderCustomerId = accountSenderCustomerId;
		this.accountReceiverAccountId = accountReceiverAccountId;
		this.accountReceiverCustomerId = accountReceiverCustomerId;
		this.isPendingTransaction = isPendingTransaction;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public int getAccountSenderAccountId() {
		return accountSenderAccountId;
	}

	public void setAccountSenderAccountId(int accountSenderAccountId) {
		this.accountSenderAccountId = accountSenderAccountId;
	}

	public int getAccountSenderCustomerId() {
		return accountSenderCustomerId;
	}

	public void setAccountSenderCustomerId(int accountSenderCustomerId) {
		this.accountSenderCustomerId = accountSenderCustomerId;
	}

	public int getAccountReceiverAccountId() {
		return accountReceiverAccountId;
	}

	public void setAccountReceiverAccountId(int accountReceiverAccountId) {
		this.accountReceiverAccountId = accountReceiverAccountId;
	}

	public int getAccountReceiverCustomerId() {
		return accountReceiverCustomerId;
	}

	public void setAccountReceiverCustomerId(int accountReceiverCustomerId) {
		this.accountReceiverCustomerId = accountReceiverCustomerId;
	}

	public boolean isPendingTransaction() {
		return isPendingTransaction;
	}

	public void setPendingTransaction(boolean isPendingTransaction) {
		this.isPendingTransaction = isPendingTransaction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountReceiverAccountId;
		result = prime * result + accountReceiverCustomerId;
		result = prime * result + accountSenderAccountId;
		result = prime * result + accountSenderCustomerId;
		result = prime * result + (isPendingTransaction ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(transactionAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + transactionId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (accountReceiverAccountId != other.accountReceiverAccountId)
			return false;
		if (accountReceiverCustomerId != other.accountReceiverCustomerId)
			return false;
		if (accountSenderAccountId != other.accountSenderAccountId)
			return false;
		if (accountSenderCustomerId != other.accountSenderCustomerId)
			return false;
		if (isPendingTransaction != other.isPendingTransaction)
			return false;
		if (Double.doubleToLongBits(transactionAmount) != Double.doubleToLongBits(other.transactionAmount))
			return false;
		if (transactionId != other.transactionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionAmount=" + transactionAmount
				+ ", accountSenderAccountId=" + accountSenderAccountId + ", accountSenderCustomerId="
				+ accountSenderCustomerId + ", accountReceiverAccountId=" + accountReceiverAccountId
				+ ", accountReceiverCustomerId=" + accountReceiverCustomerId + ", isPendingTransaction="
				+ isPendingTransaction + "]";
	}

	

}
