package com.revature.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.EmployeeDAO;
import com.revature.dao.EmployeeDAOImpl;
import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.exceptions.NoPendingAccountsException;
import com.revature.exceptions.NoTransactionsFoundException;
import com.revature.model.Account;
import com.revature.model.Employee;
import com.revature.model.Transaction;
import com.revature.ui.EmployeeLogIn;
import com.revature.util.ConnectionUtil;

public class EmployeeService {

	public EmployeeDAO employeeDAO;
	private static Logger log = Logger.getLogger(EmployeeService.class);

	public EmployeeService() {
		this.employeeDAO = new EmployeeDAOImpl();
	}

	public EmployeeService(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public boolean eLogInVerification(String eUsername, String ePassword)
			throws SQLException, EmployeeNotFoundException {

		boolean isSuccess = true;

		try (Connection con = ConnectionUtil.getConnection()) {
			isSuccess = employeeDAO.eLogInVerification(eUsername, ePassword, con);

			if (isSuccess != true) {
				log.info("Incorrect employee username or password");
				EmployeeLogIn eli = new EmployeeLogIn();
				eli.displayApp();
				throw new EmployeeNotFoundException("Incorrect username or password. Please try again");
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return isSuccess;
	}

	public Employee getEmployeeByUsername(String eUsername) throws SQLException {
		Employee employee = new Employee();
		try (Connection con = ConnectionUtil.getConnection()) {
			
			employee = employeeDAO.getEmployeeByUsername(eUsername, con);

		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return employee;
	}

	public List<Account> viewAllPendingAccounts(boolean isPending) throws SQLException, NoPendingAccountsException {
		List<Account> pendingAccounts = new ArrayList<>();
		try (Connection con = ConnectionUtil.getConnection()) {
			pendingAccounts = employeeDAO.viewAllPendingAccounts(isPending, con);
			boolean isEmpty = pendingAccounts.isEmpty();

			if (isEmpty == true) {
				throw new NoPendingAccountsException(
						"No current pending accounts to display. Enjoy the rest of your day!");
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return pendingAccounts;
	}

	public void confirmPendingAccount(int customerId, int accountId, boolean isPending) throws SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {

			employeeDAO.confirmPendingAccount(customerId, accountId, isPending, con);

		} catch (SQLException e) {
			log.info("Log: Pending account successfully confirmed");
		}
	}

	public void removePendingAccount(int customerId, int accountId, boolean isPending) throws SQLException {

		try (Connection con = ConnectionUtil.getConnection()) {

			employeeDAO.removePendingAccount(customerId, accountId, isPending, con);

		} catch (SQLException e) {
			log.info("Log: Pending account successfully denied");
		}
	}

	public Account getAccountByCustomerIDandPassword(int customerId, int accountId, boolean isPending)
			throws SQLException {
		
		Account account = new Account();
		
		try (Connection con = ConnectionUtil.getConnection()) {

			account = employeeDAO.getAccountByCustomerIDandPassword(customerId, accountId, isPending, con);
		}catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return account;
	}

	public List<Transaction> viewAllTransactions() throws SQLException, NoTransactionsFoundException {
		List<Transaction> t = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnection()) {
			t = employeeDAO.viewAllTransactions(con);
			boolean isEmpty = t.isEmpty();

			if (isEmpty == true) {
				throw new NoTransactionsFoundException("No Transactions to view");
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return t;
	}
}
