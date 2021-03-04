package com.revature.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.exceptions.NoPendingAccountsException;
import com.revature.exceptions.NoTransactionsFoundException;
import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.Employee;
import com.revature.model.Transaction;
import com.revature.services.AccountService;
import com.revature.services.EmployeeService;

public class EmployeeLogIn implements Menu {

	public EmployeeService employeeService;
	public AccountService accountService;
	Customer customer;
	Employee employee;
	List<Account> customerAccounts;
	List<Account> pendingAccounts;
	List<Transaction> allTransactions;
	boolean isPending = true;
	boolean isSuccess = false;

	public EmployeeLogIn() {
		this.employeeService = new EmployeeService();
		this.accountService = new AccountService();
		this.customer = new Customer();
		this.employee = new Employee();
		this.customerAccounts = new ArrayList<>();
		this.pendingAccounts = new ArrayList<>();

	}

	public void displayApp() {
		System.out.println("Username: ");
		String un = sc.nextLine();
		System.out.println("Password: ");
		String pw = sc.nextLine();
		try {

			if (employeeService.eLogInVerification(un, pw) == true) {
				employee = employeeService.getEmployeeByUsername(un);
				employeeAccountMenuDisplay(employee);
			}
		} catch (SQLException | EmployeeNotFoundException e) {
			System.out.println(e.getMessage());
			displayApp();
		}

	}

	public void employeeAccountMenuDisplay(Employee employee) {

		do {
			System.out.println("\n=== Welcome to the Employee Homepage, " + employee.geteFirstName() + " ===\n");
			System.out.println("Where would you like to go next?");
			System.out.println("1. View Pending Accounts");
			System.out.println("2. View Accounts by Customer ID");
			System.out.println("3. View All Transactions");
			System.out.println("4. Exit Application");

			int choice = 0;

			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}

			switch (choice) {
			case 1:
				try {
					pendingAccounts = employeeService.viewAllPendingAccounts(isPending);
					Collections.sort(pendingAccounts, new SortByAccountId());
					for (Account pa : pendingAccounts) {
						System.out.println(pa.toString());
					}
					// CustomerId
					System.out.println("Type in the customer ID for the account you would like to confirm / deny: ");
					int customerId = 0;
					try {
						customerId = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}

					// AccountId
					System.out.println("Type in the account ID for the account you would like to confirm / deny:");
					int accountId = 0;
					try {
						accountId = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}

					confirmAccount(customerId, accountId);

					System.out.println("Type 1 to return to the last menu, Type 2 to exit the application");
					int eDecision = 0;
					try {
						eDecision = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}

					if (eDecision == 1) {
						isSuccess = false;
					} else if (eDecision == 2) {
						System.out.println("Thank you for visiting! Have a good day.");
						isSuccess = true;
					} else {
						System.out.println("Invalid input. Please try again");
						isSuccess = false;
					}

				} catch (SQLException | NoPendingAccountsException e) {
					System.out.println(e.getMessage());
					isSuccess = false;
				}
				break;
			case 2:
				System.out.println("Type in the customer ID to select all accounts belonging to a customer:");
				int selectedCustomerId = 0;
				try {
					selectedCustomerId = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
				}

				viewAllCustomerAccounts(selectedCustomerId);

				System.out.println("Type 1 to return to the last menu, Type 2 to exit the application");
				int eDecision2 = 0;
				try {
					eDecision2 = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
				}

				if (eDecision2 == 1) {
					isSuccess = false;
				} else if (eDecision2 == 2) {
					System.out.println("Thank you for visiting! Have a good day.");
					isSuccess = true;
				} else {
					System.out.println("Invalid input. Please try again");
					isSuccess = false;
				}
				break;
			case 3:
				System.out.println("Here is a list of all transactions: ");
				try {
					allTransactions = employeeService.viewAllTransactions();
					Collections.sort(allTransactions, new SortByTransactionId());
					for (Transaction t : allTransactions) {
						System.out.println(t.toString());
					}

					System.out.println("Type 1 to return to the last menu, Type 2 to exit the application");
					int eDecision3 = 0;
					try {
						eDecision3 = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());

						if (eDecision3 == 1) {
							isSuccess = false;
						} else if (eDecision3 == 2) {
							System.out.println("Thank you for visiting! Have a good day.");
							isSuccess = true;
							System.exit(0);
							break;
						} else {
							System.out.println("Invalid input. Please try again");
							isSuccess = false;
						}
					}
				} catch (SQLException | NoTransactionsFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("Thank you for visiting! Have a good day.");
				isSuccess = true;
				System.exit(0);
				break;
			}
		} while (isSuccess == false);

	}

	public void confirmAccount(int customerId, int accountId) {
		boolean isPendingConfirm = false;
		boolean isPendingDeny = true;
		boolean retryMethod = false;
		System.out.println("This is the account for customer with ID: " + customerId);

		try {
			System.out.println(employeeService.getAccountByCustomerIDandPassword(customerId, accountId, isPending));
		} catch (SQLException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Type (1) to confirm account");
		System.out.println("Type (2) to deny account");

		do {
			int choice = 0;
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}

			if (choice == 1) {
				try {
					employeeService.confirmPendingAccount(customerId, accountId, isPendingConfirm);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				retryMethod = true;
			} else if (choice == 2) {

				try {
					employeeService.removePendingAccount(customerId, accountId, isPendingDeny);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				retryMethod = true;
			} else {
				System.out.println("Invalid input. Please try again");
				retryMethod = false;
			}
		} while (retryMethod == false);
	}

	public void viewAllCustomerAccounts(int selectedCustomerId) {
		isPending = false;
		try {
			customerAccounts = accountService.getAllAccountsByCustomerId(selectedCustomerId, isPending);
			Collections.sort(customerAccounts, new SortByAccountId());
			for (Account a : customerAccounts) {
				System.out.println(a.toString());
			}

		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
	}
}
