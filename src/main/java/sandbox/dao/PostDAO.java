package sandbox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sandbox.model.JobPosts;
import sandbox.model.User;
import sandbox.model.UserLogin;

public class PostDAO {
	
//	Database Credentials
	private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
	private static final String jdbcUsername = "oracle123";
	private static final String jdbcPassword = "mypassword1";
//	End of db cred
	
//	SQL queries to be used
//	Probably created to tie into prepared statements; preventing SQL injections
	private static final String SELECT_ALL_JOBS = "select * from posts";
	private static final String SELECT_ALL_JOBS_COMPANY = "select * from posts where companyid = ?";
	private static final String SELECT_JOB_BY_ID = "select * from posts where id = ?";
	private static final String DELETE_JOBS_SQL = "delete from posts where id = ?";
//	End of queries

	
	
//	Connection function (used in every function thereafter to connect to database
	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(jdbcURL);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return conn;
	}
//	End of connection function
	
	
////	Functions

//	Gets the user info by ID/ return an object with all the personal info of a user
	public JobPosts getUserById(int userId) { // Class JobPosts functionName(variables)
		JobPosts post = null; // Initialize post with post with type JobPosts
		ResultSet rs = null; // Type ResultSet variable name

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement("select * from User where id = ?");) {

			preparedStatement.setInt(1, userId); // Replace the ? in the query above to the given userId

			rs = preparedStatement.executeQuery(); // Execute the query/ retrieve every field from that id

			if (rs.next()) { // If data is returned then:
				int id = rs.getInt("id");
				// Specify id as an integer, then get the id from the results
				
				String fname = rs.getString("firstname");
				// Get string and store it into variables name
				
				String lname = rs.getString("lastname");
				String district = rs.getString("district");
				String barangay = rs.getString("barangay");
				String bio = rs.getString("bio");
				String icon = rs.getString("icon");
				// All are the same with first name variable

				post = new JobPosts(id, fname, lname, district, barangay, bio, icon);
				// Create a new object with previously declared variables, and store it into post
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return post;
		// if no error occurs, return the object to whatever file used the class method (JobPost)
		// It returns the post object which can then be utilized by whatever function in the code called it
	}
//	End of getting user ID
	
	
//	Gets the company's info by ID/ retrieves all info about the company through its ID
	public JobPosts getCompanyById(int userId) {
		JobPosts company = null;
		ResultSet rs = null;

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement("select * from Company where id = ?");) {

			preparedStatement.setInt(1, userId);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String name = rs.getString("companyname");
				String address = rs.getString("address");
				String desc = rs.getString("description");
				String icon = rs.getString("companyImage");

				company = new JobPosts(name, desc, icon, address);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return company;
	}
//	End of company id
	
	
	
//	For inserting a resume into the database
	public int insertResume(User resume) { // So resume which is a User type variable
		int rowCount = 0;
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(
						"INSERT INTO Resume (address, workhist, educhist, skills, resdesc, userID) VALUES"
								+ "(?, ?, ?, ?, ?, ?)");) {
//			From that user object, we are able to retrieve information without putting much in the parameters
			preparedStatement.setString(1, resume.getAddress());
			preparedStatement.setString(2, resume.getWorkhist());
			preparedStatement.setString(3, resume.getEduchist());
			preparedStatement.setString(4, resume.getSkills());
			preparedStatement.setString(5, resume.getResdesc());
			preparedStatement.setInt(6, UserLogin.getId2());
			rowCount = preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}
//	End of insert resume
	
	
//	Update a user's resume
	public boolean updateResume(User resume) {
		boolean rowCount = false;
		String sql = "UPDATE Resume SET address = ?, workhist = ?, educhist = ?, skills = ?, resdesc = ? WHERE userID = ?";

		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

			preparedStatement.setString(1, resume.getAddress());
			preparedStatement.setString(2, resume.getWorkhist());
			preparedStatement.setString(3, resume.getEduchist());
			preparedStatement.setString(4, resume.getSkills());
			preparedStatement.setString(5, resume.getResdesc());
			preparedStatement.setInt(6, UserLogin.getId2());

			rowCount = preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;
	}
//	End of updating user resume

	
//	Retrieval of a certain job post through its ID
	public JobPosts selectJob(int id) {
		JobPosts jobpost = null;
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_JOB_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int companyid = rs.getInt("companyid");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("postDate");

				String companyQuery = "SELECT companyname FROM Company WHERE id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("companyname");
						jobpost = new JobPosts(id, company, title, desc, address, category, postdate, "placeholder");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobpost;
	}
//	End of retrieving job post function
	
	
//	Used for displaying multiple jobs on the home page
	public List<JobPosts> selectAllJobs() { // Specify a class list with method selectAllJobs
		List<JobPosts> work = new ArrayList<>(); // Initialize the object to be returned, which is an array
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_JOBS);) {

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int companyid = rs.getInt("companyid");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("postDate");

				String companyQuery = "SELECT companyname FROM Company WHERE id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("companyname");
						work.add(new JobPosts(id, company, title, desc, address, category, postdate, "placeholder"));
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return work;
	}
