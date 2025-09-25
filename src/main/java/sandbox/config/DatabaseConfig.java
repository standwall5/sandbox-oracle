package sandbox.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * PostgreSQL Database Configuration
 * Database configuration for Peso Job Application System
 */
public class DatabaseConfig {
    // PostgreSQL connection details
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/peso-application";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "admin123";
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    
    static {
        try {
            // Load PostgreSQL JDBC driver
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }
    
    /**
     * Get database connection
     * @return Connection to PostgreSQL database
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }
    
    /**
     * Test database connection
     * @return true if connection successful
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}
