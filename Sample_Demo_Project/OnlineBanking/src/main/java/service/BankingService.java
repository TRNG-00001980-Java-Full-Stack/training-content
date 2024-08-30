package service;

import dao.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;
import exception.UserNotFoundException;

public class BankingService {

    private CustomerDAO customerDAO;

    public BankingService() {
        customerDAO = new CustomerDAO();
    }

    public void registerCustomer(Customer customer2) {
        Customer customer = new Customer(customer2.getName(), customer2.getEmail(), customer2.getPassword(), customer2.getBalance());
        customerDAO.addCustomer(customer);
    }

    public CustomerDTO login(String email, String password) throws UserNotFoundException {
    	//System.out.println(password);
        Customer customer = customerDAO.getCustomerByEmail(email);
        if (customer == null || !customer.getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid email or password.");
        }
        return new CustomerDTO(customer.getName(), customer.getEmail(), customer.getBalance());
    }

    public double viewBalance(CustomerDTO customerDTO) {
        Customer customer = customerDAO.getCustomerByEmail(customerDTO.getEmail());
        return customer.getBalance();
    }

    public void deposit(CustomerDTO customerDTO, double amount) {
        Customer customer = customerDAO.getCustomerByEmail(customerDTO.getEmail());
        customer.setBalance(customer.getBalance() + amount);
        customerDAO.updateCustomer(customer);
    }

    public boolean withdraw(CustomerDTO customerDTO, double amount) {
        Customer customer = customerDAO.getCustomerByEmail(customerDTO.getEmail());
        if (amount > customer.getBalance()) {
            return false;
        }
        customer.setBalance(customer.getBalance() - amount);
        customerDAO.updateCustomer(customer);
        return true;
    }

    public boolean transfer(CustomerDTO customerDTO, String recipientEmail, double amount) {
        Customer sender = customerDAO.getCustomerByEmail(customerDTO.getEmail());
        Customer recipient = customerDAO.getCustomerByEmail(recipientEmail);
        if (recipient != null && withdraw(customerDTO, amount)) {
            recipient.setBalance(recipient.getBalance() + amount);
            customerDAO.updateCustomer(recipient);
            return true;
        }
        return false;
    }

	
}