//	End of list jobs

	
//	We select all the jobs posted by our company, not sure if this was used to exclude
//	the jobs that company posted or to only show that company's jobs
//	This is used for company view mode
	public List<JobPosts> selectAllJobsCompany() {
		List<JobPosts> work = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_JOBS_COMPANY);) {
			preparedStatement.setInt(1, UserLogin.getCompanyID());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int companyid = rs.getInt("companyid");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("postDate");

				final String companyQuery = "SELECT companyname FROM Company WHERE id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("companyname");
						work.add(new JobPosts(id, company, title, desc, address, category, postdate, "placeholder"));
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return work;
	}
//	End of getting jobs by ID
	
	
//	Deleting a work (only accessible in company mode)
	public boolean deleteWork(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(DELETE_JOBS_SQL);) {
			statement.setInt(1, id);

			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
//	Applying work (user mode)
	public void applyWork(int postId, int userId) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn
						.prepareStatement("INSERT INTO Applications (postid, userid) VALUES (?, ?)")) {

			statement.setInt(1, postId);
			statement.setInt(2, userId);

			int rowCount = statement.executeUpdate();
			if (rowCount > 0) {
				System.out.println("Application successfully added.");
			} else {
				System.out.println("No rows affected. Application may not have been added.");
			}
		} catch (SQLException e) {
			System.err.println("Error applying work: " + e.getMessage());
			throw e;
		}
	}

	
//	Check if you have applied for this? (not sure what it is for yet)
	public int checkApply(int postId, int userId) throws SQLException {
		int exists = 0;
		try (Connection conn = getConnection();
				PreparedStatement statement = conn
						.prepareStatement("SELECT * from applications where userid = ? and postid = ?")) {
			statement.setInt(1, userId);
			statement.setInt(2, postId);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				exists = 1;
			} else {
				exists = 0;
			}
		} catch (SQLException e) {
			System.err.println("Error applying work: " + e.getMessage());
			throw e;
		}
		return exists;
	}
	
	
//	Accept an applicant (company mode)
//	That function probably loops through each applicant and assigns an ID
//	so when the company clicks accept on a user, the ID of that user is already set
//	using the ${name} thing (only present in JSP)
	public void acceptApplicant(int id) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM Company where id = ?")) {
			statement.setInt(1, UserLogin.getCompanyID());

			ResultSet rs = statement.executeQuery(); // Get every data of that company

			if (rs.next()) {
				int empId = rs.getInt("EmpID");
				
//				Sets the applicant's empID (in database) to the unique empID of that company
				PreparedStatement statement2 = conn.prepareStatement("UPDATE User set EmployeeID = ? where id = ?");
				statement2.setInt(1, empId);
				statement2.setInt(2, id);

				int rowCount = statement2.executeUpdate();

			}
		}
	}
	
	
//	Conversely. reject the applicant
	public void rejectApplicant(int userId, int workId) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn
						.prepareStatement("DELETE FROM Applications WHERE Userid = ? AND postid = ?")) {

			statement.setInt(1, userId);
			statement.setInt(2, workId);

//			Remove the applicant from the applied list on that job
			int rowsDeleted = statement.executeUpdate(); 
			if (rowsDeleted > 0) {
				System.out.println(
						"Applicant with User ID " + userId + " and Work ID " + workId + " rejected successfully.");
			} else {
				System.out.println("No applicant found with User ID " + userId + " and Work ID " + workId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
//	Used for displaying all info of a resume
	public User selectAllResume(int id) throws SQLException {
		User user = null;
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM resume where userid = ?")) {

			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String address = rs.getString("address");
				String workhist = rs.getString("workhist");
				String educhist = rs.getString("educhist");
				String skills = rs.getString("skills");
				String desc = rs.getString("resdesc");

				user = new User(address, workhist, educhist, skills, desc);
			} else {
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return user;
	}

	/*
	 * public void applyWork(int id, int id2) throws SQLException { int rowCount =
	 * 0; try(Connection conn = getConnection(); PreparedStatement statement = conn.
	 * prepareStatement("INSERT INTO Applications (postid, userid) VALUES (?, ?)");)
	 * { statement.setInt(1, id); statement.setInt(2, id2);
	 * 
	 * rowCount = statement.executeUpdate(); } }
	 */
}
