package com.revature.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.revature.dao.AccountDAO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.AccountsNotFoundException;
import com.revature.exceptions.FailedToCreatePendingAccountException;
import com.revature.model.Account;
import com.revature.util.ConnectionUtil;

public class AccountServiceTest {

	AccountService accountService;
	public static Connection mockConnection;
	public static AccountDAO accountDAO;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	public static List<Account> accounts;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
		accountDAO = mock(AccountDAO.class);
		mockConnection = mock(Connection.class);
		
		//when(accountDAO.getCustomerByUsername(eq("trevormul"), any(Connection.class))).thenReturn
			//	(new Customer(1, "trevormul", "password", "Trevor", "Mulrenin"));
		
		
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account(999200.56, 1, "Checkings", false));
		accounts.add(new Account(10000.2, 1, "Savings2", false));
		when(accountDAO.getAllAccountsByCustomerId(eq(1), eq(false), eq(mockConnection))).thenReturn(accounts);
	
		Account account = new Account(800.33, 1, "Checkings", false);
		when(accountDAO.getAccountByCustomerAndAccountId(eq(1), eq(1), eq(false), eq(mockConnection))).thenReturn(account);
		when(accountDAO.getAccountByCustomerAndAccountId(eq(1), eq(10), eq(false), eq(mockConnection))).thenReturn(null);
		
	}
	
	@Before
	public void setUp() throws Exception {
		accountService = new AccountService(accountDAO);
	}
	
	@Test (expected = FailedToCreatePendingAccountException.class)
	public void applyForNewAccount() throws FailedToCreatePendingAccountException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			accountService.applyForNewAccount(-100, "Checkings2", 2, true);
		}
	}
	
	@Test
	public void testValidGetAllAccountsByCustomerId() throws SQLException, AccountsNotFoundException{
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			List<Account> actual = accountService.getAllAccountsByCustomerId(1, false);
			
			List<Account> accounts = new ArrayList<>();
			Account account = new Account(999200.56, 1, "Checkings", false);
			Account account2 = new Account(10000.2, 1, "Savings2", false);
			accounts.add(account);
			accounts.add(account2);
			List<Account> expected = accounts;
			
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void testValidGetAccountByCustomerAndAccountId() throws SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			Account actual = accountService.getAccountByCustomerAndAccountId(1, 1, false);
			
		}
	}
}
