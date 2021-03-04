package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {

	public void executeNewTransaction(double transactionAmount, int accountSenderAccountId, int accountSenderCustomerId,
			int accountReceiverAccountId, int accountReceiverCustomerId, boolean isPendingTransaction, Connection con)
			throws SQLException {

		String executeTransaction = "BEGIN TRANSACTION;"
				+ "UPDATE bank_app.accounts SET account_balance = account_balance - ? WHERE account_id = ? AND customer = ?;"
				+ "UPDATE bank_app.accounts SET account_balance = account_balance + ? WHERE account_id = ? AND customer = ?;"
				+ "UPDATE bank_app.transactions SET is_pending_transaction = ? WHERE account_sender_account_id = ? AND account_sender_customer_id = ?;"
				+ "UPDATE bank_app.transactions SET is_pending_transaction = ? WHERE account_receiver_account_id = ? AND account_receiver_customer_id = ?;"
				+ "COMMIT;";
		PreparedStatement pstmt = con.prepareStatement(executeTransaction);

		// Sender
		pstmt.setDouble(1, transactionAmount);
		pstmt.setInt(2, accountSenderAccountId);
		pstmt.setInt(3, accountSenderCustomerId);

		pstmt.setBoolean(7, isPendingTransaction);
		pstmt.setInt(8, accountSenderAccountId);
		pstmt.setInt(9, accountSenderCustomerId);

		// Receiver
		pstmt.setDouble(4, transactionAmount);
		pstmt.setInt(5, accountReceiverAccountId);
		pstmt.setInt(6, accountReceiverCustomerId);

		pstmt.setBoolean(10, isPendingTransaction);
		pstmt.setInt(11, accountReceiverAccountId);
		pstmt.setInt(12, accountReceiverCustomerId);

		pstmt.executeQuery();
	}

	@Override
	public void createNewTransaction(int accountSenderCustomerId, int accountSenderAccountId,
			int accountReceiverCustomerId, int accountReceiverAccountId, double transactionAmount,
			boolean isPendingTransaction, Connection con) throws SQLException {

		String createTransaction = "BEGIN TRANSACTION;"
				+ "INSERT INTO bank_app.transactions(transaction_amount, is_pending_transaction, account_sender_account_id,"
				+ "account_sender_customer_id, account_receiver_account_id, account_receiver_customer_id) VALUES(?, ?, ?, ?, ?, ?);"
				+ "UPDATE bank_app.accounts SET is_pending_transaction = ? WHERE customer = ? AND account_id = ?;"
				+ "COMMIT;";

		PreparedStatement pstmt = con.prepareStatement(createTransaction);

		// Transaction
		pstmt.setDouble(1, transactionAmount);
		pstmt.setBoolean(2, isPendingTransaction);
		pstmt.setInt(3, accountSenderAccountId);
		pstmt.setInt(4, accountSenderCustomerId);
		pstmt.setInt(5, accountReceiverAccountId);
		pstmt.setInt(6, accountReceiverCustomerId);

		// Pending Transactions Account Reference
		pstmt.setBoolean(7, isPendingTransaction);
		pstmt.setInt(8, accountReceiverCustomerId);
		pstmt.setInt(9, accountReceiverAccountId);

		pstmt.executeQuery();
	}

	public double senderNewBalance(int accountId, int customerId, Connection con) throws SQLException {
		double senderNewBalance = 0;
		String snb = "SELECT account_balance FROM bank_app.accounts WHERE account_id = ? AND customer_id = ?";
		PreparedStatement pstmt = con.prepareStatement(snb);

		pstmt.setInt(1, accountId);
		pstmt.setInt(2, customerId);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			senderNewBalance = rs.getDouble("account_balance");
		}
		return senderNewBalance;
	}

	@Override
	public double receiverNewBalance(int accountId, int customerId, Connection con) throws SQLException {
		double receiverNewBalance = 0;
		String rnb = "SELECT account_balance FROM bank_upp.accounts WHERE account_id = ? AND customer_id = ?";
		PreparedStatement pstmt = con.prepareStatement(rnb);

		pstmt.setInt(1, accountId);
		pstmt.setInt(2, customerId);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			receiverNewBalance = rs.getDouble("account_balance");
		}
		return receiverNewBalance;
	}

	@Override
	public List<Transaction> getAllIncomingTransactionsByCustomerAndAccountId(int customerId, int accountId,
			boolean isPendingTransaction, Connection con) throws SQLException {
		List<Transaction> pendingTransactions = new ArrayList<>();

		String pTransactions = "SELECT * FROM bank_app.transactions WHERE account_receiver_customer_id = ? AND account_receiver_account_id = ? AND "
				+ "is_pending_transaction = ?";
		PreparedStatement pstmt = con.prepareStatement(pTransactions);

		pstmt.setInt(1, customerId);
		pstmt.setInt(2, accountId);
		pstmt.setBoolean(3, isPendingTransaction);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int transactionId = rs.getInt("transaction_id");
			double transactionAmount = rs.getDouble("transaction_amount");
			boolean pendingTransaction = rs.getBoolean("is_pending_transaction");
			int accountSenderAccountId = rs.getInt("account_sender_account_id");
			int accountSenderCustomerId = rs.getInt("account_sender_customer_id");
			int accountReceiverAccountId = rs.getInt("account_receiver_account_id");
			int accountReceiverCustomerId = rs.getInt("account_receiver_customer_id");

			pendingTransactions.add(new Transaction(transactionId, transactionAmount, accountSenderAccountId,
					accountSenderCustomerId, accountReceiverAccountId, accountReceiverCustomerId, pendingTransaction));
		}
		return pendingTransactions;

	}

	@Override
	public Transaction getTransactionByTransactionId(int transactionId, int customerId, int accountId, Connection con)
			throws SQLException {

		Transaction t = new Transaction();

		String transaction = "SELECT * FROM bank_app.transactions WHERE transaction_id = ? AND account_receiver_customer_id = ? AND account_receiver_account_id = ?";
		PreparedStatement pstmt = con.prepareStatement(transaction);

		pstmt.setInt(1, transactionId);
		pstmt.setInt(2, customerId);
		pstmt.setInt(3, accountId);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			t.setTransactionId(transactionId);
			double transactionAmount = rs.getDouble("transaction_amount");
			t.setTransactionAmount(transactionAmount);
			boolean isPendingTransaction = rs.getBoolean("is_pending_transaction");
			t.setPendingTransaction(isPendingTransaction);
			int accountSenderCustomerId = rs.getInt("account_sender_customer_id");
			t.setAccountSenderCustomerId(accountSenderCustomerId);
			int accountSenderAccountId = rs.getInt("account_sender_account_id");
			t.setAccountSenderAccountId(accountSenderAccountId);
			int accountReceiverAccountId = rs.getInt("account_receiver_account_id");
			t.setAccountReceiverAccountId(accountReceiverAccountId);
			int accountReceiverCustomerId = rs.getInt("account_receiver_customer_id");
			t.setAccountReceiverCustomerId(accountReceiverCustomerId);

			t = new Transaction(transactionId, transactionAmount, accountSenderCustomerId, accountSenderAccountId,
					accountReceiverAccountId, accountReceiverCustomerId, isPendingTransaction);
		}
		return t;
	}

	@Override
	public void denyNewTransaction(int transactionId, Connection con) throws SQLException {
		String deleteTransaction = "DELETE FROM bank_app.transactions WHERE transaction_id = ?";
		PreparedStatement pstmt = con.prepareStatement(deleteTransaction);

		pstmt.setInt(1, transactionId);

		pstmt.executeQuery();
	}

}
