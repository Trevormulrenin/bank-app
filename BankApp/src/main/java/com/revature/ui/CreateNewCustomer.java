package com.revature.ui;

import java.sql.SQLException;

import com.revature.exceptions.FailedToCreateCustomerException;
import com.revature.model.Customer;
import com.revature.services.CustomerService;

public class CreateNewCustomer implements Menu {
	
	String un;
	String pw;
	String fn;
	String ln;
	
	Customer customer = new Customer();

	public CustomerService customerService;

	public CreateNewCustomer() {
		this.customerService = new CustomerService();
	}

	public void displayApp() {

		System.out.println("Please enter the following credentials: \n");

		System.out.println("Username: ");
		un = sc.nextLine();
		customer.setcUsername(un);

		System.out.println("Password: ");
		pw = sc.nextLine();
		customer.setcPassword(pw);

		System.out.println("First name: ");
		fn = sc.nextLine();
		customer.setcFirstName(fn);

		System.out.println("Last name: ");
		ln = sc.nextLine();
		customer.setcLastname(ln);

	if (customer != null) {
			try {
				confirmCustomer();
			} catch (FailedToCreateCustomerException e) {
				e.getMessage();
			}
		}
	}

	public void confirmCustomer() throws FailedToCreateCustomerException {
	

		System.out.println("Can you confirm the details below?\n");
		System.out.println(customer.toString());
		System.out.println("\nAre these credentials correct?\n Type Y for Yes and N for No");
		String createVerification = sc.nextLine().trim();

		if (createVerification.equals("Y") || createVerification.equals("y")) {
			try {
				customerService.createNewCustomer(un, pw, fn, ln);
			} catch (SQLException | FailedToCreateCustomerException e) {
				e.getMessage();
			}
			System.out.println("Thank you. Please try logging in to view your homepage");
			CustomerLogIn cli = new CustomerLogIn();
			cli.displayApp();
		} else if (createVerification.equals("N") || createVerification.equals("n")) {
			System.out.println("Please try recreating your account");
			customer = null;
			displayApp();
		} else {
			throw new FailedToCreateCustomerException("Invalid input, please try again");
		}
	
	}
}
