package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Account;
import com.revature.model.Employee;
import com.revature.model.Transaction;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public boolean eLogInVerification(String eUsername, String ePassword, Connection con) throws SQLException {
		boolean isSuccess = false;

		String password = "SELECT employee_password FROM bank_app.employees WHERE employee_username = ?";
		PreparedStatement pstmt = con.prepareStatement(password);

		pstmt.setString(1, eUsername);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			password = rs.getString("employee_password");
			if (ePassword.equals(password)) {
				isSuccess = true;
			} else {
				isSuccess = false;
			}
		}
		return isSuccess;
	}

	@Override
	public Employee getEmployeeByUsername(String eUsername, Connection con) throws SQLException {
		Employee employee = null;

		String sql = "SELECT * FROM bank_app.employees WHERE employee_username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, eUsername);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int eId = rs.getInt("employee_id");
			String username = rs.getString("employee_username");
			String eFirstName = rs.getString("employee_first_name");
			String eLastName = rs.getString("employee_last_name");
			String ePassword = rs.getString("employee_password");

			employee = new Employee(eId, username, ePassword, eFirstName, eLastName);
		}
		return employee;
	}

	@Override
	public List<Account> viewAllPendingAccounts(boolean isPending, Connection con) throws SQLException {
		List<Account> pendingAccounts = new ArrayList<>();
		Account pAccount = new Account();

		String sql = "SELECT * FROM bank_app.accounts WHERE is_pending = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setBoolean(1, isPending);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int accountId = rs.getInt("account_id");
			double accountBalance = rs.getDouble("account_balance");
			String accountName = rs.getString("account_name");
			int accountHolderNo = rs.getInt("customer");

			pAccount = new Account(accountId, accountBalance, accountHolderNo, accountName, isPending);
			pendingAccounts.add(pAccount);
		}

		return pendingAccounts;
	}

	@Override
	public void confirmPendingAccount(int customerId, int accountId, boolean isPending, Connection con) throws SQLException {

		String confirm = "UPDATE bank_app.accounts SET is_pending = ? WHERE customer = ? AND account_id = ?";
		PreparedStatement pstmt = con.prepareStatement(confirm);

		pstmt.setBoolean(1, isPending);
		pstmt.setInt(2, customerId);
		pstmt.setInt(3, accountId);
		
		pstmt.executeQuery();

	}

	@Override
	public void removePendingAccount(int customerId, int accountId, boolean isPending, Connection con) throws SQLException {

		String deny = "DELETE FROM bank_app.accounts WHERE is_pending = ? AND customer = ? AND account_id = ?";
		PreparedStatement pstmt = con.prepareStatement(deny);

		pstmt.setBoolean(1, isPending);
		pstmt.setInt(2, customerId);
		pstmt.setInt(3, accountId);

		pstmt.executeQuery();
	}

	@Override
	public Account getAccountByCustomerIDandPassword(int customerId, int accountId, boolean isPending, Connection con)
			throws SQLException {
		Account account = new Account();
		
		String sql = "SELECT * FROM bank_app.accounts WHERE customer = ? AND account_id = ? AND is_pending = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, customerId);
		pstmt.setInt(2, accountId);
		pstmt.setBoolean(3, isPending);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int aId = rs.getInt("account_id");
			double accountBalance = rs.getDouble("account_balance");
			String accountName = rs.getString("account_name");
			int cId = rs.getInt("customer");
			boolean isP = rs.getBoolean("is_pending");
			
			account = new Account(aId, accountBalance, cId, accountName, isP);
		}
		return account;
	}

	@Override
	public List<Transaction> viewAllTransactions(Connection con) throws SQLException {
		
		List<Transaction> allTransactions = new ArrayList<>();
		
		String getAllTransactions = "SELECT * FROM bank_app.transactions";
		PreparedStatement pstmt = con.prepareStatement(getAllTransactions);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int transactionId = rs.getInt("transaction_id");
			double transactionAmount = rs.getDouble("transaction_amount");
			boolean isPendingTransaction = rs.getBoolean("is_pending_transaction");
			int accountSenderAccountId = rs.getInt("account_sender_account_id");
			int accountSenderCustomerId = rs.getInt("account_sender_customer_id");
			int accountReceiverAccountId = rs.getInt("account_receiver_account_id");
			int accountReceiverCustomerId = rs.getInt("account_receiver_customer_id");
			
			allTransactions.add(new Transaction(transactionId, transactionAmount, accountSenderAccountId, accountSenderCustomerId, accountReceiverAccountId, accountReceiverCustomerId, isPendingTransaction));
		}
		return allTransactions;
	}
}
