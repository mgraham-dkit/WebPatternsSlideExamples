package databases.daoPattern.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDao {
    public Connection getConnection() {
        // Create variables to hold database details
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";

        Connection connection = null;

        try {
            // Load the database driver
            Class.forName(driver);
            // TRY to get a connection to the database
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred when trying to load driver: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred when attempting to connect to database.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
        return connection;
    }

    public void freeConnection(Connection con){
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred when attempting to free connection to database.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
