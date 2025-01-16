package sandbox.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import oracle.jdbc.datasource.impl.OracleDataSource;
import sandbox.model.Company;
import sandbox.model.CompanyContact;
import sandbox.model.Contact;
import sandbox.model.Education;
import sandbox.model.JobPosts;
import sandbox.model.ResumeId;
import sandbox.model.Skills;
import sandbox.model.User;
import sandbox.model.UserLogin;
import sandbox.model.WorkHist;

public class PostDAO {
	
//	Database Credentials
	private static final String jdbcURL = "jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1";
	private static final String jdbcUsername = "sandbox";
	private static final String jdbcPassword = "sandboxUser";
//	End of db cred
	
//	SQL queries to be used
	
	private static final String SELECT_ALL_JOBS = "select * from job_posts";
	private static final String SELECT_ALL_JOBS_COMPANY = "select * from job_posts where company_id = ?";
	private static final String SELECT_JOB_BY_ID = "select * from job_posts where post_id = ?";
	private static final String DELETE_JOBS_SQL = "delete from job_posts where post_id = ?";
	private static final String SELECT_USER_BY_ID = "select * from users where user_id = ?";
	private static final String SELECT_COMPANY_BY_ID = "select * from Company where company_id = ?";
	private static final String INSERT_INTO_RESUME = "INSERT INTO Resume (address, workhist, educhist, skills, resdesc, userID) VALUES" + "(?, ?, ?, ?, ?, ?)"; 
	private static final String UPDATE_RESUME = "UPDATE Resume SET address = ?, workhist = ?, educhist = ?, skills = ?, resdesc = ? WHERE user_id = ?"; 
	private static final String INSERT_INTO_APPLICATIONS = "INSERT INTO Applications (post_id, user_id) VALUES (?, ?)";
	private static final String SELECT_ALL_APPLICATIONS = "SELECT * from applications where user_id = ? and post_id = ?";
	private static final String UPDATE_USER_EMPID = "UPDATE Users set Employee_ID = ? where user_id = ?";
	
//	End of queries

	
	
//	Connection function (used in every function thereafter to connect to database
	protected Connection getConnection() throws SQLException{
		OracleDataSource ods = new OracleDataSource();
		Connection conn = null;
		try {
			ods.setURL(jdbcURL);
			ods.setUser(jdbcUsername);
			ods.setPassword(jdbcPassword);
			conn = ods.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
//	End of connection function
	
	
////	Functions

//	public void connect() {
//		try {
//			Connection con = DriverManager.getConnection(jdbcURL, username, password);
//				if (con != null) {
//					System.out.println("Connection Successful");
//					PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM testing");
//					ResultSet rs = preparedStatement.executeQuery();
//					
//					while (rs.next()) {
//						int id = rs.getInt("id");
//						String name = rs.getString("name");
//						int age = rs.getInt("age");
//						
//						System.out.printf("ID: %d%n Name: %s%n Age: %d%n", id, name, age);
//					}
//					
//				}
//				else {
//					System.out.println("Connection failed");
//				}
//			}catch (Exception e) {
//				System.out.println(e);
//			}
//	}
	
//	public boolean uploadImage(InputStream fileContent, Contact userContact) {
//		int rowCount = 0;
//		int contactId = 0;
//		try (Connection conn = getConnection();
//				PreparedStatement stmt = conn.prepareStatement("SELECT contact_id from"
//						+ " contact where email = ?");) {
//				ResultSet rs = null;
//				stmt.setString(1, userContact.getEmail());
//				rs = stmt.executeQuery();
//				
//            if (rs.next()) {
//            	contactId = rs.getInt("contact_id");
//            	try(PreparedStatement stmt2 = conn.prepareStatement("UPDATE users SET icon = ?"
//						+ " WHERE contact_id = ?");){
//			stmt2.setBinaryStream(1, fileContent);  // Set the BLOB
//			stmt2.setInt(2, contactId);
//            rowCount = stmt2.executeUpdate();
//            } catch (Exception e) {
//            	e.printStackTrace();
//            	}
//            }
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//			
//		
//		return rowCount > 0;
//	}

	public boolean uploadImage(InputStream fileContent, Contact userContact) {
	    int rowCount = 0;
	    int contactId = 0;

	    // Define the directory where images will be saved
	    String imageDirectory = "C:\\Users\\johnp\\eclipse-workspace-new\\jobapplication-oracle-project\\src\\main\\webapp\\images"; // Replace with your desired path
	    File fileSaveDir = new File(imageDirectory);
	    if (!fileSaveDir.exists()) {
	        fileSaveDir.mkdirs(); // Create the directory if it doesn't exist
	    }

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT contact_id FROM contact WHERE email = ?")) {
	        stmt.setString(1, userContact.getEmail());
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                contactId = rs.getInt("contact_id");

	                // Generate a unique file name for the image
	                String uniqueFileName = "user_" + contactId + "_" + System.currentTimeMillis() + ".jpg";
	                String imagePath = imageDirectory + File.separator + uniqueFileName;

	                // Save the image to the filesystem
	                try (OutputStream outputStream = new FileOutputStream(imagePath)) {
	                    byte[] buffer = new byte[8192]; // 8KB buffer
	                    int bytesRead;
	                    while ((bytesRead = fileContent.read(buffer)) != -1) {
	                        outputStream.write(buffer, 0, bytesRead);
	                    }
	                }

	                // Save the image file name (or path) to the database
	                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE users SET icon = ? WHERE contact_id = ?")) {
	                    stmt2.setString(1, uniqueFileName); // Save the file name, not the binary data
	                    stmt2.setInt(2, contactId);
	                    rowCount = stmt2.executeUpdate();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return rowCount > 0;
	}
	
	public boolean uploadImageCompany(InputStream fileContent, int contactId) throws IOException {
	    int rowCount = 0;

	    // Define the directory where images will be saved
	    String imageDirectory = "C:\\Users\\johnp\\eclipse-workspace-new\\jobapplication-oracle-project\\src\\main\\webapp\\images"; // Replace with your desired path
	    File fileSaveDir = new File(imageDirectory);
	    if (!fileSaveDir.exists()) {
	        fileSaveDir.mkdirs(); // Create the directory if it doesn't exist
	    }
	                // Generate a unique file name for the image
	                String uniqueFileName = "company_" + contactId + "_" + System.currentTimeMillis() + ".jpg";
	                String imagePath = imageDirectory + File.separator + uniqueFileName;

	                // Save the image to the filesystem
	                try (OutputStream outputStream = new FileOutputStream(imagePath)) {
	                    byte[] buffer = new byte[8192]; // 8KB buffer
	                    int bytesRead;
	                    while ((bytesRead = fileContent.read(buffer)) != -1) {
	                        outputStream.write(buffer, 0, bytesRead);
	                    }
	                }

	                // Save the image file name (or path) to the database
	                try (Connection conn = getConnection();
	                		PreparedStatement stmt2 = conn.prepareStatement("UPDATE company SET company_icon = ? WHERE contact_id = ?")) {
	                    stmt2.setString(1, uniqueFileName); // Save the file name, not the binary data
	                    stmt2.setInt(2, contactId);
	                    rowCount = stmt2.executeUpdate();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }

	    return rowCount > 0;
	}

	
	
	public boolean registerFinal(UserLogin user, Contact userContact) {
	    int rowCount = 0;   
	    Connection conn = null;   
	    try {
	        conn = getConnection();  
	        conn.setAutoCommit(false); 

	        // 
	        try (PreparedStatement stmt = conn.prepareStatement(
	            "INSERT INTO Contact (email, contact_number, specific_address, district, barangay) VALUES (?, ?, ?, ?, ?)")) {
	            stmt.setString(1, userContact.getEmail());
	            stmt.setString(2, userContact.getContact_number());
	            stmt.setString(3, userContact.getSpecific_address());
	            stmt.setString(4, userContact.getDistrict());
	            stmt.setString(5, userContact.getBarangay());
	            rowCount = stmt.executeUpdate();  // Execute the insert
	        }

	        if (rowCount > 0) {   
	            // Get contact_id from the "Contact" table
	            ResultSet rs = null;
	            int contactId = -1;
	            try (PreparedStatement stmt2 = conn.prepareStatement(
	                "SELECT contact_id FROM Contact WHERE email = ? AND contact_number = ?")) {
	                stmt2.setString(1, userContact.getEmail());
	                stmt2.setString(2, userContact.getContact_number());
	                rs = stmt2.executeQuery();  // Execute the query to get contact_id

	                if (rs.next()) {
	                    contactId = rs.getInt("contact_id");  // Get the contact_id
	                }
	            } finally {
	                if (rs != null) {
	                    rs.close();   // Always close ResultSet to free up resources
	                }
	            }

	            if (contactId != -1) {   // If we got the contact_id
	                // Insert the user details into the "USERS" table
	                try (PreparedStatement stmt3 = conn.prepareStatement(
	                    "INSERT INTO USERS (first_name, last_name, description, contact_id, password, icon, birth_date, employee_id) VALUES (?, ?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), 1)")) {
	                    stmt3.setString(1, user.getFname());
	                    stmt3.setString(2, user.getLname());
	                    stmt3.setString(3, user.getBio());
	                    stmt3.setInt(4, contactId);  // Use the contact_id retrieved
	                    stmt3.setString(5, user.getPassword());
	                    stmt3.setString(6, user.getIcon());
	                    stmt3.setString(7, user.getBday());
	                    stmt3.executeUpdate();  // Execute the insert into USERS table
	                }

	                conn.commit();  // Commit the transaction (save changes to the database)
	            } else {
	                conn.rollback();  // Rollback if we couldn't get the contact_id
	            }
	        } else {
	            conn.rollback();  // Rollback if the contact insert failed
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();  // Rollback if an error occurs
	            } catch (SQLException se) {
	                se.printStackTrace();
	            }
	        }
	    } finally {
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(true);  // Re-enable auto-commit for future operations
	                conn.close();  // Close the connection
	            } catch (SQLException se) {
	                se.printStackTrace();
	            }
	        }
	    }

