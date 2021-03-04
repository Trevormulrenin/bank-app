package com.revature.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountDAOImpl;
import com.revature.exceptions.FailedToCreatePendingAccountException;
import com.revature.exceptions.InvalidDepositException;
import com.revature.exceptions.InvalidWithdrawalException;
import com.revature.model.Account;
import com.revature.util.ConnectionUtil;

public class AccountService {
	public AccountDAO accountDAO;
	private static Logger log = Logger.getLogger(AccountService.class);

	public AccountService() {
		this.accountDAO = new AccountDAOImpl();
	}

	public AccountService(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void withdrawFromAccount(int accountId, double withdraw, boolean isPending, Account account)
			throws SQLException, InvalidWithdrawalException {

		try (Connection con = ConnectionUtil.getConnection()) {

			if (withdraw <= account.getAccountBalance() && withdraw >= 0 && isPending == false) {
				accountDAO.withdrawFromAccount(accountId, withdraw, isPending, con);
				log.info("Log: Withdrawal from account with customer ID: " + account.getAccountHolderNo()
						+ " and account ID: " + account.getAccountId());
			} else {
				throw new InvalidWithdrawalException("Invalid withdrawal attempted. Please try again.");
			}
		} catch (SQLException e) {
		}
	}

	public void depositToAccount(int accountId, double deposit, boolean isPending, Account account)
			throws SQLException, InvalidDepositException {

		try (Connection con = ConnectionUtil.getConnection()) {

			if (deposit >= 0 && isPending == false) {
				accountDAO.depositToAccount(accountId, deposit, isPending, con);
				log.info("Log: Deposit to account with customer ID: " + account.getAccountHolderNo()
						+ " and account ID: " + account.getAccountId());
			} else {
				throw new InvalidDepositException("Invalid deposit attempted. Please try again.");
			}
		} catch (SQLException e) {
		}
	}

	public void applyForNewAccount(double accountBalance, String accountName, int accountHolderNo, boolean isPending)
			throws SQLException, FailedToCreatePendingAccountException {

		try (Connection con = ConnectionUtil.getConnection()) {

			if (accountBalance < 0 || accountName == null) {
				throw new FailedToCreatePendingAccountException(
						"Some account details were not provided. Please try again.");
			} else {
				accountDAO.applyForNewAccount(accountBalance, accountName, accountHolderNo, isPending, con);
			}

		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
	}

	public List<Account> getAllAccountsByCustomerId(int customerId, boolean isPending) throws SQLException {

		List<Account> accounts = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnection()) {

			accounts = accountDAO.getAllAccountsByCustomerId(customerId, isPending, con);

		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			return null;
		}
		return accounts;
	}

	public Account getAccountByCustomerAndAccountId(int customerId, int accountChoice, boolean isPending)
			throws SQLException {

		Account a = new Account();

		try (Connection con = ConnectionUtil.getConnection()) {

			a = accountDAO.getAccountByCustomerAndAccountId(customerId, accountChoice, isPending, con);

		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return a;
	}
}
