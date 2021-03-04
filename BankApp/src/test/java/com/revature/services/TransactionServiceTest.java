package com.revature.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.revature.dao.CustomerDAO;
import com.revature.dao.EmployeeDAO;
import com.revature.dao.TransactionDAO;
import com.revature.exceptions.TransactionNotFoundException;
import com.revature.model.Transaction;
import com.revature.util.ConnectionUtil;

public class TransactionServiceTest {
	TransactionService transactionService;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	public static TransactionDAO transactionDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		transactionDAO = mock(TransactionDAO.class);
		mockConnection = mock(Connection.class);

		Transaction t = new Transaction(1, 880.2, 1, 1, 1, 3, true);
		when(transactionDAO.getTransactionByTransactionId(eq(1), eq(1), eq(1), eq(mockConnection))).thenReturn(t);
	}
	
	@Before
	public void setUp() throws Exception {
		transactionService = new TransactionService(transactionDAO);
	}
	
	@Test
	public void testValidTransactionByTransactionId() throws SQLException, TransactionNotFoundException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
		Transaction actual = transactionService.getTransactionByTransactionId(1, 1, 1);
		
		Transaction t = new Transaction(1, 880.2, 1, 1, 1, 3, true);
		Transaction expected = t;
		
		assertEquals(actual, expected);
		}
	}
	
//	@Test(expected = SQLException.class)
//	public void testInvalidTransactionByTransactionId() throws SQLException {
//		
//		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
//			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
//		
//			Transaction actual = transactionService.getTransactionByTransactionId(1, 1, 10);
//
//		}
	
	
}
	
	//@Test
	//public List<Transaction> getAllIncomingTransactionsByCustomerAndAccountId
