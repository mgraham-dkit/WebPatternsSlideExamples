package databases.introducingLombok.applications;

import databases.introducingLombok.business.Customer;
import databases.introducingLombok.business.CustomerCountryAscNameAscComparator;
import databases.introducingLombok.business.CustomerNameAscComparator;
import databases.introducingLombok.business.CustomerNameDescComparator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author michelle
 */
public class IntroSQLExample_SelectAll {

    public static void main(String[] args) {
        // Create variable to hold the customer info from the database
        ArrayList<Customer> customers = new ArrayList<>();
        
        // Create variables to hold database details
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";
        
        try{
            // Load the database driver
            Class.forName(driver);
            // Get a connection to the database
            try(Connection conn = DriverManager.getConnection(url, username, password)) {
                // Can also do:
                // String URL = "jdbc:mysql://127.0.0.1:3306/classicmodels?user=root&password=";
                // Connection conn = DriverManager.getConnection(URL);

                // Get a statement from the connection
                try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers ORDER BY country ASC")) {
                    // Execute the query
                    try(ResultSet rs = ps.executeQuery()) {
                        // Loop through the result set
                        while (rs.next()) {
                            // Get the pieces of a customer from the resultset and create a new Customer
                            // using Lombok's builder functionality
                            Customer c = Customer.builder()
                                    .customerNumber(rs.getInt("customerNumber"))
                                    .customerName(rs.getString("customerName"))
                                    .contactLastName(rs.getString("contactLastName"))
                                    .contactFirstName(rs.getString("contactFirstName"))
                                    .phone(rs.getString("phone"))
                                    .addressLine1(rs.getString("addressLine1"))
                                    .addressLine2(rs.getString("addressLine2"))
                                    .city(rs.getString("city"))
                                    .state(rs.getString("state"))
                                    .postalCode(rs.getString("postalCode"))
                                    .country(rs.getString("country"))
                                    .salesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"))
                                    .creditLimit(rs.getDouble("creditLimit"))
                                    .build(); // Without this, we won't make anything!

                            customers.add(c);
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
        }catch(ClassNotFoundException e){
            System.out.println( "ClassNotFoundException occurred when trying to load driver: " + e.getMessage() );
            e.printStackTrace();
        }
        
        // Display the Customer information
        // It should NOT be in ascending order of customer number
        for (Customer c : customers) {
            System.out.println("Customer: " + c);
        }
        
        System.out.println("----------------------------------------------------");
        
        System.out.println("Reshuffling Customer information to use natural order "
                + "(i.e. the order established by Comparable)");
        Collections.sort(customers);
        System.out.println("----------------------------------------------------");
        for(Customer c: customers){
            System.out.println("Customer: " + c);
        }
        
        System.out.println("----------------------------------------------------");
        
        System.out.println("Reshuffling Customer information to use ascending order of name"
                + "(i.e. the order established by the CustomerNameAsc Comparator)");
        customers.sort(new CustomerNameAscComparator());
        System.out.println("----------------------------------------------------");
        for(Customer c: customers){
            System.out.println("Customer: " + c);
        }
        
        System.out.println("----------------------------------------------------");
        
        System.out.println("Reshuffling Customer information to use descending order of name"
                + "(i.e. the order established by the CustomerNameDesc Comparator)");
        customers.sort(new CustomerNameDescComparator());
        System.out.println("----------------------------------------------------");
        for(Customer c: customers){
            System.out.println("Customer: " + c);
        }
        
        System.out.println("----------------------------------------------------");
        
        System.out.println("Reshuffling Customer information to use ascending order of country. "
                + "Where two countries are the same, Customers should be ranked in ascending order of name."
                + "(i.e. the order established by the CustomerCountryAscNameAsc Comparator)");
        customers.sort(new CustomerCountryAscNameAscComparator());
        System.out.println("----------------------------------------------------");
        for(Customer c: customers){
            System.out.println("Customer: " + c);
        }
    }
}
