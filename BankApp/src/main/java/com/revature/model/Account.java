package com.revature.model;

public class Account {
	private int accountId;
	private double accountBalance;
	private int accountHolderNo;
	private String accountName;
	private boolean isPending;

	public Account() {
	}
	
	public Account(double accountBalance, int accountHolderNo, String accountName, boolean isPending) {
		this.accountBalance = accountBalance;
		this.accountHolderNo = accountHolderNo;
		this.accountName = accountName;
		this.isPending = isPending;
	}

	public Account(int accountId, double accountBalance, int accountHolderNo, String accountName, boolean isPending) {
		super();
		this.accountId = accountId;
		this.accountBalance = accountBalance;
		this.accountHolderNo = accountHolderNo;
		this.accountName = accountName;
		this.isPending = isPending;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public int getAccountHolderNo() {
		return accountHolderNo;
	}

	public void setAccountHolderNo(int accountHolderNo) {
		this.accountHolderNo = accountHolderNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(accountBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + accountHolderNo;
		result = prime * result + accountId;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + (isPending ? 1231 : 1237);
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
		Account other = (Account) obj;
		if (Double.doubleToLongBits(accountBalance) != Double.doubleToLongBits(other.accountBalance))
			return false;
		if (accountHolderNo != other.accountHolderNo)
			return false;
		if (accountId != other.accountId)
			return false;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (isPending != other.isPending)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [Customer Id=" + accountHolderNo + ", Account Id=" + accountId + ", Account Balance=" + accountBalance + 
				", Account Name=" + accountName + ", Pending=" + isPending + "]";
	}
	public String toStringApply() {
		return "Account [Customer Id=" + accountHolderNo + ", Account Balance=" + accountBalance + 
				", Account Name=" + accountName + ", Pending=" + isPending + "]";
	}
}
