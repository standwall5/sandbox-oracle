package sandbox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.datasource.impl.OracleDataSource;
import sandbox.model.Company;
import sandbox.model.JobPosts;
import sandbox.model.User;

public class SearchDAO {
	private static final String jdbcURL = "jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1";
	private static final String jdbcUsername = "sandbox";
	private static final String jdbcPassword = "sandboxUser";

	protected Connection getConnection() throws SQLException{
		OracleDataSource ods = new OracleDataSource();
		Connection conn = null;
		try {
			ods.setURL(jdbcURL);
			ods.setUser(jdbcUsername);
			ods.setPassword(jdbcPassword);
//			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
			conn = ods.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public List<User> selectAllUsersSearch(String query) throws SQLException {
		List<User> users = new ArrayList<>();
		String searchQuery = "SELECT * FROM users \r\n"
				+ "WHERE LOWER(first_name) LIKE LOWER(?) \r\n"
				+ "   OR LOWER(last_name) LIKE LOWER(?) \r\n"
				+ "   OR LOWER(description) LIKE LOWER(?)\r\n"
				+ ""; //  OR district LIKE ? OR barangay LIKE ?

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {

//			% is used to have a SIMILAR match, otherwise, SQL will search for an exact match
//			Example: search query ("john patrick"), without % (%john patrick%) then SQL will search
//			for "john patrick". If there is "john patrick salen", it wont be found because it is not
//			an exact match. %john patrick% solves this
			
//			Inputting the query in all of this allows for searching in every field
//			i.e. barangay, district or last name
//			If someone put john patrick moonwalk, all john patrick that is NOT in moonwalk
//			will not appear as it does not satisfy the conditions
			preparedStatement.setString(1, "%" + query + "%");
			preparedStatement.setString(2, "%" + query + "%");
			preparedStatement.setString(3, "%" + query + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("user_id");
				int contactId = rs.getInt("contact_id");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");
				String bio = rs.getString("description");
				String icon = rs.getString("icon");
				String district = null;
				String barangay = null;
				String email = null;
				
				try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contact where contact_id = ?")) {
					stmt.setInt(1, contactId);
					ResultSet rs2 = stmt.executeQuery();
					
					if (rs2.next()) {
						district = rs2.getString("district");
						barangay = rs2.getString("barangay");
						email = rs2.getString("email");
					}
				}

				users.add(new User(id, fname, lname, bio, icon, district, barangay, email));
			}
		}
		return users;
	}
	
	
//	Searching all companies, has its own section
	public List<Company> selectAllCompaniesSearch(String query) throws SQLException {
		List<Company> companies = new ArrayList<>();
		String searchQuery = "SELECT * FROM Company WHERE LOWER(company_name) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?)"; // OR address LIKE ? add this

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {

			preparedStatement.setString(1, "%" + query + "%");
			preparedStatement.setString(2, "%" + query + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("company_id");
				int contactId = rs.getInt("contact_id");
				String name = rs.getString("company_name");
				String desc = rs.getString("description");
				String icon = rs.getString("company_icon"); // change all jsp references to images
				String address = null;
				String province = null;
				String city = null;
				try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM company_contact WHERE contact_id = ?")) {
					stmt.setInt(1, contactId);
					ResultSet rs2 = stmt.executeQuery();
					if (rs2.next()) {
						address = rs2.getString("specific_address");
						province = rs2.getString("province");
						city = rs2.getString("city");
					}
				}
				
				companies.add(new Company(id, name, desc, icon, address, city, province));
			}
			
		}
		return companies;
	}
	
	
//	All jobs (has its own section)
	public List<JobPosts> selectAllJobsSearch(String query) {
		List<JobPosts> jobs = new ArrayList<>();
		String searchQuery = "SELECT * FROM job_posts WHERE LOWER(title) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?) OR LOWER(address) LIKE LOWER(?) OR LOWER(post_date) LIKE LOWER(?)";

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {

			preparedStatement.setString(1, "%" + query + "%");
			preparedStatement.setString(2, "%" + query + "%");
			preparedStatement.setString(3, "%" + query + "%");
			preparedStatement.setString(4, "%" + query + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("post_id");
				int companyId = rs.getInt("company_id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postDate = rs.getString("post_date");

				String companyQuery = "SELECT company_name FROM Company WHERE company_id = ?";
				try (PreparedStatement companyStatement = conn.prepareStatement(companyQuery)) {
					companyStatement.setInt(1, companyId);
					ResultSet companyResult = companyStatement.executeQuery();

					if (companyResult.next()) {
						String companyName = companyResult.getString("company_name");
						jobs.add(
								new JobPosts(id, companyName, title, desc, address, category, postDate, "placeholder"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jobs;
	}
}
