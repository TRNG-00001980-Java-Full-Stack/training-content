package Main;

import dto.CustomerDTO;
import entity.Customer;
import exception.UserNotFoundException;
import service.BankingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingService bankingService = new BankingService();

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = scanner.next();
                    System.out.print("Enter your email: ");
                    String email = scanner.next();
                    System.out.print("Enter your password: ");
                    String password = scanner.next();
                    Customer customer = new Customer(name, email, password, 0);
                    bankingService.registerCustomer(customer);
                    System.out.println("Registration successful!");
                    break;

                case 2:
                    System.out.print("Enter your email: ");
                    String loginEmail = scanner.next();
                    System.out.print("Enter your password: ");
                    String loginPassword = scanner.next();

                    try {
                        CustomerDTO loggedInCustomer = bankingService.login(loginEmail, loginPassword);
                        System.out.println("Login successful!");
                        loggedInMenu(loggedInCustomer, bankingService, scanner);
                    } catch (UserNotFoundException e) {
                        System.out.println("Login failed: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loggedInMenu(CustomerDTO customerDTO, BankingService bankingService, Scanner scanner) {
        while (true) {
            System.out.println("\nWelcome, " + customerDTO.getName());
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Your balance is: " + customerDTO.getBalance());
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    bankingService.deposit(customerDTO, depositAmount);
                    System.out.println("Deposit successful!");
                    // Update balance from service
                    customerDTO.setBalance(bankingService.viewBalance(customerDTO));
                    System.out.println("New balance: " + customerDTO.getBalance());
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    boolean withdrawSuccess = bankingService.withdraw(customerDTO, withdrawAmount);
                    if (withdrawSuccess) {
                        System.out.println("Withdrawal successful!");
                        // Update balance from service
                        customerDTO.setBalance(bankingService.viewBalance(customerDTO));
                        System.out.println("New balance: " + customerDTO.getBalance());
                    } else {
                        System.out.println("Insufficient funds.");
                    }
                    break;

                case 4:
                    System.out.print("Enter recipient's email: ");
                    String recipientEmail = scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    boolean transferSuccess = bankingService.transfer(customerDTO, recipientEmail, transferAmount);
                    if (transferSuccess) {
                        System.out.println("Transfer successful!");
                        // Update balance from service
                        customerDTO.setBalance(bankingService.viewBalance(customerDTO));
                        System.out.println("New balance: " + customerDTO.getBalance());
                    } else {
                        System.out.println("Transfer failed.");
                    }
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
