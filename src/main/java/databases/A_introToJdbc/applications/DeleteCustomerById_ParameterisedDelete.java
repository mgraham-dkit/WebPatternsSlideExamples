package databases.A_introToJdbc.applications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author michelle
 */
public class DeleteCustomerById_ParameterisedDelete {

    public static void main(String[] args) {
        // Create a Scanner to take in information from the user
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the number of the customer to be removed:");
        int searchNumber = input.nextInt();

        // DATABASE CODE
        // Create variable to hold the result of the operation
        // Remember, where you are NOT doing a select, you will only ever get
        // a number indicating how many things were changed/affected
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
            try(Connection conn = DriverManager.getConnection(url, username, password)) {
                // TRY to get a statement from the connection
                // When you are parameterizing the query, remember that you need
                // to use the ? notation (so you can fill in the blanks later)
                try(PreparedStatement ps = conn.prepareStatement("DELETE FROM customers where customerNumber = ?")) {

                    // Fill in the blanks, i.e. parameterize the query
                    ps.setInt(1, searchNumber);

                    // Execute the operation
                    // Remember that when you are doing an update, a delete or an insert,
                    // your only result will be a number indicating how many rows were affected
                    rowsAffected = ps.executeUpdate();
                }catch(SQLException e){
                    System.out.println("SQL Exception occurred when attempting to prepare/execute SQL.");
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

        if (rowsAffected > 0) {
            System.out.printf("Customer %d deleted.", searchNumber);
        } else if (rowsAffected == -1) {
            System.out.printf("Customer %d could not be deleted at this time.", searchNumber);
        } else {
            System.out.printf("No Customer present with id number %d.", searchNumber);
        }
    }

}
