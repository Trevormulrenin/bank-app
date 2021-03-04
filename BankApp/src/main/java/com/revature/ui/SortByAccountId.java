package com.revature.ui;

import java.util.Comparator;

import com.revature.model.Account;

public class SortByAccountId implements Comparator<Account> {
	
	public int compare(Account a, Account b) {
		return a.getAccountId() - b.getAccountId();
	}
}
