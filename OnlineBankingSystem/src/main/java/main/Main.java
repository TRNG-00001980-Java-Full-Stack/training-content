package main;

import entity.Customer;
import model.Admin;
import service.AdminService;
import service.BankingService;
import util.InputUtil;
import util.PasswordUtil;
import exception.InsufficientBalanceException;
import exception.InvalidInputException;
import exception.UserNotFoundException;

public class Main {
    public static void main(String[] args) {
        AdminService adminService = new AdminService();
        BankingService bankingService = new BankingService();
        boolean running = true;

        while (running) {
            System.out.println("1. Admin Login\n2. Customer Register\n3. Customer Login\n4. Exit");
            int choice = InputUtil.getInt("Choose an option: ");

            switch (choice) {
                case 1:
                    handleAdminLogin(adminService);
                    break;
                case 2:
                    handleCustomerRegistration(bankingService);
                    break;
                case 3:
                    handleCustomerLogin(bankingService);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleAdminLogin(AdminService adminService) {
        String username = InputUtil.getString("Enter username: ");
        String password = InputUtil.getString("Enter password: ");

        try {
            Admin admin = adminService.loginAdmin(username, PasswordUtil.hashPassword(password));
            System.out.println("Welcome, " + admin.getUsername() + "!");
            // Handle further admin operations
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleCustomerRegistration(BankingService bankingService) {
        String name = InputUtil.getString("Enter name: ");
        String email = InputUtil.getString("Enter email: ");
        String password = InputUtil.getString("Enter password: ");
        double initialDeposit = InputUtil.getDouble("Enter initial deposit: ");

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(PasswordUtil.hashPassword(password));
        customer.setBalance(initialDeposit);

        try {
            bankingService.registerCustomer(customer);
            System.out.println("Registration successful. You can now log in.");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleCustomerLogin(BankingService bankingService) {
        String email = InputUtil.getString("Enter email: ");
        String password = InputUtil.getString("Enter password: ");

        try {
            Customer customer = bankingService.loginCustomer(email, PasswordUtil.hashPassword(password));
            System.out.println("Welcome, " + customer.getName() + "!");
            handleCustomerOperations(bankingService, customer.getCustomerId());
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleCustomerOperations(BankingService bankingService, int customerId) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("1. View Balance\n2. Deposit Money\n3. Withdraw Money\n4. Transfer Money\n5. Logout");
            int choice = InputUtil.getInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        double balance = bankingService.viewBalance(customerId);
                        System.out.println("Your balance: " + balance);
                        break;
                    case 2:
                        double depositAmount = InputUtil.getDouble("Enter deposit amount: ");
                        bankingService.depositMoney(customerId, depositAmount);
                        System.out.println("Deposit successful.");
                        break;
                    case 3:
                        double withdrawAmount = InputUtil.getDouble("Enter withdraw amount: ");
                        bankingService.withdrawMoney(customerId, withdrawAmount);
                        System.out.println("Withdrawal successful.");
                        break;
                    case 4:
                        int receiverId = InputUtil.getInt("Enter receiver customer ID: ");
                        double transferAmount = InputUtil.getDouble("Enter transfer amount: ");
                        bankingService.transferMoney(customerId, receiverId, transferAmount);
                        System.out.println("Transfer successful.");
                        break;
                    case 5:
                        loggedIn = false;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InvalidInputException | InsufficientBalanceException | UserNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
