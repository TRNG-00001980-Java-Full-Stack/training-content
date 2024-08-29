package service;

import dao.CustomerDAO;
import entity.Customer;
import exception.InsufficientBalanceException;
import exception.InvalidInputException;
import exception.UserNotFoundException;

public class BankingService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public void registerCustomer(Customer customer) throws InvalidInputException {
        if (customer.getName().isEmpty() || customer.getEmail().isEmpty() || customer.getPassword().isEmpty()) {
            throw new InvalidInputException("All fields are required.");
        }
        customerDAO.addCustomer(customer);
    }

    public Customer loginCustomer(String email, String password) throws UserNotFoundException {
        Customer customer = customerDAO.getCustomerByEmail(email);
        if (customer == null || !customer.getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid email or password.");
        }
        return customer;
    }

    public void depositMoney(int customerId, double amount) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Invalid deposit amount.");
        }
        customerDAO.updateBalance(customerId, amount);
    }

    public void withdrawMoney(int customerId, double amount) throws InsufficientBalanceException {
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }
        customerDAO.updateBalance(customerId, -amount);
    }

    public void transferMoney(int senderId, int receiverId, double amount) throws InsufficientBalanceException {
        Customer sender = customerDAO.getCustomerById(senderId);
        Customer receiver = customerDAO.getCustomerById(receiverId);

        if (sender.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        customerDAO.updateBalance(senderId, -amount);
        customerDAO.updateBalance(receiverId, amount);
    }

    public double viewBalance(int customerId) throws UserNotFoundException {
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            throw new UserNotFoundException("Customer not found.");
        }
        return customer.getBalance();
    }
    }
