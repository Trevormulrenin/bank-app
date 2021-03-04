package com.revature.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.services.AccountService;
import com.revature.services.CustomerService;

public class CustomerLogIn implements Menu {

	// WHEN I RECALL THE VIEWALLACCOUNTS METHOD, IT WONT LET ME PUT IN USER INPUT A
	// SECOND TIME, ASK JAKE

	public CustomerService customerService;
	public AccountService accountService;
	boolean isSuccess = false;
	List<Account> accounts;
	Account account;
	Customer customer;
	boolean isPending = false;

	public CustomerLogIn() {
		this.customerService = new CustomerService();
		this.accountService = new AccountService();
		this.accounts = new ArrayList<>();
		this.customer = new Customer();
		this.account = new Account();
	}

	public CustomerLogIn(Customer customer) {
		this.customer = customer;
		this.customerService = new CustomerService();
		this.accountService = new AccountService();
		this.accounts = new ArrayList<>();
		this.account = new Account();
	}

	public void displayApp() {
		System.out.println("Username: ");
		String un = sc.nextLine();
		System.out.println("Password: ");
		String pw = sc.nextLine();
		try {
			if (customerService.logInVerification(un, pw) == true) {
				customer = customerService.getCustomerByUsername(un);
				accountDisplay(customer);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	public void accountDisplay(Customer customer) {
		System.out.println("Welcome " + customer.getcFirstName() + "!\n");
		System.out.println("Select an option from the navigation menu");
		System.out.println("1. View all accounts");
		System.out.println("2. Apply for new bank account");
		System.out.println("3. Exit application");

		int choice = 0;

		try {
			choice = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e2) {
		}

		switch (choice) {
		case 1:
			viewAllAccounts(customer);
			break;
		case 2:
			ApplyForAccount afa = new ApplyForAccount(customer);
			afa.displayApp();
			break;
		case 3:
			System.out.println("Thank you for visiting. Come back soon!");
			break;
		default:
			System.out.println("Invalid input. Please try again");
			break;
		}
	}

	public void viewAllAccounts(Customer customer) {
		do {
			try {
				accounts = accountService.getAllAccountsByCustomerId(customer.getCustomerId(), isPending);
				Collections.sort(accounts, new SortByAccountId());
				for (Account a : accounts) {
					System.out.println(a.toString());
				}

				System.out.println(
						"\nAll of your accounts are listed above. Please type in the account ID of the account you would like to view."
						+ "\nIf there are no current accounts, press 0 to return to Customer Menu");

				int accountChoice = 0;


				try {
					accountChoice = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					e.getMessage();
				}
				
				if(accountChoice == 0) {
					accountDisplay(customer);
				}

				try {
					account = accountService.getAccountByCustomerAndAccountId(customer.getCustomerId(), accountChoice, isPending);
					System.out.println("\n" + account);
				} catch (SQLException e) {
					e.getMessage();
				}

				System.out.println("\nIs this the account you would like to view? Type 1 for Yes, Type 2 for No");
				String confirmView = sc.nextLine();
				int cView = Integer.valueOf(confirmView);

				if (cView == 1) {
					System.out.println("You are being redirected to your account page.");
					AccountMenu am = new AccountMenu(account, customer);
					am.displayApp();
					isSuccess = true;
				} else if (cView == 2) {
					viewAllAccounts(customer);
					isSuccess = true;
				} else {
					System.out.println("Invalid input, please try again");
					isSuccess = false;
				}

			} catch (SQLException e) {
				e.getMessage();
			}
		} while (isSuccess = false);
	}
}
