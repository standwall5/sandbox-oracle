package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Clob;

public class testConnection {
	private static final String jdbcURL = "jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1";
	private static final String username = "sandbox"; // Replace with your Oracle username
	private static final String password = "sandboxUser";
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		
		try {
		Connection con = DriverManager.getConnection(jdbcURL, username, password);
			if (con != null) {
				System.out.println("Connection Successful");
				PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM testing");
				ResultSet rs = preparedStatement.executeQuery();
				
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					int age = rs.getInt("age");
					
					System.out.printf("ID: %d%n Name: %s%n Age: %d%n", id, name, age);
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