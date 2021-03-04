package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Account;
import com.revature.model.Employee;
import com.revature.model.Transaction;

public interface EmployeeDAO {
	public List<Account> viewAllPendingAccounts(boolean isPending, Connection con) throws SQLException;
	
	public boolean eLogInVerification(String eUsername, String ePassword, Connection con) throws SQLException;
	
	public Employee getEmployeeByUsername(String eUsername, Connection con) throws SQLException;
	
	public void confirmPendingAccount(int customerId, int accountId, boolean isPending, Connection con) throws SQLException;
	
	public void removePendingAccount(int customerId, int accountId, boolean isPending, Connection con) throws SQLException;
	
	public Account getAccountByCustomerIDandPassword(int customerId, int accountId, boolean isPending, Connection con) throws SQLException;
	
	public List<Transaction> viewAllTransactions(Connection con) throws SQLException;
	
}
