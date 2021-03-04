package com.revature.ui;

import java.sql.SQLException;

import com.revature.exceptions.FailedToCreatePendingAccountException;
import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.services.AccountService;

public class ApplyForAccount implements Menu {

	EmployeeLogIn eli = new EmployeeLogIn();
	Customer customer = new Customer();
	Account account = new Account();
	public AccountService accountService;

	public ApplyForAccount() {
	}

	public ApplyForAccount(Customer customer) {
		this.customer = customer;
		this.account = new Account();
		this.accountService = new AccountService();
	}

	@Override
	public void displayApp() {

		int verifyId = customer.getCustomerId();
		int accountHolderNo = verifyId;

		System.out.println("Please enter your customer ID for verification: ");
		int id = 0;
		try {
			id = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			e.getMessage();
		}

		if (id == verifyId) {
			System.out.println("Enter your desired account balance: ");
		
			double ab = 0;
			try {
				ab = Double.parseDouble(sc.nextLine());
			}catch(NumberFormatException e) {
				e.getMessage();
			}
			
			System.out.println("Enter the desired name of your account: ");
			String accountName = sc.nextLine().trim();
			
			boolean isPending = true;

			account.setAccountBalance(ab);
			account.setAccountHolderNo(accountHolderNo);
			account.setAccountName(accountName);

			account = new Account(ab, accountHolderNo, accountName, isPending);

			if (account != null) {
				System.out.println("Can you confirm the details of your new Account below?");
				System.out.println(account.toStringApply());

				int choice = 0;
				boolean isSuccess = true;

				do {

					System.out.println("\nAre these credentials correct?\n Type 1 for Yes and 2 for No");

					try {
						choice = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
					}

					switch (choice) {
					case 1:
						try {
							accountService.applyForNewAccount(ab, accountName, accountHolderNo, isPending);
							System.out.println(
									"Thank you. If your account is approved, it will show up on your View All Accounts page. "
											+ "You will now be redirected to the Customer homepage");
							CustomerLogIn cli = new CustomerLogIn();
							cli.accountDisplay(customer);
						} catch (SQLException | FailedToCreatePendingAccountException e1) {
							e1.getMessage();
						}
						isSuccess = false;
						break;
					case 2:
						System.out.println("Please try recreating your account");
						displayApp();
						isSuccess = false;
						break;
					default:
						System.out.println("Invalid input, please try again");
						isSuccess = true;
					}
				} while (isSuccess == true);
			}
		} else {
			System.out.println("Incorrect account ID, please try again.");
			displayApp();
		}
	}
}