	    return rowCount > 0;  // Return true if at least one row was inserted
	}

	
	
//	Gets the user info by ID/ return an object with all the personal info of a user
	public User getUserById(int userId) { // Class JobPosts functionName(variables)
		User user = null; // Initialize post with post with type JobPosts
		ResultSet rs = null; // Type ResultSet variable name

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER_BY_ID);) {

			preparedStatement.setInt(1, userId); // Replace the ? in the query above to the given userId

			rs = preparedStatement.executeQuery(); // Execute the query/ retrieve every field from that id

			if (rs.next()) { // If data is returned then:
				int id = rs.getInt("user_id");
				int contactId = rs.getInt("contact_id");
				// Specify id as an integer, then get the id from the results
				
				String fname = rs.getString("first_name");
				// Get string and store it into variables name
				
				String lname = rs.getString("last_name");
				String bio = rs.getString("description");
				String icon = rs.getString("icon");
				String bday = rs.getString("birth_date");
				String address = null;
				String district = null;
				String barangay = null;
				String cnumber = null;
				String email = null;
				
				try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contact where contact_id = ?");) {
					stmt.setInt(1, contactId);
					ResultSet rs2 = stmt.executeQuery();
					
					if (rs2.next()) {
						address = rs2.getString("specific_address");
						district = rs2.getString("district");
						barangay = rs2.getString("barangay");
						cnumber = rs2.getString("contact_number");
						email = rs2.getString("email");
					}
					
				}
				
				
				
				// All are the same with first name variable

				user = new User(id, fname, lname, icon, bio, bday, cnumber, address, district, barangay, email);
				// Create a new object with previously declared variables, and store it into post
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
		// if no error occurs, return the object to whatever file used the class method (JobPost)
		// It returns the post object which can then be utilized by whatever function in the code called it
	}
