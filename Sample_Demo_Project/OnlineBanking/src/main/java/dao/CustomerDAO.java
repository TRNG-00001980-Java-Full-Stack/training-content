package dao;

import entity.Customer;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    private Connection connection;

    public CustomerDAO() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addCustomer(Customer customer) {
        try {
            String query = "INSERT INTO customer (name, email, password, balance) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            ps.setDouble(4, customer.getBalance());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerByEmail(String email) {
        Customer customer = null;
        try {
            String query = "SELECT * FROM customer WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void updateCustomer(Customer customer) {
        try {
            String query = "UPDATE customer SET name = ?, email = ?, password = ?, balance = ? WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            ps.setDouble(4, customer.getBalance());
            ps.setString(5, customer.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
