package sandbox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sandbox.model.Company;
import sandbox.model.Education;
import sandbox.model.Skills;
import sandbox.model.User;
import sandbox.model.WorkHist;

public class UserDAO {
	private static final String jdbcURL = "jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1";
	private static final String jdbcUsername = "sandbox";
	private static final String jdbcPassword = "sandboxUser";

	protected Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	
//	This should be in searchDAO? nvm not used as of Jan 14 14:51
//	public List<User> selectAllUsersSearch() throws SQLException {
//		List<User> users = new ArrayList<>();
//		String SELECT_ALL_USERS = "SELECT * FROM Users";
//		try (Connection conn = getConnection();
//				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_USERS)) {
//
//			ResultSet rs = preparedStatement.executeQuery();
//
//			while (rs.next()) {
//				int id = rs.getInt("id");
//				String fname = rs.getString("first_name");
//				String lname = rs.getString("lastname");
//				String district = rs.getString("district");
//				String barangay = rs.getString("barangay");
//				String bio = rs.getString("bio");
//				String icon = rs.getString("icon");
//
//				users.add(new User(id, fname, lname, district, barangay, bio, icon));
//			}
//		}
//		return users;
//
//	}
	
	
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
				String icon = rs.getString("company_icon");
				int contactId = rs.getInt("contact_id");
				String address = null;
				String city = null;
				String province = null;
				try (PreparedStatement stmt = conn.prepareStatement("SELECT * from company_contact where contact_id = ?");){
					stmt.setInt(1, contactId);
					address = rs.getString("specific_address");
					city = rs.getString("city");
					province = rs.getString("province");
					
				}
				

				company.add(new Company(id, name, desc, icon, address, city, province));
			}
		}
		return company;

	}

	
