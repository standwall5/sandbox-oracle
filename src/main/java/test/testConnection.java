package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Clob;

public class testConnection {
	private static final String jdbcURL = "jdbc:postgresql://localhost:5432/sandbox-application";
	private static final String username = "postgres";
	private static final String password = "pgLarry1!";
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		
		try {
		Connection con = DriverManager.getConnection(jdbcURL, username, password);
			if (con != null) {
				System.out.println("Connection Successful");
				PreparedStatement preparedStatement = con.prepareStatement("SELECT u.user_id, u.first_name, u.last_name, c.email FROM users u LEFT JOIN contact c ON u.contact_id = c.contact_id LIMIT 3");
				ResultSet rs = preparedStatement.executeQuery();
				
				while (rs.next()) {
					int id = rs.getInt("user_id");
					String fname = rs.getString("first_name");
					String lname = rs.getString("last_name");
					String email = rs.getString("email");
					
					System.out.printf("ID: %d%n Name: %s %s%n Email: %s%n%n", id, fname, lname, email);
				}
				
			}
			else {
				System.out.println("Connection failed");
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}