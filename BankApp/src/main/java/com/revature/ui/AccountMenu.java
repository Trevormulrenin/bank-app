package com.revature.ui;

import java.sql.SQLException;

import com.revature.exceptions.InvalidDepositException;
import com.revature.exceptions.InvalidWithdrawalException;
import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.services.AccountService;

public class AccountMenu implements Menu {

	public Customer customer = new Customer();
	public Account account = new Account();
	public AccountService accountService;
	boolean isPending = false;
	boolean isSuccess = true;

	public AccountMenu() {
	}

	public AccountMenu(Account account, Customer customer) {
		this.account = account;
		this.customer = customer;
		this.accountService = new AccountService();
	}

	public void displayApp() {

		int inputId = 0;
		int verifyId = customer.getCustomerId();

		System.out.println(
				"\n=== Welcome to your " + account.getAccountName() + " account, " + customer.getcFirstName() + " ===");
		System.out.println("Please enter your customer ID below for confirmation: ");
		
		inputId = 0;

		try {
			inputId = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			e.getMessage();
		}

		do {
			if (inputId == verifyId) {
				System.out.println("Verification successful.\n");
				System.out.println("Where would you like to go next?");
				System.out.println("1. View Account Balance");
				System.out.println("2. Withdraw / Deposit Funds");
				System.out.println("3. Transaction Menu");
				System.out.println("4. Select a different account");
				System.out.println("5. Back to Main Menu");
				System.out.println("6. Logout");

				int choice = 0;

				try {
					choice = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					e.getMessage();
				}

				switch (choice) {
				case 1:
					System.out.println("Your balance for account " + account.getAccountName() + " is: "
							+ account.getAccountBalance());
					System.out.println("Press 0 to return to the Account Menu");
					
					int returnChoice = 0;
					try {
						returnChoice = Integer.parseInt(sc.nextLine());
					}catch(NumberFormatException e) {
						e.getMessage();
					}

					if (returnChoice == 0) {
						isSuccess = true;
					}
					break;
				case 2:
					withdrawAndDepositMenu(account);
					System.out.println("Press 0 to return to Account Menu");
					int returnChoice2 = 0;
					try {
						returnChoice2 = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						e.getMessage();
					}

					if (returnChoice2 == 0) {
						isSuccess = true;
					}
					break;
				case 3:
					TransactionMenu tm = new TransactionMenu(account, customer);
					tm.displayApp();
					isSuccess = false;
					break;
				case 4:
					CustomerLogIn cli = new CustomerLogIn(customer);
					cli.viewAllAccounts(customer);
					isSuccess = false;
					break;
				case 5:
					CustomerLogIn cli2 = new CustomerLogIn(customer);
					cli2.accountDisplay(customer);
					isSuccess = false;
					break;
				case 6:
					System.out.println("Thank you for visiting! Have a good day.");
					isSuccess = false;
					break;
				default:
					System.out.println("Invalid input. Please try again");
					continue;
				}

			} else {
				System.out.println("Incorrect ID, please try again");
				displayApp();
			}
		} while (isSuccess == true);
	}

	public void withdrawAndDepositMenu(Account account) {
		System.out.println("Type 1 to withdraw, Type 2 to deposit");
		int wd = 0;

		try {
			wd = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			e.getMessage();
		}

		switch (wd) {
		case 1:
			System.out.println("How much would you like to withdraw?");
			double withdraw = 0;
			try {
				withdraw = Double.parseDouble(sc.nextLine());
			} catch (NumberFormatException e) {
				e.getMessage();
			}
			double newBalanceAfterWithdrawal = account.getAccountBalance() - withdraw;

			try {
				accountService.withdrawFromAccount(account.getAccountId(), withdraw, isPending, account);
				System.out.println("Your updated balance is: " + newBalanceAfterWithdrawal);

			} catch (SQLException | InvalidWithdrawalException e) {
				e.getMessage();
			}
			break;
		case 2:
			System.out.println("How much would you like to deposit?");
			double deposit = 0;
			try {
				deposit = Double.parseDouble(sc.nextLine());
			} catch (NumberFormatException e) {
				e.getMessage();
			}

			double newBalanceAfterDeposit = account.getAccountBalance() + deposit;

			try {
				accountService.depositToAccount(account.getAccountId(), deposit, isPending, account);
				System.out.println("Your updated balance is: " + newBalanceAfterDeposit);
			} catch (SQLException | InvalidDepositException e) {
				e.getMessage();
			}
		}
	}
}
