package databases.B_daoPattern.persistence;

import databases.B_daoPattern.business.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public Customer getById(int id) {
        Customer customer = null;

        // Create variables to hold database details
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers where customerNumber = ?")) {
                    // Fill in the blanks, i.e. parameterize the query
                    ps.setInt(1, id);

                    // TRY to execute the query
                    try (ResultSet rs = ps.executeQuery()) {
                        // Extract the information from the result set
                        if (rs.next()) {
                            // Get the pieces of a customer from the resultset and create a new Customer
                            customer = new Customer(
                                    rs.getInt("customerNumber"),
                                    rs.getString("customerName"),
                                    rs.getString("contactLastName"),
                                    rs.getString("contactFirstName"),
                                    rs.getString("phone"),
                                    rs.getString("addressLine1"),
                                    rs.getString("addressLine2"),
                                    rs.getString("city"),
                                    rs.getString("state"),
                                    rs.getString("postalCode"),
                                    rs.getString("country"),
                                    rs.getInt("salesRepEmployeeNumber"),
                                    rs.getDouble("creditLimit")
                            );
                        }
                    } catch (SQLException e) {
                        System.out.println("SQL Exception occurred when executing SQL or processing results.");
                        System.out.println("Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception occurred when attempting to prepare SQL for execution");
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred when attempting to connect to database.");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred when trying to load driver: " + e.getMessage());
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        // Create variable to hold the customer info from the database
        List<Customer> customers = new ArrayList<>();

        // Create variables to hold database details
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";

        try {
            // Load the database driver
            Class.forName(driver);
            // TRY to get a connection to the database
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                // Get a statement from the connection
                try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers")) {
                    // Execute the query
                    try (ResultSet rs = ps.executeQuery()) {
                        // Loop through the result set
                        while (rs.next()) {
                            // Get the pieces of a customer from the resultset and create a new Customer
                            Customer c = new Customer(
                                    rs.getInt("customerNumber"),
                                    rs.getString("customerName"),
                                    rs.getString("contactLastName"),
                                    rs.getString("contactFirstName"),
                                    rs.getString("phone"),
                                    rs.getString("addressLine1"),
                                    rs.getString("addressLine2"),
                                    rs.getString("city"),
                                    rs.getString("state"),
                                    rs.getString("postalCode"),
                                    rs.getString("country"),
                                    rs.getInt("salesRepEmployeeNumber"),
                                    rs.getDouble("creditLimit")
                            );

                            customers.add(c);
                        }
                    } catch (SQLException e) {
                        System.out.println("SQL Exception occurred when executing SQL or processing results.");
                        System.out.println("Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception occurred when attempting to prepare SQL for execution");
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred when attempting to connect to database.");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred when trying to load driver: " + e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public boolean deleteById(int id) {
        int rowsAffected = 0;

        // Create variables to hold database details
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";

        try {
            // Load the database driver
            Class.forName(driver);
            // TRY to get a connection to the database
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM customers where customerNumber = ?")) {
                    // Fill in the blanks, i.e. parameterize the query
                    ps.setInt(1, id);

                    // Execute the operation
                    // Remember that when you are doing an update, a delete or an insert,
                    // your only result will be a number indicating how many rows were affected
                    rowsAffected = ps.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("SQL Exception occurred when attempting to prepare/execute SQL.");
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred when attempting to connect to database.");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred when trying to load driver: " + e.getMessage());
            e.printStackTrace();
        }

        if (rowsAffected > 0) {
            return true;
        } else {
            return false;
        }
    }
}
