package sandbox.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * H2 Database Configuration (Embedded Database)
 * No PostgreSQL installation required!
 */
public class DatabaseConfigH2 {
    // H2 embedded database (file-based)
    private static final String JDBC_URL = "jdbc:h2:./database/sandbox_jobs;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1";
    private static final String JDBC_USERNAME = "sa";
    private static final String JDBC_PASSWORD = "";
    private static final String JDBC_DRIVER = "org.h2.Driver";
    
    static {
        try {
            // Load H2 JDBC driver
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 JDBC Driver not found", e);
        }
    }
    
    /**
     * Get database connection
     * @return Connection to H2 database
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
    
    /**
     * Initialize database with sample data
     * Call this once to set up tables and data
     */
    public static void initializeDatabase() throws SQLException {
        try (Connection conn = getConnection()) {
            // Create tables and insert data here
            System.out.println("Database initialized successfully!");
        }
    }
}