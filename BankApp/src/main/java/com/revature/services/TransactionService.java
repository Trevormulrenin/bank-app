package com.revature.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.TransactionDAO;
import com.revature.dao.TransactionDAOImpl;
import com.revature.exceptions.NoCurrentPendingTransactionsException;
import com.revature.exceptions.NoTransactionsFoundException;
import com.revature.model.Transaction;
import com.revature.util.ConnectionUtil;

public class TransactionService {

	public TransactionDAO transactionDAO;
	private static Logger log = Logger.getLogger(EmployeeService.class);

	public TransactionService() {
		this.transactionDAO = new TransactionDAOImpl();
	}

	public TransactionService(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public void createNewTransaction(int accountSenderCustomerId, int accountSenderAccountId,
			int accountReceiverCustomerId, int accountReceiverAccountId, double transactionAmount,
			boolean isPendingTransaction) throws SQLException {

		try (Connection con = ConnectionUtil.getConnection()) {
			
			transactionDAO.createNewTransaction(accountSenderCustomerId, accountSenderAccountId,
					accountReceiverCustomerId, accountReceiverAccountId, transactionAmount, isPendingTransaction, con);
			log.info("Transaction created");
		} catch (SQLException e) {
		}
	}

	public void executeNewTransaction(double transactionAmount, int accountSenderAccountId, int accountSenderCustomerId,
			int accountReceiverAccountId, int accountReceiverCustomerId, int transactionId, boolean isPendingTransaction)
			throws SQLException {

		try (Connection con = ConnectionUtil.getConnection()) {
			
			transactionDAO.executeNewTransaction(transactionAmount, accountSenderAccountId, accountSenderCustomerId,
			accountReceiverAccountId, accountReceiverCustomerId, isPendingTransaction, transactionId, con);
			log.info("Transaction confirmed");
		} catch (SQLException e) {
		}
	}

	public List<Transaction> getAllIncomingTransactionsByCustomerAndAccountId(int customerId, int accountId,
			boolean isPendingTransaction) throws SQLException, NoCurrentPendingTransactionsException {
		
		List<Transaction> pendingTransactions = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnection()) {
			pendingTransactions = transactionDAO.getAllIncomingTransactionsByCustomerAndAccountId(customerId, accountId,
					isPendingTransaction, con);
			boolean isEmpty = pendingTransactions.isEmpty();

			if (isEmpty == true) {
				throw new NoCurrentPendingTransactionsException("You have no current pending transactions");
			} else {
				log.info("Log: Incoming transactions received by customer with id: " + customerId + " and account id: "
						+ accountId);
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return pendingTransactions;
	}

	public Transaction getTransactionByTransactionId(int transactionId, int customerId, int accountId)
			throws SQLException, NoTransactionsFoundException{
	
		Transaction t = new Transaction();

		try (Connection con = ConnectionUtil.getConnection()) {
			
			t = transactionDAO.getTransactionByTransactionId(transactionId, customerId, accountId, con);
			if(t.getTransactionId() != transactionId) {
				throw new NoTransactionsFoundException("Transaction with id: " + transactionId + " was not found");
			}
		} catch (SQLException e) {
		}
		return t;
	}

	public void denyNewTransaction(int transactionId) throws SQLException {
		
		try (Connection con = ConnectionUtil.getConnection()) {
		
			transactionDAO.denyNewTransaction(transactionId, con);
			
		} catch (SQLException e) {
		}
	}
}
