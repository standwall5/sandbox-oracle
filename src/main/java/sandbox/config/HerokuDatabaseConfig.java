package sandbox.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Heroku-compatible Database Configuration
 * Uses environment variables for database connection
 */
public class HerokuDatabaseConfig {
    private static final String DEFAULT_LOCAL_URL = "jdbc:postgresql://localhost:5432/peso-application";
    private static final String DEFAULT_LOCAL_USERNAME = "postgres";
    private static final String DEFAULT_LOCAL_PASSWORD = "admin123";
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
     * Get database connection using Heroku DATABASE_URL or local config
     * @return Connection to PostgreSQL database
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null) {
            // Heroku PostgreSQL connection
            try {
                URI dbUri = new URI(databaseUrl);
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
                
                return DriverManager.getConnection(jdbcUrl, username, password);
            } catch (URISyntaxException e) {
                throw new SQLException("Invalid DATABASE_URL format", e);
            }
        } else {
            // Local development connection
            return DriverManager.getConnection(DEFAULT_LOCAL_URL, DEFAULT_LOCAL_USERNAME, DEFAULT_LOCAL_PASSWORD);
        }
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
     * Get connection info for debugging
     * @return connection info string
     */
    public static String getConnectionInfo() {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl != null) {
            return "Using Heroku DATABASE_URL";
        } else {
            return "Using local PostgreSQL connection";
        }
    }
}