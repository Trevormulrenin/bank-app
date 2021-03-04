package com.revature.ui;

import java.util.Comparator;

import com.revature.model.Transaction;

public class SortByTransactionId implements Comparator<Transaction> {

	@Override
	public int compare(Transaction o1, Transaction o2) {
		return o1.getTransactionId() - o2.getTransactionId();
	}

}
