package com.revature.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exceptions.NoCurrentPendingTransactionsException;
import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.Transaction;
import com.revature.services.EmployeeService;
import com.revature.services.TransactionService;

public class TransactionMenu implements Menu {
	public TransactionService transactionService;
	Account account;
	Customer customer;
	Transaction transaction;
	List<Transaction> pendingTransactions;
	boolean isPendingTransaction = true;
	private static Logger log = Logger.getLogger(EmployeeService.class);

	public TransactionMenu() {
	}

	public TransactionMenu(Account account, Customer customer) {
		this.transactionService = new TransactionService();
		this.pendingTransactions = new ArrayList<>();
		this.account = account;
		this.customer = customer;
		this.transaction = new Transaction();
	}

	@Override
	public void displayApp() {

		System.out
				.println("\n=== Welcome to the Transaction Menu for account : " + account.getAccountName() + " ===\n");

		System.out.println("Where would you like to go next?");
		System.out.println("1. View all incoming transactions");
		System.out.println("2. Make a transfer to another account");
		System.out.println("3. Back to Account Menu");
		System.out.println("4. Log out");

		int choice = 0;

		try {
			choice = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e2) {
		}

		switch (choice) {
		case 1:
			System.out.println("Here is a list of all incoming transactions: \n");

			try {
				try {
					pendingTransactions = transactionService.getAllIncomingTransactionsByCustomerAndAccountId(
							customer.getCustomerId(), account.getAccountId(), isPendingTransaction);
				} catch (NoCurrentPendingTransactionsException e) {
					e.getMessage();
				}
			} catch (SQLException e) {
				e.getMessage();
			}
			for (Transaction t : pendingTransactions) {
				System.out.println(t.toString());
			}
			System.out.println("\nType in the transaction id for the transaction you would like to confirm / deny"
					+ ", or type 0 if you would like to return to your Account Menu ");
			int transactionId = 0;

			try {
				transactionId = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
			}

			if (transactionId == 0) {
				displayApp();
				break;
			} else {
				try {
					transaction = transactionService.getTransactionByTransactionId(transactionId,
							customer.getCustomerId(), account.getAccountId());
					System.out.println(transaction);
					confirmTransaction(transaction);
				} catch (SQLException e1) {
					e1.getMessage();
					displayApp();
				}
			}
			break;
		case 2:
			System.out.println("Please confirm your customer and account ID before you transfer funds: ");

			// Sender Customer
			System.out.println("Your Customer ID: ");
			int accountSenderCustomerId = 0;
			try {
				accountSenderCustomerId = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e2) {
				e2.getMessage();
				displayApp();
			}
			// Sender Account
			System.out.println("Your Account ID: ");
			int accountSenderAccountId = 0;
			try {
				accountSenderAccountId = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e2) {
				e2.getMessage();
				displayApp();
			}

			if (accountSenderCustomerId != account.getAccountHolderNo()
					|| accountSenderAccountId != account.getAccountId()) {
				System.out.println("\nIncorrect customer or account ID. Please try again.\n");
				log.info("Invalid verification from customer with ID: " + customer.getCustomerId() + " and account: "
						+ account.getAccountId());

				displayApp();
				break;
			} else {
				log.info("\nVerification successful from customer with ID: " + customer.getCustomerId() + " and account: "
						+ account.getAccountId());
				System.out.println(
						"\nPlease type in the customer and account ID for the account you wish to transfer to: \n");

				// Receiver Customer
				System.out.println("Recipient Customer ID: ");
				int accountReceiverCustomerId = 0;
				try {
					accountReceiverCustomerId = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					e.getMessage();
					displayApp();
				}
				// Receiver Account
				System.out.println("Recipient Account ID: ");
				int accountReceiverAccountId = 0;
				try {
					accountReceiverAccountId = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					e.getMessage();
					displayApp();
				}
				// Transaction Amount
				System.out.println("Please provide the transaction amount: ");
				double transactionAmount = 0;
				try {
					transactionAmount = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					e.getMessage();
					displayApp();
				}

				if (transactionAmount > account.getAccountBalance()) {
					System.out.println("Invalid funds, please try again");
					displayApp();
					break;
				} else {
					try {
						transactionService.createNewTransaction(accountSenderCustomerId, accountSenderAccountId,
								accountReceiverCustomerId, accountReceiverAccountId, transactionAmount,
								isPendingTransaction);
						System.out.println("\nPending transaction sent. Waiting on confirmation from the recipient");
						System.out.println("\nReturning to Transaction Menu...\n");
						displayApp();
						break;
					} catch (SQLException e) {
						e.getMessage();
						displayApp();
					}
				}
			}
		case 3:
			AccountMenu am = new AccountMenu(account, customer);
			am.displayApp();
			break;
		case 4:
			System.out.println("\nThank you for visiting! Have a nice day\n");
			break;
		default:
			System.out.println("\nInvalid input. Please try again\n");
			displayApp();
			break;
		}
	}

	private void confirmTransaction(Transaction transaction) {
		isPendingTransaction = false;
		System.out.println("Type 1 to confirm transaction, type 2 to deny transaction");

		int choice = 0;

		try {
			choice = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			e.getMessage();
			displayApp();
		}

		if (choice == 1) {
			try {
				transactionService.executeNewTransaction(transaction.getTransactionAmount(),
						transaction.getAccountSenderAccountId(), transaction.getAccountSenderCustomerId(),
						transaction.getAccountReceiverAccountId(), transaction.getAccountReceiverCustomerId(),
						isPendingTransaction);
				System.out.println("\nTransaction successfully confirmed\n");
				displayApp();
			} catch (SQLException e) {
				e.getMessage();
				displayApp();
			}
		} else if (choice == 2) {
			try {
				transactionService.denyNewTransaction(transaction.getTransactionId());
				System.out.println("\nTransaction successfully denied\n");
				displayApp();
			} catch (SQLException e) {
				e.getMessage();
				displayApp();
			}
		} else {
			System.out.println("\nInvalid input, please try again\n");
			confirmTransaction(transaction);
		}

	}

}
