package com.revature.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.revature.dao.EmployeeDAO;
import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.util.ConnectionUtil;

public class EmployeeServiceTest {
	EmployeeService employeeService;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	public static EmployeeDAO employeeDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		employeeDAO = mock(EmployeeDAO.class);
		mockConnection = mock(Connection.class);

		when(employeeDAO.eLogInVerification(eq("JohnD123"), eq("JohnDoe123"), eq(mockConnection))).thenReturn(true);
	}

	@Before
	public void setUp() throws Exception {
		employeeService = new EmployeeService(employeeDAO);
	}

	@Test
	public void testValidELogInVerification() throws SQLException, EmployeeNotFoundException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
		
			boolean actual = employeeService.eLogInVerification("JohnD123", "JohnDoe123");
			boolean expected = true;

			assertEquals(actual, expected);
		}
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testInvalidELogInVerification() throws SQLException, EmployeeNotFoundException {
		
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
		
			employeeService.eLogInVerification("JonD123", "JohnDoe123");
		}
	}
}
