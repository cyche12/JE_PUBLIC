package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class defines the database connection methods.
 * Utilizes the Singleton design pattern to ensure a single instance of the database connection.
 * @autor Jake Elliott
 */
public class DBConnection {
    
    private static DBConnection instance;
    private final Connection connection;
    
    /**
     * Private constructor for initializing the database connection.
     * Attempts to establish a connection to the SQLite database.
     */
    private DBConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:bookvault.db";
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading SQLite JDBC driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
    
    /**
     * Returns the singleton instance of the DBConnection.
     * @return the singleton instance
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Returns the database connection.
     * @return the database connection
     */
    public Connection getConnection() {
        return connection;
    }
}