//	End of getting user ID
	
	
//	Gets the company's info by ID/ retrieves all info about the company through its ID
	public Company getCompanyById(int userId) {
		Company company = null;
		ResultSet rs = null;

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_COMPANY_BY_ID);) {

			preparedStatement.setInt(1, userId);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String name = rs.getString("company_name");
				String address = null;  // grab from company contact
				int company_contact = rs.getInt("contact_id"); // Initialize contact_id
				String desc = rs.getString("description");
				String icon = rs.getString("company_icon");
				String province = null;
				String city = null;
				// grab address info with contact_id
				try (PreparedStatement preparedStatement2 = conn.prepareStatement("SELECT specific_address, province,"
						+ " city FROM company_contact WHERE contact_id = ?");) {
					preparedStatement2.setInt(1, company_contact);
					
					ResultSet rs2 = preparedStatement2.executeQuery();
					
					if(rs2.next()) {
						address = rs2.getString("specific_address");
						city = rs2.getString("city");
						province = rs2.getString("province");
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				

				company = new Company(userId, name, desc, icon, address, city, province);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return company;
	}
//	End of company id
	
	
	public int initResume(Contact userContact) {
		int resumeId = 0;
		int rowCount = 0;
		ResultSet rs = null;
		try(Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT contact_id FROM contact WHERE email = ?");){
			stmt.setString(1, userContact.getEmail());
			rs = stmt.executeQuery();
				
			if (rs.next()) {
				int contactId = rs.getInt("contact_id");
				try(PreparedStatement stmt2 = conn.prepareStatement("SELECT user_id from users where contact_id = ?");) {
					stmt2.setInt(1, contactId);
					
					ResultSet rs2 = stmt2.executeQuery();
					if (rs2.next()) {
						int userId = rs2.getInt("user_id");
						try(PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO resume (user_id) VALUES (?)");) {
							stmt3.setInt(1, userId);
							rowCount = stmt3.executeUpdate();
							
							if (rowCount > 0) {
								try(PreparedStatement stmt4 = conn.prepareStatement("SELECT resume_id FROM resume where user_id = ?");){
									stmt4.setInt(1, userId);
									rs = stmt4.executeQuery();
									if (rs.next()) {
										resumeId = rs.getInt("resume_id");
									}
								}
							}
						
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resumeId;
	}
	
	public boolean insertSkills(Skills skillName, int resume) {
		int rowCount = 0;
		try(Connection conn = getConnection();
				PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO skills"
				+ " (name, resume_id)"
				+ " VALUES (?, ?)");) {
		stmt3.setString(1, skillName.getName());
		stmt3.setInt(2, resume);
		
		
		
		rowCount = stmt3.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rowCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean insertEduc(Education educ, int resume) {
		int rowCount = 0;
		try(Connection conn = getConnection();
				PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO education"
				+ " (start_year, end_year, school_name, degree, resume_id)"
				+ " VALUES (?, ?, ?, ?, ?)");) {
		stmt2.setInt(1, educ.getStart_year());
		stmt2.setInt(2, educ.getEnd_year());
		stmt2.setString(3, educ.getSchool_name());
		stmt2.setString(4, educ.getDegree());
		stmt2.setInt(5, resume);
		
		
		rowCount = stmt2.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rowCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean insertWorkHist(WorkHist hist, int resume) { //144
		int rowCount = 0;
		try (Connection conn = getConnection();){
				
			
			// Insert work history
			try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO work_hist"
						+ " (start_year, end_year, company_name, job_title, resume_id)"
						+ " VALUES (?, ?, ?, ?, ?)");) {
			stmt.setInt(1, hist.getStartYear());
			stmt.setInt(2, hist.getEndYear());
			stmt.setString(3, hist.getCompanyName());
			stmt.setString(4, hist.getJobTitle());
			stmt.setInt(5, resume);
			
			
			rowCount = stmt.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rowCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public boolean updateSkills(Skills skillName, int id) {
		int rowCount = 0;
		try(Connection conn = getConnection();
				PreparedStatement stmt3 = conn.prepareStatement("UPDATE skills"
				+ " SET name = ?"
				+ " WHERE skill_id = ?");) {
		stmt3.setString(1, skillName.getName());
		stmt3.setInt(2, id);
		
		
		
		rowCount = stmt3.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rowCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean updateEduc(Education educ, int id) {
		int rowCount = 0;
		try(Connection conn = getConnection();
				PreparedStatement stmt2 = conn.prepareStatement("UPDATE education"
				+ " SET start_year = ?, end_year = ?, school_name = ?, degree = ?"
				+ " WHERE education_id = ?");) {
		stmt2.setInt(1, educ.getStart_year());
		stmt2.setInt(2, educ.getEnd_year());
		stmt2.setString(3, educ.getSchool_name());
		stmt2.setString(4, educ.getDegree());
		stmt2.setInt(5, id);
		
		
		rowCount = stmt2.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rowCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean updateWorkHist(WorkHist hist, int id) { //144
		int rowCount = 0;
		try (Connection conn = getConnection();){
				
			
			// Insert work history
			try(PreparedStatement stmt = conn.prepareStatement("UPDATE work_hist"
						+ " SET start_year = ?, end_year = ?, company_name = ?, job_title = ?"
						+ " WHERE work_hist_id = ?");) {
			stmt.setInt(1, hist.getStartYear());
			stmt.setInt(2, hist.getEndYear());
			stmt.setString(3, hist.getCompanyName());
			stmt.setString(4, hist.getJobTitle());
			stmt.setInt(5, id);
			
			
			rowCount = stmt.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rowCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
//	For inserting a resume into the database
//	public int insertResume(User resume, int userId) { // So resume which is a User type variable
//		int rowCount = 0;
//		
//		try (Connection conn = getConnection();
//				PreparedStatement preparedStatement = conn.prepareStatement(INSERT_INTO_RESUME);) {
////			From that user object, we are able to retrieve information without putting much in the parameters
//			preparedStatement.setString(1, resume.getAddress());
//			preparedStatement.setString(2, resume.getWorkhist());
//			preparedStatement.setString(3, resume.getEduchist());
//			preparedStatement.setString(4, resume.getSkills());
//			preparedStatement.setString(5, resume.getResdesc());
//			preparedStatement.setInt(6, user.getId2());
//			rowCount = preparedStatement.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return rowCount;
//	}
//	End of insert resume
	
	
//	Update a user's resume
	public boolean updateResume(User resume) {
		boolean rowCount = false;

		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_RESUME)) {

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
				int companyid = rs.getInt("company_id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("post_date");

				String companyQuery = "SELECT company_name FROM Company WHERE company_id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("company_name");
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
				int id = rs.getInt("post_id");
				int companyid = rs.getInt("company_id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("post_date");

				String companyQuery = "SELECT company_name FROM Company WHERE company_id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("company_name");
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
	public List<JobPosts> selectAllJobsCompany(int companyId) {
		List<JobPosts> work = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_JOBS_COMPANY);) {
			preparedStatement.setInt(1, companyId);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("post_id");
				int companyid = rs.getInt("company_id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("post_date");

				final String companyQuery = "SELECT company_name FROM Company WHERE company_id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("company_name");
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
						.prepareStatement(INSERT_INTO_APPLICATIONS)) {

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

	
//	Used by companies, checking all applicants
	public int checkApply(int postId, int userId) throws SQLException { // checking if a user has already applied
		int exists = 0;
		try (Connection conn = getConnection();
				PreparedStatement statement = conn
						.prepareStatement(SELECT_ALL_APPLICATIONS)) {
			statement.setInt(1, userId);
			statement.setInt(2, postId);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				exists = 1; // already applied
			} else {
				exists = 0; // does not exist
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
	public void acceptApplicant(int id, int companyId) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(SELECT_COMPANY_BY_ID)) {
			statement.setInt(1, companyId);

			ResultSet rs = statement.executeQuery(); // Get every data of that company

			if (rs.next()) {
				int empId = rs.getInt("employee_id");
				
//				Sets the applicant's empID (in database) to the unique empID of that company
				PreparedStatement statement2 = conn.prepareStatement(UPDATE_USER_EMPID);
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
						.prepareStatement("DELETE FROM Applications WHERE user_id = ? AND post_id = ?")) {

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
