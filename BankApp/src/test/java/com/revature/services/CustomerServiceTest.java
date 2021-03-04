package com.revature.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import com.revature.dao.CustomerDAO;
import com.revature.exceptions.CustomerNotFoundException;
import com.revature.exceptions.FailedToCreateCustomerException;
import com.revature.model.Customer;
import com.revature.util.ConnectionUtil;

public class CustomerServiceTest {

	CustomerService customerService;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	public static CustomerDAO customerDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		customerDAO = mock(CustomerDAO.class);
		mockConnection = mock(Connection.class);

		Customer customer = new Customer(1, "trevormul", "password", "Trevor", "Mulrenin");
		when(customerDAO.getCustomerByUsername(eq("trevormul"), eq(mockConnection))).thenReturn(customer);

	}

	@Before
	public void setUp() throws Exception {
		customerService = new CustomerService(customerDAO);
	}

	@Test
	public void testValidUsername() throws CustomerNotFoundException, SQLException {
	
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			Customer actual = customerService.getCustomerByUsername("trevormul");
			Customer expected = new Customer(1, "trevormul", "password", "Trevor", "Mulrenin");

			assertEquals(expected, actual);
		}
	}

	@Test(expected = CustomerNotFoundException.class)
	public void testInvalidUsername() throws CustomerNotFoundException, SQLException {
		
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			customerService.getCustomerByUsername("trevormull");
		}
	}

	@Test
	public void testCreateNewCustomer()throws SQLException, FailedToCreateCustomerException, CustomerNotFoundException {
		
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			customerService.createNewCustomer("joesmith1", "joey123", "Joe", "Smith");
		}
	}

	@Test(expected = FailedToCreateCustomerException.class)
	public void testInvalidCreateNewCustomer() throws SQLException, FailedToCreateCustomerException {
		
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			customerService.createNewCustomer("joesmith", null, null, null);
		}
	}
}
