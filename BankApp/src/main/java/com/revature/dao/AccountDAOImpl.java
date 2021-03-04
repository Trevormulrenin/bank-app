package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Account;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public void applyForNewAccount(double accountBalance, String accountName, int accountHolderNo, boolean isPending,
			Connection con) throws SQLException {
		String apply = "INSERT INTO bank_app.accounts(account_balance, account_name, is_pending, customer) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(apply);

		pstmt.setDouble(1, accountBalance);
		pstmt.setString(2, accountName);
		pstmt.setBoolean(3, isPending);
		pstmt.setInt(4, accountHolderNo);
		
		pstmt.executeQuery();
	}

	@Override
	public void withdrawFromAccount(int accountId, double withdraw, boolean isPending, Connection con)
			throws SQLException {

		String aWithdraw = "UPDATE bank_app.accounts SET account_balance = account_balance - ? WHERE account_id = ? AND is_pending = ?";

		PreparedStatement pstmt = con.prepareStatement(aWithdraw);

		pstmt.setDouble(1, withdraw);
		pstmt.setInt(2, accountId);
		pstmt.setBoolean(3, isPending);

		pstmt.executeQuery();
	}

	@Override
	public void depositToAccount(int accountId, double deposit, boolean isPending, Connection con) throws SQLException {

		String aDeposit = "UPDATE bank_app.accounts SET account_balance = account_balance + ? WHERE account_id = ? AND is_pending = ?";
		PreparedStatement pstmt = con.prepareStatement(aDeposit);

		pstmt.setDouble(1, deposit);
		pstmt.setInt(2, accountId);
		pstmt.setBoolean(3, isPending);

		pstmt.executeQuery();

	}

	@Override
	public List<Account> getAllAccountsByCustomerId(int customerId, boolean isPending, Connection con)
			throws SQLException {

		List<Account> customerAccounts = new ArrayList<>();

		String sql = "SELECT * FROM bank_app.accounts WHERE customer = ? AND is_pending = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, customerId);
		pstmt.setBoolean(2, isPending);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int accountId = rs.getInt("account_id");
			double accountBalance = rs.getDouble("account_balance");
			String accountName = rs.getString("account_name");

			customerAccounts.add(new Account(accountId, accountBalance, customerId, accountName, isPending));
		}
		return customerAccounts;
	}

	@Override
	public Account getAccountByCustomerAndAccountId(int customerId, int accountChoice, boolean isPending,
			Connection con) throws SQLException {
		Account a = new Account();
		String getAccount = "SELECT * FROM bank_app.accounts WHERE customer = ? AND account_id = ? AND is_pending = ?";
		PreparedStatement pstmt = con.prepareStatement(getAccount);
		
		pstmt.setInt(1, customerId);
		pstmt.setInt(2, accountChoice);
		pstmt.setBoolean(3, isPending);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int accountId = rs.getInt("account_id");
			int accountHolderNo = rs.getInt("customer");
			double accountBalance = rs.getDouble("account_balance");
			boolean isPendingAcc = rs.getBoolean("is_pending");
			String accountName = rs.getString("account_name");
			
			a = new Account(accountId, accountBalance, accountHolderNo, accountName, isPendingAcc);
		}
		return a;
	}
}
