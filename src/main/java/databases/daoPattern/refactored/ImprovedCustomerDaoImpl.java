package databases.daoPattern.refactored;

import databases.daoPattern.business.Customer;
import databases.daoPattern.persistence.CustomerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImprovedCustomerDaoImpl extends MySQLDao implements CustomerDao {
    public ImprovedCustomerDaoImpl(String propertiesFilename){
        super(propertiesFilename);
    }

    @Override
    public Customer getById(int id) {
        Customer customer = null;

        // Get a connection using the superclass
        Connection conn = super.getConnection();
        // TRY to get a statement from the connection
        // When you are parameterizing the query, remember that you need
        // to use the ? notation (so you can fill in the blanks later)
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers where customerNumber = ?")) {

            // Fill in the blanks, i.e. parameterize the query
            ps.setInt(1, id);

            // TRY to execute the query
            try (ResultSet rs = ps.executeQuery()) {
                // Extract the information from the result set
                // Use extraction method to avoid code repetition!
                customer = extractCustomerFromRow(rs);
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred when executing SQL or processing results.");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred when attempting to prepare SQL for execution");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }finally {
            // Close the connection using the superclass method
            super.freeConnection(conn);
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        // Create variable to hold the customer info from the database
        List<Customer> customers = new ArrayList<>();

        // Get a connection using the superclass
        Connection conn = super.getConnection();
        // Get a statement from the connection
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers")) {
            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                // Repeatedly try to get a customer from the resultset
                Customer c = null;
                boolean empty = false;
                while (!empty) {
                    c = extractCustomerFromRow(rs);
                    // If it's null, the row was empty/didn't exist so the loop should end
                    if (c == null) {
                        empty = true;
                    } else {
                        // If a real customer was returned, add it to the list of results
                        customers.add(c);
                    }
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
        }finally {
            // Close the connection using the superclass method
            super.freeConnection(conn);
        }

        return customers;
    }

    @Override
    public boolean deleteById(int id) {
        int rowsAffected = 0;

        Connection conn = super.getConnection();
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
        } finally {
            // Close the superclass method
            super.freeConnection(conn);
        }

        if (rowsAffected > 0) {
            return true;
        } else {
            return false;
        }
    }

    private Customer extractCustomerFromRow(ResultSet rs) throws SQLException {
        // Confirm the ResultSet exists before using it
        if (rs == null) {
            throw new IllegalArgumentException("ResultSet cannot be null.");
        }

        // Set up Customer object to hold extracted data
        Customer c = null;

        // If there is a row remaining in the resultset, extract it to a Customer object
        if (rs.next()) {
            // Get the pieces of a customer from the resultset and create a new Customer
            c = new Customer(
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
        // Return the extracted Customer (or null if the resultset was empty)
        return c;
    }

    public static void main(String[] args) {
        CustomerDao customerDao = new ImprovedCustomerDaoImpl("database.properties");
        List<Customer> customers = customerDao.getAllCustomers();
        System.out.println(customers);
    }
}
