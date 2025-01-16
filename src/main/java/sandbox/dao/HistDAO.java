package sandbox.dao;

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
import sandbox.model.Education;
import sandbox.model.JobPosts;
import sandbox.model.Skills;
import sandbox.model.User;
import sandbox.model.UserEdit;
import sandbox.model.WorkHist;

public class HistDAO {
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
	
	public int getCompanyIdByPost(int postId) throws SQLException {
		int companyId = 0;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT company_id FROM job_posts where post_id = ?")){
			stmt.setInt(1, postId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				companyId = rs.getInt("company_id");
			}
		}
		return companyId;
	}

	
//	UserEdit user = new UserEdit(userId, fname, lname, bday, cnumber, desc, specificAddress, barangay, district);
	
	public boolean updateResume(UserEdit user) {
	    int rowCount = 0;
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(
	             "UPDATE users SET first_name = ?, last_name = ?, birth_date = TO_DATE(?, 'YYYY-MM-DD'), description = ? WHERE user_id = ?")) {
	        
	        stmt.setString(1, user.getFname());
	        stmt.setString(2, user.getLname());
	        stmt.setString(3, user.getBday());
	        stmt.setString(4, user.getDesc());
	        stmt.setInt(5, user.getUserId());

	        rowCount = stmt.executeUpdate();
	        if (rowCount > 0) { // if rows were affected or updated
	            try (PreparedStatement stmt2 = conn.prepareStatement(
	                "SELECT contact_id FROM users WHERE user_id = ?")) { // grab contact id
	                stmt2.setInt(1, user.getUserId());
	                ResultSet rs = stmt2.executeQuery();
	                
	                if (rs.next()) {
	                    int contactId = rs.getInt("contact_id");
	                    try (PreparedStatement stmt3 = conn.prepareStatement(
	                        "UPDATE contact SET contact_number = ?, specific_address = ?, barangay = ?, district = ? WHERE contact_id = ?")) {
	                        stmt3.setString(1, user.getCnumber());
	                        stmt3.setString(2, user.getAddress());
	                        stmt3.setString(3, user.getbarangay());
	                        stmt3.setString(4, user.getDistrict());
	                        stmt3.setInt(5, contactId);

	                        rowCount = stmt3.executeUpdate();
	                    }
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return rowCount > 0;
	}
	// This is user update
	
	public int getResumeId(int userId) {
		int resumeId = 0;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT resume_id FROM resume where user_id = ?");){
			stmt.setInt(1,  userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				resumeId = rs.getInt("resume_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resumeId;
	}

	public List<WorkHist> getWork(int userId) {
		int resumeId = getResumeId(userId);
		List<WorkHist> workHist = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * from work_hist where resume_id = ?");){
				stmt.setInt(1, resumeId);
				String jobTitle = null;
				String companyName = null;
				int startYearStr = 0;
				int endYearStr = 0;
				int workId = 0;
				
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					jobTitle = rs.getString("job_title");
					companyName = rs.getString("company_name");
					startYearStr = rs.getInt("start_year");
					endYearStr = rs.getInt("end_year");
					workId = rs.getInt("work_hist_id");
					
					workHist.add(new WorkHist(jobTitle, companyName, startYearStr, endYearStr, workId));
				}
				
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workHist;
	}
	
	public List<Education> getEduc(int userId) {
		int resumeId = getResumeId(userId);
		List<Education> educ = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * from education where resume_id = ?");){
				stmt.setInt(1, resumeId);
				String schoolName = null;
				String degree = null;
				int startYearStr = 0;
				int endYearStr = 0;
				int educId = 0;
				
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					schoolName = rs.getString("school_name");
					degree = rs.getString("degree");
					startYearStr = rs.getInt("start_year");
					endYearStr = rs.getInt("end_year");
					educId = rs.getInt("education_id");
					
					educ.add(new Education(schoolName, degree, startYearStr, endYearStr, educId));
				}
				
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return educ;
	}
	
	public List<Skills> getSkills(int userId) { // how is usedId retrieved - through the idk same process with the servlet u what pag mag sleep lu yakapi sobr mm kis
		int resumeId = getResumeId(userId);
		List<Skills> skill = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * from skills where resume_id = ?");){
				stmt.setInt(1, resumeId);
				String skillName = null;
				int skillId = 0;
				
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					skillName = rs.getString("name");
					skillId = rs.getInt("skill_id");
					skill.add(new Skills(skillName, skillId));
				}
				
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return skill;
	}


	public void deleteWorkExperience(int workId) throws SQLException {
		// TODO Auto-generated method stub
		try(Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM work_hist where work_hist_id = ?")) {
			stmt.setInt(1, workId);
			stmt.executeUpdate();
		}
	}


	public void deleteEducation(int educId) throws SQLException {
		// TODO Auto-generated method stub
		try(Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM education where education_id = ?")) {
			stmt.setInt(1, educId);
			stmt.executeUpdate();
		}
	}


	public void deleteSkill(int skillId) throws SQLException {
		// TODO Auto-generated method stub
		try(Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM skills where skill_id = ?")) {
			stmt.setInt(1, skillId);
			stmt.executeUpdate();
		}
	}
	
	
	
}