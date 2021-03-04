package com.revature.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.dao.CustomerDAO;
import com.revature.dao.CustomerDAOImpl;
import com.revature.exceptions.FailedToCreateCustomerException;
import com.revature.model.Customer;
import com.revature.ui.CustomerLogIn;
import com.revature.util.ConnectionUtil;

public class CustomerService {

	public CustomerDAO customerDAO;
	private static Logger log = Logger.getLogger(CustomerService.class);

	public CustomerService() {
		this.customerDAO = new CustomerDAOImpl();
	}

	public CustomerService(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public void createNewCustomer(String cUsername, String cPassword, String cFirstName, String cLastName)
			throws SQLException, FailedToCreateCustomerException {

		try (Connection con = ConnectionUtil.getConnection()) {

			customerDAO.createNewCustomer(cUsername, cPassword, cFirstName, cLastName, con);

			if (cUsername == null || cPassword == null || cFirstName == null || cLastName == null) {
				throw new FailedToCreateCustomerException("Failed to create account, try again");
			} else {
				log.info("Log: Customer created successfully");
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
	}

	public boolean logInVerification(String cUsername, String cPassword) throws SQLException {

		boolean isSuccess = true;

		try (Connection con = ConnectionUtil.getConnection()) {
			isSuccess = customerDAO.logInVerification(cUsername, cPassword, con);

			if (isSuccess != true) {
				System.out.println("Incorrect username or password. Please try again");
				CustomerLogIn cli = new CustomerLogIn();
				cli.displayApp();
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}

	public Customer getCustomerByUsername(String cUsername) throws SQLException {

		Customer customer = new Customer();

		try (Connection con = ConnectionUtil.getConnection()) {

			customer = customerDAO.getCustomerByUsername(cUsername, con);

		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return customer;
	}
}