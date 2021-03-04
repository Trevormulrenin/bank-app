package com.revature.ui;

public class MainMenu implements Menu {

	public void displayApp() {
		System.out.println("=== Welcome to the Banking Application ===\n");

		int choice = 0;

		System.out.println("Where would you like to go next?");
		System.out.println("1. Customer Log-in");
		System.out.println("2. Employee Log-in");
		System.out.println("3. Create an account");
		System.out.println("4. Exit application");

		try {
			choice = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		switch (choice) {
		case 1:
			CustomerLogIn cl = new CustomerLogIn();
			cl.displayApp();
			break;
		case 2:
			EmployeeLogIn el = new EmployeeLogIn();
			el.displayApp();
			break;
		case 3:
			CreateNewCustomer caa = new CreateNewCustomer();
			caa.displayApp();
			break;
		case 4:
			System.out.println("Thank you for visiting. Come back soon!");
			break;
		default:
			System.out.println("No valid choice entered, please try again");
			displayApp();
		}
	}
}
