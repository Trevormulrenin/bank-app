package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Account;

public interface AccountDAO {
	
	public void applyForNewAccount(double accountBalance, String accountName, int accountHolderNo, boolean isPending, Connection con) throws SQLException;
	
	public void withdrawFromAccount(int accountId, double withdraw, boolean isPending, Connection con) throws SQLException;
	
	public void depositToAccount(int accountId, double deposit, boolean isPending, Connection con) throws SQLException;
	
	public List<Account> getAllAccountsByCustomerId(int customerId, boolean isPending, Connection con) throws SQLException;
	
	public Account getAccountByCustomerAndAccountId(int customerId, int accountChoice, boolean isPending, Connection con) throws SQLException;
}
