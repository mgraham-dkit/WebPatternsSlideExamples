package databases.A_introToJdbc.applications;

import databases.A_introToJdbc.business.Employee;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author michelle
 */
public class AddNewEmployee_ParameterisedInsert {
    private static final Scanner input = new Scanner(System.in);
    public static Employee createEmployee(){
        System.out.println("Please enter the new employee's id number:");
        int idNumber = input.nextInt();
        // Remove the left-over newline character from the buffer
        input.nextLine();
        // Read in the next component of the customer's information
        System.out.println("Please enter the new employee's first name:");
        String firstName = input.nextLine();

        System.out.println("Please enter the new employee's last name:");
        String lastName = input.nextLine();

        System.out.println("Please enter the new employee's extension number:");
        String extension = input.nextLine();

        System.out.println("Please enter the new employee's email address:");
        String email = input.nextLine();

        System.out.println("Please enter the new employee's office code:");
        String officeCode = input.nextLine();

        System.out.println("Please enter the id of the new employee's manager");
        int reportsTo = input.nextInt();
        // Remove the left-over newline character from the buffer
        input.nextLine();

        System.out.println("Please enter the new employee's job title:");
        String jobTitle = input.nextLine();

        // Put all the information about the employee into an Employee object
        return new Employee(idNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);
    }

    public static int insertEmployee(Employee newEmployee){
        // DATABASE CODE
        //
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
            try(Connection conn = DriverManager.getConnection(url, username, password)){
                // TRY to prepare a statement from the connection
                // When you are parameterizing the update, remember that you need
                // to use the ? notation (so you can fill in the blanks later)
                try(PreparedStatement ps = conn.prepareStatement("insert into employees values(?, ?, ?, ?, ?, ?, ?, " +
                        "?)")) {
                    // Fill in the blanks, i.e. parameterize the update
                    ps.setInt(1, newEmployee.getEmployeeNumber());
                    ps.setString(2, newEmployee.getLastName());
                    ps.setString(3, newEmployee.getFirstName());
                    ps.setString(4, newEmployee.getExtension());
                    ps.setString(5, newEmployee.getEmail());
                    ps.setString(6, newEmployee.getOfficeCode());
                    ps.setInt(7, newEmployee.getReportsTo());
                    ps.setString(8, newEmployee.getJobTitle());

                    // Execute the update and store how many rows were affected/changed
                    // when inserting, this number indicates if the row was
                    // added to the database (>0 means it was added)
                    rowsAffected = ps.executeUpdate();
                }// Add an extra exception handling block for where there is already an entry
                // with the primary key specified
                catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Constraint Exception occurred: " + e.getMessage());
                    // Set the rowsAffected to -1, this can be used as a flag for the display section
                    rowsAffected = -1;
                }catch(SQLException e){
                    System.out.println("SQL Exception occurred when attempting to prepare/execute SQL");
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }catch(SQLException e){
                System.out.println("SQL Exception occurred when attempting to connect to database.");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            System.out.println( "ClassNotFoundException occurred when trying to load driver: " + e.getMessage() );
            e.printStackTrace();
        }

        return rowsAffected;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the AddEmployee application");
        Employee newEmployee = createEmployee();
        int rowsAffected = insertEmployee(newEmployee);

        if (rowsAffected > 0) {
            System.out.println("Employee added successfully.");
        } else if (rowsAffected == -1) {
            System.out.println("The Employee id supplied already exists. Please check the employee details and try again.");
        } else {
            System.out.println("The Employee could not be added at this time.");
        }
    }
}
