package sandbox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sandbox.model.Company;
import sandbox.model.User;

public class UserDAO {
	private static final String jdbcURL = "jdbc:oracle:thin:@" + "localhost" + ":" + "1521" + "/" + "FREE";
	private static final String username = "SYS"; // Replace with your Oracle username
	private static final String password = "mypassword1";

	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(jdbcURL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return conn;
	}

	
//	This should be in searchDAO?
	public List<User> selectAllUsersSearch() throws SQLException {
		List<User> users = new ArrayList<>();
		String SELECT_ALL_USERS = "SELECT * FROM User";
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_USERS)) {

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("firstname");
				String lname = rs.getString("lastname");
				String district = rs.getString("district");
				String barangay = rs.getString("barangay");
				String bio = rs.getString("bio");
				String icon = rs.getString("icon");

				users.add(new User(id, fname, lname, district, barangay, bio, icon));
			}
		}
		return users;

	}
	
	
//	wait what? what is this for? But we are selecting all companies this is probably a duplicate
//	class method, i am really confused (its not a search query though, just gets every company)
	public List<Company> selectAllCompaniesSearch() throws SQLException {
		List<Company> company = new ArrayList<>();
		String SELECT_ALL_COMPANIES = "SELECT * FROM Company";
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_COMPANIES)) {

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("companyname");
				String desc = rs.getString("description");
				String icon = rs.getString("companyimage");
				String address = rs.getString("address");

				company.add(new Company(id, name, desc, icon, address));
			}
		}
		return company;

	}

	
//	WHAT
//	selects every user (by id) in a certain post, then through user id, retrieves their data
//	probably used to display all applicants
	public List<User> selectAllUsers(int postId) {
		List<User> users = new ArrayList<>();
		String SELECT_ALL_USERS_APP = "SELECT * FROM Applications WHERE postId = ?";
		String SELECT_USER_INFO = "SELECT * FROM User WHERE id = ?";
		String SELECT_RESUME_INFO = "SELECT * FROM Resume WHERE userId = ?";

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_USERS_APP)) {

			preparedStatement.setInt(1, postId);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int userId = rs.getInt("userId");

				try (PreparedStatement preparedStatement2 = conn.prepareStatement(SELECT_USER_INFO)) {
					preparedStatement2.setInt(1, userId);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String fname = rs2.getString("firstname");
						String lname = rs2.getString("lastname");
						String cnumber = rs2.getString("contactnumber");
						String district = rs2.getString("district");
						String barangay = rs2.getString("barangay");

						try (PreparedStatement preparedStatement3 = conn.prepareStatement(SELECT_RESUME_INFO)) {
							preparedStatement3.setInt(1, userId);
							ResultSet rs3 = preparedStatement3.executeQuery();

							if (rs3.next()) {
								String address = rs3.getString("address");
								String workhist = rs3.getString("workhist");
								String educhist = rs3.getString("educhist");
								String skills = rs3.getString("skills");
								String resdesc = rs3.getString("resdesc");

								users.add(new User(userId, fname, lname, cnumber, district, barangay, address, workhist,
										educhist, skills, resdesc));
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
