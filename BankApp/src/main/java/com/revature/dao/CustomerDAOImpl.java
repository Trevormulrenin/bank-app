package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	public void createNewCustomer(String cUsername, String cPassword, String cFirstName, String cLastName,
			Connection con) throws SQLException {
		String sql = "INSERT INTO bank_app.customers(customer_username, customer_password, customer_first_name, customer_last_name)"
				+ " VALUES(?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, cUsername);
		pstmt.setString(2, cPassword);
		pstmt.setString(3, cFirstName);
		pstmt.setString(4, cLastName);

		ResultSet rs = pstmt.executeQuery();

		Customer customer = new Customer();
		
		while (rs.next()) {
			customer.setcUsername("customer_username");
			customer.setcPassword("customer_password");
			customer.setcFirstName("customer_first_name");
			customer.setcLastname("customer_last_name");	
		}
	}

	public boolean logInVerification(String cUsername, String cPassword, Connection con) throws SQLException {

		boolean isSuccess = false;

		String password = "SELECT customer_password FROM bank_app.customers WHERE customer_username = ?";
		PreparedStatement pstmt = con.prepareStatement(password);

		pstmt.setString(1, cUsername);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			password = rs.getString("customer_password");
			if (cPassword.equals(password)) {
				isSuccess = true;
			} else {
				isSuccess = false;
			}
		}
		return isSuccess;
	}

	public Customer getCustomerByUsername(String cUsername, Connection con) throws SQLException {
		Customer customer = null;

		String sql = "SELECT * FROM bank_app.customers WHERE customer_username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, cUsername);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int cId = rs.getInt("customer_id");
			String username = rs.getString("customer_username");
			String cFirstName = rs.getString("customer_first_name");
			String cLastName = rs.getString("customer_last_name");
			String cPassword = rs.getString("customer_password");

			customer = new Customer(cId, username, cPassword, cFirstName, cLastName);
		}

		return customer;
	}
}
