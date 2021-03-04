package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Transaction;

public interface TransactionDAO {
	public void executeNewTransaction(double transactionAmount, int accountSenderAccountId, int accountSenderCustomerId,
			int accountReceiverAccountId, int accountReceiverCustomerId, boolean isPendingTransaction, int transactionId, Connection con) throws SQLException;
	
	public void createNewTransaction(int accountSenderCustomerId, int accountSenderAccountId, int accountReceiverCustomerId,
			int accountReceiverAccountId, double transactionAmount, boolean isPendingTransaction, Connection con) throws SQLException;
	
	public double senderNewBalance(int accountId, int customerId, Connection con) throws SQLException;
	
	public double receiverNewBalance(int accountId, int customerId, Connection con) throws SQLException;
	
	public List<Transaction> getAllIncomingTransactionsByCustomerAndAccountId(int customerId, int accountId, boolean isPendingTransaction, Connection con) throws SQLException;
	
	public Transaction getTransactionByTransactionId(int transactionId, int customerId, int accountId, Connection con) throws SQLException;
	
	public void denyNewTransaction(int transactionId, Connection con) throws SQLException;
}
