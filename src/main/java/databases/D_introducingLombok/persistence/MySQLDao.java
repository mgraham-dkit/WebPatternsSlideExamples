package databases.D_introducingLombok.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDao {
    private Properties properties;
    private Connection conn = null;

    public MySQLDao(Connection conn){
        this.conn = conn;
    }

    public MySQLDao(String propertiesFilename) {
        properties = new Properties();
        try {
            // Get the path to the specified properties file
            String rootPath = Thread.currentThread().getContextClassLoader().getResource(propertiesFilename).getPath();
            // Load in all key-value pairs from properties file
            properties.load(new FileInputStream(rootPath));
        }catch(IOException e){
            System.out.println("An exception occurred when attempting to load properties from \"" + propertiesFilename + "\": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if(conn != null){
            return conn;
        }
        // Retrieve connection information from properties file
        // Where no values exist for a property, a default is used
        String driver = properties.getProperty("driver", "com.mysql.cj.jdbc.Driver");
        String url = properties.getProperty("url", "jdbc:mysql://127.0.0.1:3306/");
        String database = properties.getProperty("database", "classicmodels");
        String username = properties.getProperty("username", "root");
        String password = properties.getProperty("password", "");

        Connection connection = null;

        try {
            // Load the database driver
            Class.forName(driver);
            // TRY to get a connection to the database
            connection = DriverManager.getConnection(url+database, username, password);
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
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred when attempting to free connection to database.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
