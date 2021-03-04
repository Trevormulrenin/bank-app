package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.model.Customer;

public interface CustomerDAO {
	
	public void createNewCustomer(String cUsername, String cPassword, String cFirstName, String cLastName, Connection con) throws SQLException;
	
	public boolean logInVerification(String cUsername, String cPassword, Connection connection) throws SQLException;
	
	public Customer getCustomerByUsername(String cUsername, Connection con) throws SQLException;

}
