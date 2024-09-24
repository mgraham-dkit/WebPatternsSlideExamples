package databases.A_introToJdbc.applications;

import databases.A_introToJdbc.business.Customer;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author michelle
 */
public class SelectCustomerById_ParameterisedSelect {
    public static Customer getCustomerById(int searchNumber){
        // DATABASE CODE
        // Create variable to hold the customer info from the database
        // When there is only one match, no need for an arraylist, just make a single object
        Customer customer = null;

        // Create variables to hold database details
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";

        try {
            // Load the database driver
            Class.forName(driver);

            // TRY to get a connection to the database
            try(Connection conn = DriverManager.getConnection(url, username, password)) {

                // TRY to get a statement from the connection
                // When you are parameterizing the query, remember that you need
                // to use the ? notation (so you can fill in the blanks later)
                try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers where customerNumber = ?")) {

                    // Fill in the blanks, i.e. parameterize the query
                    ps.setInt(1, searchNumber);

                    // TRY to execute the query
                    try(ResultSet rs = ps.executeQuery()){
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
                    }catch(SQLException e){
                        System.out.println("SQL Exception occurred when executing SQL or processing results.");
                        System.out.println("Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }catch(SQLException e){
                    System.out.println("SQL Exception occurred when attempting to prepare SQL for execution");
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }catch(SQLException e){
                System.out.println("SQL Exception occurred when attempting to connect to database.");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } catch(ClassNotFoundException e){
            System.out.println( "ClassNotFoundException occurred when trying to load driver: " + e.getMessage() );
            e.printStackTrace();
        }

        return customer;
    }

    public static void main(String[] args) {
        // Create a Scanner to take in information from the user
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the number of the customer to be found:");
        int searchNumber = input.nextInt();

        Customer customer = getCustomerById(searchNumber);

        // Display the Customer information
        if (customer != null) {
            System.out.println("Match found: ");
            System.out.println(customer.format());
        } else {
            System.out.println("Sorry, no match was found for that customer number");
        }
    }
}