//	WHAT
//	selects every user (by id) in a certain post, then through user id, retrieves their data
//	probably used to display all applicants
//	public List<User> selectAllUsers(int postId) {
//		List<User> users = new ArrayList<>();
//		String SELECT_ALL_USERS_APP = "SELECT * FROM Applications WHERE post_Id = ?";
//		String SELECT_USER_INFO = "SELECT * FROM Users WHERE user_id = ?";
//		String SELECT_RESUME_INFO = "SELECT * FROM Resume WHERE user_Id = ?";
//
//	
//
//		try (Connection conn = getConnection();
//				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_USERS_APP)) {
//
//			preparedStatement.setInt(1, postId);
//			ResultSet rs = preparedStatement.executeQuery();
//
//			while (rs.next()) {
//				int userId = rs.getInt("user_id");
//
//				try (PreparedStatement preparedStatement2 = conn.prepareStatement(SELECT_USER_INFO)) {
//					preparedStatement2.setInt(1, userId);
//					ResultSet rs2 = preparedStatement2.executeQuery();
//
//					if (rs2.next()) {
//						String fname = rs2.getString("first_name");
//						String lname = rs2.getString("last_name");
//						int contactId = rs2.getInt("contact_id"); // make a contact id thing
//						String specific_address = null;
//						String district = null;
//						String barangay = null;
//						String cnumber = null;
////						String district = rs2.getString("district");
////						String barangay = rs2.getString("barangay");
//						
//						try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CONTACT WHERE contact_id = ?");){
//							stmt.setInt(1, contactId);
//							ResultSet rsC = stmt.executeQuery();
//									
//							if(rsC.next()) {
//								specific_address = rsC.getString("specific_address");
//								district = rsC.getString("district");
//								barangay = rsC.getString("barangay");
//								cnumber = rsC.getString("contact_number");
//								
//							}
//						}
//
////						try (PreparedStatement preparedStatement3 = conn.prepareStatement(SELECT_RESUME_INFO)) {
////							preparedStatement3.setInt(1, userId);
////							ResultSet rs3 = preparedStatement3.executeQuery();
////
////							if (rs3.next()) {
////								String address = rs3.getString("address");
////								String workhist = rs3.getString("workhist");
////								String educhist = rs3.getString("educhist");
////								String skills = rs3.getString("skills");
////								String resdesc = rs3.getString("resdesc");
//
//								users.add(new User(userId, fname, lname, cnumber, specific_address, district, barangay));
//							}
//				}
//					
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return users;
//	}
//}

	public List<User> selectAllUsers(int postId) {
	    List<User> users = new ArrayList<>();
	    List<WorkHist> workHist = new ArrayList<>();
	    List<Education> educ = new ArrayList<>();
	    List<Skills> skills = new ArrayList<>();
	    String SELECT_ALL_USERS_APP = "SELECT * FROM Applications WHERE post_id = ?";
	    String SELECT_USER_INFO = "SELECT * FROM Users WHERE user_id = ?";
	    String SELECT_RESUME_INFO = "SELECT * FROM Resume WHERE user_Id = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_USERS_APP)) {

	        preparedStatement.setInt(1, postId);
	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            int userId = rs.getInt("user_id");

	            try (PreparedStatement preparedStatement2 = conn.prepareStatement(SELECT_USER_INFO)) {
	                preparedStatement2.setInt(1, userId);
	                ResultSet rs2 = preparedStatement2.executeQuery();

	                if (rs2.next()) {
	                    String fname = rs2.getString("first_name");
	                    String lname = rs2.getString("last_name");
	                    int contactId = rs2.getInt("contact_id");
	                    String bio = rs2.getString("description");
	                    String specific_address = null;
	                    String district = null;
	                    String barangay = null;
	                    String cnumber = null;
	                    String email = null;

	                    try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CONTACT WHERE contact_id = ?")) {
	                        stmt.setInt(1, contactId);
	                        ResultSet rsC = stmt.executeQuery();

	                        if (rsC.next()) {
	                            specific_address = rsC.getString("specific_address");
	                            district = rsC.getString("district");
	                            barangay = rsC.getString("barangay");
	                            cnumber = rsC.getString("contact_number");
	                            email = rsC.getString("email");

	                            try (PreparedStatement stmtRes = conn.prepareStatement("SELECT * FROM resume where user_id = ?")) {
	                                stmtRes.setInt(1, userId);
	                                ResultSet rsr = stmtRes.executeQuery();

	                                if (rsr.next()) {
	                                    int resumeId = rsr.getInt("resume_id");

	                                    try (PreparedStatement stmtWork = conn.prepareStatement("SELECT * FROM work_hist where resume_id = ?")) {
	                                        stmtWork.setInt(1, resumeId);
	                                        ResultSet rsw = stmtWork.executeQuery();

	                                        while (rsw.next()) {
	                                            String jobTitle = rsw.getString("job_title");
	                                            String companyName = rsw.getString("company_name");
	                                            int startYear = rsw.getInt("start_year");
	                                            int endYear = rsw.getInt("end_year");
	                                            int workId = rsw.getInt("work_hist_id");

	                                            workHist.add(new WorkHist(jobTitle, companyName, startYear, endYear, workId));
	                                        }
	                                    } catch (Exception e) {
	                                        e.printStackTrace();
	                                    }

	                                    try (PreparedStatement stmtEduc = conn.prepareStatement("SELECT * FROM education where resume_id = ?")) {
	                                        stmtEduc.setInt(1, resumeId);
	                                        ResultSet rse = stmtEduc.executeQuery();

	                                        while (rse.next()) {
	                                            String schoolName = rse.getString("school_name");
	                                            String degree = rse.getString("degree");
	                                            int startYear = rse.getInt("start_year");
	                                            int endYear = rse.getInt("end_year");
	                                            int educId = rse.getInt("education_id");

	                                            educ.add(new Education(schoolName, degree, startYear, endYear, educId));
	                                        }
	                                    } catch (Exception e) {
	                                        e.printStackTrace();
	                                    }

	                                    try (PreparedStatement stmtSkills = conn.prepareStatement("SELECT * FROM skills where resume_id = ?")) {
	                                        stmtSkills.setInt(1, resumeId);
	                                        ResultSet rss = stmtSkills.executeQuery();

	                                        while (rss.next()) {
	                                            String skill = rss.getString("name");
	                                            int skillId = rss.getInt("skill_id");
	                                            skills.add(new Skills(skill, skillId));
	                                        }
	                                    } catch (Exception e) {
	                                        e.printStackTrace();
	                                    }
	                                }
	                            } catch (Exception e) {
	                                e.printStackTrace();
	                            }
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }

	                    users.add(new User(userId, fname, lname, bio, email, cnumber, specific_address, district, barangay, workHist, educ, skills));
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return users;
	}
	
	
	public User getUserForEdit(int userId) {
	    User user = null;
	    List<WorkHist> workHist = new ArrayList<>();
	    List<Education> educ = new ArrayList<>();
	    List<Skills> skills = new ArrayList<>();
	    
	    String SELECT_USER_INFO = "SELECT * FROM Users WHERE user_id = ?";
	    String SELECT_RESUME_INFO = "SELECT * FROM Resume WHERE user_Id = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER_INFO)) {

	        preparedStatement.setInt(1, userId);
	        ResultSet rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            String fname = rs.getString("first_name");
	            String lname = rs.getString("last_name");
	            int contactId = rs.getInt("contact_id");
	            String bio = rs.getString("description");
	            String specific_address = null;
	            String district = null;
	            String barangay = null;
	            String cnumber = null;

	            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CONTACT WHERE contact_id = ?")) {
	                stmt.setInt(1, contactId);
	                ResultSet rsC = stmt.executeQuery();

	                if (rsC.next()) {
	                    specific_address = rsC.getString("specific_address");
	                    district = rsC.getString("district");
	                    barangay = rsC.getString("barangay");
	                    cnumber = rsC.getString("contact_number");

	                    try (PreparedStatement stmtRes = conn.prepareStatement("SELECT * FROM resume WHERE user_id = ?")) {
	                        stmtRes.setInt(1, userId);
	                        ResultSet rsr = stmtRes.executeQuery();

	                        if (rsr.next()) {
	                            int resumeId = rsr.getInt("resume_id");

	                            // Work History
	                            try (PreparedStatement stmtWork = conn.prepareStatement("SELECT * FROM work_hist WHERE resume_id = ?")) {
	                                stmtWork.setInt(1, resumeId);
	                                ResultSet rsw = stmtWork.executeQuery();
	                                while (rsw.next()) {
	                                	int workId = rsw.getInt("work_hist_id");
	                                    String jobTitle = rsw.getString("job_title");
	                                    String companyName = rsw.getString("company_name");
	                                    int startYear = rsw.getInt("start_year");
	                                    int endYear = rsw.getInt("end_year");
	                                    workHist.add(new WorkHist(jobTitle, companyName, startYear, endYear, workId));
	                                }
	                            }

	                            // Education
	                            try (PreparedStatement stmtEduc = conn.prepareStatement("SELECT * FROM education WHERE resume_id = ?")) {
	                                stmtEduc.setInt(1, resumeId);
	                                ResultSet rse = stmtEduc.executeQuery();
	                                while (rse.next()) {
	                                	int educId = rse.getInt("education_id");
	                                    String schoolName = rse.getString("school_name");
	                                    String degree = rse.getString("degree");
	                                    int startYear = rse.getInt("start_year");
	                                    int endYear = rse.getInt("end_year");
	                                    educ.add(new Education(schoolName, degree, startYear, endYear, educId));
	                                }
	                            }

	                            // Skills
	                            try (PreparedStatement stmtSkills = conn.prepareStatement("SELECT * FROM skills WHERE resume_id = ?")) {
	                                stmtSkills.setInt(1, resumeId);
	                                ResultSet rss = stmtSkills.executeQuery();
	                                while (rss.next()) {
	                                    String skill = rss.getString("name");
	                                    int skillId = rss.getInt("skill_id");
	                                    skills.add(new Skills(skill, skillId));
	                                }
	                            }
	                        }
	                    }
	                }
	            }

	            user = new User(userId, fname, lname, bio, cnumber, specific_address, district, barangay, workHist, educ, skills);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return user;
	}

}
