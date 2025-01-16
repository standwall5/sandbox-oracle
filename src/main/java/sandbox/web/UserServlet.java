package sandbox.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import oracle.jdbc.datasource.impl.OracleDataSource;
import sandbox.dao.HistDAO;
import sandbox.dao.PostDAO;
import sandbox.dao.SearchDAO;
import sandbox.dao.UserDAO;
import sandbox.model.Company;
import sandbox.model.Contact;
import sandbox.model.Education;
import sandbox.model.JobPosts;
import sandbox.model.ResumeId;
import sandbox.model.Skills;
import sandbox.model.User;
import sandbox.model.UserEdit;
import sandbox.model.UserLogin;
import sandbox.model.WorkHist;

/**
 * Servlet implementation class UserServlet
 */

@WebServlet("/")
@MultipartConfig(
	    location = "/tmp",  // Temporary directory for file uploads
	    maxFileSize = 1024 * 1024 * 10,  // 10 MB
	    maxRequestSize = 1024 * 1024 * 20,  // 20 MB
	    fileSizeThreshold = 1024 * 1024  // 1 MB
	)
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private PostDAO postDAO;
	private SearchDAO searchDAO;
	private HistDAO histDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		this.userDAO = new UserDAO(); // I guess through UserServlet, we are importing every DAO function?
		this.postDAO = new PostDAO();
		this.searchDAO = new SearchDAO();
		this.histDAO = new HistDAO();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) { // Don't know where this is accessed yet honestly, probably put in the JSP form submits
		case "/registerLast": // 2 reg commands? this is prolly not used
			registerUser(request, response);
			break;
		case "/registerInfo": // This is used in registerDetails.jsp
			registerInfo(request, response);
			break;
		case "/RegisterCompany":
			try {
				insertCompany(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
//		case "/RegisterCompanyUser":
//			insertCompanyUser(request, response);
//			break;
		case "/makePost":
			try {
				createPost(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/createResume":
			try {
				createResume(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
//		case "/updateResume":
//			try {
//				updateResume(request, response);
//			} catch (SQLException | IOException | ServletException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			break;
		
		case "/updateProfile":
			try {
				updateProfile(request, response);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			try {
				homepage(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	private void withdrawApp(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		System.out.println("withraw accessed");
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		int workId = Integer.parseInt(request.getParameter("id"));
		try {
			postDAO.rejectApplicant(userId, workId);
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write("success");
			out.flush();
		} catch (SQLException e) {

			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Failed to apply work.");
		}
		
	}

	private void updateProfileForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		System.out.println("servlet accessed");
		// Assuming you already have a valid userId
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = userDAO.getUserForEdit(userId);  // This method fetches the user and their data
		if (user != null) {
		    request.setAttribute("user", user);
		    session.setAttribute("userForm", user);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("editProfile.jsp");
		    dispatcher.forward(request, response);
		} else {
		    // Handle the case where user is not found
		    response.sendRedirect("errorPage.jsp");
		}
		
	}

	private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		int resumeId = histDAO.getResumeId(userId);
		
		// **************************************************
		//				DELETION LOGIC
		// **************************************************
		
		String removedWorkIdsStr = request.getParameter("removedWorkIds"); 
		String removedEducIdsStr = request.getParameter("removedEducIds"); 
		String removedSkillIdsStr = request.getParameter("removedSkillIds"); 

		// Work Deletion Logic
		if (removedWorkIdsStr != null && !removedWorkIdsStr.isEmpty()) {
		    // Convert the comma-separated string to an integer array
		    int[] removedWorkIds = Arrays.stream(removedWorkIdsStr.split(","))
		                                  .mapToInt(Integer::parseInt)
		                                  .toArray();

		    // Perform the work experience deletion logic
		    for (int workId : removedWorkIds) {
		        // Call the DAO method to delete the work experience with the given workId
		        histDAO.deleteWorkExperience(workId);
		    }
		} else {
		    System.out.println("No work experience IDs to delete.");
		}

		// Education Deletion Logic
		if (removedEducIdsStr != null && !removedEducIdsStr.isEmpty()) {
		    // Convert the comma-separated string to an integer array
		    int[] removedEducIds = Arrays.stream(removedEducIdsStr.split(","))
		                                 .mapToInt(Integer::parseInt)
		                                 .toArray();

		    // Perform the education deletion logic
		    for (int educId : removedEducIds) {
		        // Call the DAO method to delete the education with the given educId
		        histDAO.deleteEducation(educId);
		    }
		} else {
		    System.out.println("No education IDs to delete.");
		}

		// Skill Deletion Logic
		if (removedSkillIdsStr != null && !removedSkillIdsStr.isEmpty()) {
		    // Convert the comma-separated string to an integer array
		    int[] removedSkillIds = Arrays.stream(removedSkillIdsStr.split(","))
		                                  .mapToInt(Integer::parseInt)
		                                  .toArray();

		    // Perform the skill deletion logic
		    for (int skillId : removedSkillIds) {
		        // Call the DAO method to delete the skill with the given skillId
		        histDAO.deleteSkill(skillId);
		    }
		} else {
		    System.out.println("No skill IDs to delete.");
		}

		
		// **************************************************
		//				SET UPDATE USER DETAILS
		// **************************************************
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String cnumber = request.getParameter("cnumber");
		String bday  = request.getParameter("birthday");
		String desc = request.getParameter("desc");
		String specificAddress = request.getParameter("address");
		String district = request.getParameter("district");
		String barangay = request.getParameter("barangay");
		
		// **************************************************
		//			UPDATE RESUME DETAILS
		// **************************************************
		
		// This refers to the already existing data the user has
		// Existing data for work history, education, and skills
		// Retrieve the hidden input values for IDs
		// Get the User object from the session
		// Retrieve the User object from the session
//		User userList = (User) session.getAttribute("userForm");
//
//		// Check if userList is null
//		if (userList == null) {
//		    System.out.println("User object is null. Please check session initialization.");
//		} else {
//		    // Get the lists of workHistory, education, and skills from the User object
//		    List<WorkHist> workHistoryList = userList.getWorkHistory();
//		    List<Education> educationList = userList.getEducation();
//		    List<Skills> skillsList = userList.getSkills();
//
//		    System.out.println("Work History List: " + workHistoryList.size());
//		    System.out.println("Education List: " + educationList.size());
//		    System.out.println("Skills List: " + skillsList.size());
//
//		    // Initialize arrays to store the IDs for work, education, and skills
//		    int[] workIds = new int[workHistoryList.size()];
//		    int[] educIds = new int[educationList.size()];
//		    int[] skillIds = new int[skillsList.size()];
//
//		    // Extract the workIds, educIds, and skillIds from the lists
//		    for (int i = 0; i < workHistoryList.size(); i++) {
//		        workIds[i] = workHistoryList.get(i).getWorkId(); // Get the workId from the WorkHist object
//		    }
//
//		    for (int i = 0; i < educationList.size(); i++) {
//		        educIds[i] = educationList.get(i).getEducId(); // Get the educId from the Education object
//		    }
//
//		    for (int i = 0; i < skillsList.size(); i++) {
//		        skillIds[i] = skillsList.get(i).getSkillId(); // Get the skillId from the Skills object
//		    }
//
//		    System.out.println("Work IDs: " + Arrays.toString(workIds));
//		    System.out.println("Education IDs: " + Arrays.toString(educIds));
//		    System.out.println("Skill IDs: " + Arrays.toString(skillIds));
//
//		    // Retrieve the existing data for work, education, and skills
//		    String[] jobTitlesExist = request.getParameterValues("job_title_exist[]");
//		    String[] companyNamesExist = request.getParameterValues("company_name_exist[]");
//		    String[] startYearsStrExist = request.getParameterValues("start_year_exist[]");
//		    String[] endYearsStrExist = request.getParameterValues("end_year_exist[]");
//
//		    String[] schoolNamesExist = request.getParameterValues("school_name_exist[]");
//		    String[] degreesExist = request.getParameterValues("degree_exist[]");
//		    String[] schoolStartYearsStrExist = request.getParameterValues("school_start_year_exist[]");
//		    String[] schoolEndYearsStrExist = request.getParameterValues("school_end_year_exist[]");
//
//		    String[] skillsExist = request.getParameterValues("skill_exist[]");
//
//		    // Check if parameters are null or empty
//		    if (jobTitlesExist == null || companyNamesExist == null || startYearsStrExist == null || endYearsStrExist == null) {
//		        System.out.println("Missing work history data.");
//		    }
//		    if (schoolNamesExist == null || degreesExist == null || schoolStartYearsStrExist == null || schoolEndYearsStrExist == null) {
//		        System.out.println("Missing education data.");
//		    }
//		    if (skillsExist == null) {
//		        System.out.println("Missing skills data.");
//		    }
//
//		    // Update Work History
//		    if (workIds.length > 0 && jobTitlesExist != null && jobTitlesExist.length > 0) {
//		        System.out.println("Updating Work History...");
//		        for (int i = 0; i < workIds.length; i++) {
//		            int workId = workIds[i];
//		            String jobTitle = jobTitlesExist[i];
//		            String companyName = companyNamesExist[i];
//		            int startYear = (startYearsStrExist[i] != null && !startYearsStrExist[i].isEmpty())
//		                    ? Integer.parseInt(startYearsStrExist[i]) : 0;
//		            int endYear = (endYearsStrExist[i] != null && !endYearsStrExist[i].isEmpty())
//		                    ? Integer.parseInt(endYearsStrExist[i]) : 0;
//
//		            // Map the data to the DAO and update
//		            WorkHist workHistExist = new WorkHist(jobTitle, companyName, startYear, endYear);
//		            boolean success = postDAO.updateWorkHist(workHistExist, workId);
//		            System.out.println("Work history update result for workId " + workId + ": " + success);
//		        }
//		    } else {
//		        System.out.println("No work history to update.");
//		    }
//
//		    // Update Education
//		    if (educIds.length > 0 && schoolNamesExist != null && schoolNamesExist.length > 0) {
//		        System.out.println("Updating Education...");
//		        for (int i = 0; i < educIds.length; i++) {
//		            int educId = educIds[i];
//		            String schoolName = schoolNamesExist[i];
//		            String degree = degreesExist[i];
//		            int schoolStartYear = (schoolStartYearsStrExist[i] != null && !schoolStartYearsStrExist[i].isEmpty())
//		                    ? Integer.parseInt(schoolStartYearsStrExist[i]) : 0;
//		            int schoolEndYear = (schoolEndYearsStrExist[i] != null && !schoolEndYearsStrExist[i].isEmpty())
//		                    ? Integer.parseInt(schoolEndYearsStrExist[i]) : 0;
//
//		            // Map the data to the DAO and update
//		            Education educationExist = new Education(schoolName, degree, schoolStartYear, schoolEndYear);
//		            boolean success = postDAO.updateEduc(educationExist, educId);
//		            System.out.println("Education update result for educId " + educId + ": " + success);
//		        }
//		    } else {
//		        System.out.println("No education to update.");
//		    }
//
//		    // Update Skills
//		    if (skillIds.length > 0 && skillsExist != null && skillsExist.length > 0) {
//		        System.out.println("Updating Skills...");
//		        for (int i = 0; i < skillIds.length; i++) {
//		            int skillId = skillIds[i];
//		            String skillName = skillsExist[i];
//
//		            // Map the data to the DAO and update
//		            Skills skillExist = new Skills(skillName);
//		            boolean success = postDAO.updateSkills(skillExist, skillId);
//		            System.out.println("Skills update result for skillId " + skillId + ": " + success);
//		        }
//		    } else {
//		        System.out.println("No skills to update.");
//		    }
//		}




//		End of updating existing entries
        
        
     // **************************************************
     //			INSERT NEW RESUME DETAILS
     // **************************************************
        
        //This is for new entries
		// Retrieve job-related input
		// Retrieve job-related input
//		String[] jobTitles = request.getParameterValues("job_title[]");
//		String[] companyNames = request.getParameterValues("company_name[]");
//		String[] startYearsStr = request.getParameterValues("start_year[]");
//		String[] endYearsStr = request.getParameterValues("end_year[]");
//
//		System.out.println("Received job titles: " + Arrays.toString(jobTitles));
//		System.out.println("Received company names: " + Arrays.toString(companyNames));
//		System.out.println("Received start years: " + Arrays.toString(startYearsStr));
//		System.out.println("Received end years: " + Arrays.toString(endYearsStr));
//
//		if (jobTitles != null && jobTitles.length > 0) {
//		    int[] startYears = new int[startYearsStr.length];
//		    int[] endYears = new int[endYearsStr.length];
//
//		    for (int i = 0; i < startYearsStr.length; i++) {
//		        startYears[i] = startYearsStr[i].isEmpty() ? -1 : Integer.parseInt(startYearsStr[i]);
//		        endYears[i] = endYearsStr[i].isEmpty() ? -1 : Integer.parseInt(endYearsStr[i]);
//		    }
//
//		    List<WorkHist> existingWorkHistory = userList.getWorkHistory();
//		    System.out.println("Existing work history size: " + existingWorkHistory.size());
//
//		    // Insert new work history entries only if there is new data to insert
//		    for (int i = 0; i < jobTitles.length; i++) {
//		        if (jobTitles[i] != null && !jobTitles[i].isEmpty()) {
//		            boolean alreadyExists = false;
//		            // Check if the job already exists in the work history
//		            for (WorkHist workHist : existingWorkHistory) {
//		                if (workHist.getJobTitle().equals(jobTitles[i]) && workHist.getCompanyName().equals(companyNames[i])) {
//		                    alreadyExists = true;
//		                    break;
//		                }
//		            }
//		            if (!alreadyExists) {
//		                WorkHist workHist = new WorkHist(jobTitles[i], companyNames[i], startYears[i], endYears[i]);
//		                postDAO.insertWorkHist(workHist, resumeId);
//		                System.out.println("Inserted new work history entry: " + jobTitles[i] + " at " + companyNames[i]);
//		            }
//		        }
//		    }
//		} else {
//		    System.out.println("No job titles provided, skipping work history.");
//		}
//
//		// Now handle school-related input
//		String[] schoolNames = request.getParameterValues("school_name[]");
//		String[] degrees = request.getParameterValues("degree[]");
//		String[] schoolStartYearsStr = request.getParameterValues("school_start_year[]");
//		String[] schoolEndYearsStr = request.getParameterValues("school_end_year[]");
//
//		System.out.println("Received school names: " + Arrays.toString(schoolNames));
//		System.out.println("Received degrees: " + Arrays.toString(degrees));
//		System.out.println("Received school start years: " + Arrays.toString(schoolStartYearsStr));
//		System.out.println("Received school end years: " + Arrays.toString(schoolEndYearsStr));
//
//		if (schoolNames != null && schoolNames.length > 0) {
//		    int[] schoolStartYears = new int[schoolStartYearsStr.length];
//		    int[] schoolEndYears = new int[schoolEndYearsStr.length];
//
//		    for (int i = 0; i < schoolStartYearsStr.length; i++) {
//		        schoolStartYears[i] = schoolStartYearsStr[i].isEmpty() ? -1 : Integer.parseInt(schoolStartYearsStr[i]);
//		        schoolEndYears[i] = schoolEndYearsStr[i].isEmpty() ? -1 : Integer.parseInt(schoolEndYearsStr[i]);
//		    }
//
//		    List<Education> existingEducation = userList.getEducation();
//		    System.out.println("Existing education size: " + existingEducation.size());
//
//		    // Insert new education entries only if there is new data to insert
//		    for (int i = 0; i < schoolNames.length; i++) {
//		        if (schoolNames[i] != null && !schoolNames[i].isEmpty()) {
//		            boolean alreadyExists = false;
//		            // Check if the school already exists in the education history
//		            for (Education education : existingEducation) {
//		                if (education.getSchool_name().equals(schoolNames[i]) && education.getDegree().equals(degrees[i])) {
//		                    alreadyExists = true;
//		                    break;
//		                }
//		            }
//		            if (!alreadyExists) {
//		                Education education = new Education(schoolNames[i], degrees[i], schoolStartYears[i], schoolEndYears[i]);
//		                postDAO.insertEduc(education, resumeId);
//		                System.out.println("Inserted new education entry: " + schoolNames[i] + " with degree " + degrees[i]);
//		            }
//		        }
//		    }
//		} else {
//		    System.out.println("No school data provided, skipping education.");
//		}
//
//		// Finally, handle skills input
//		String[] skills = request.getParameterValues("skill[]");
//		System.out.println("Received skills: " + Arrays.toString(skills));
//
//		if (skills != null && skills.length > 0) {
//		    List<Skills> existingSkills = userList.getSkills();
//		    System.out.println("Existing skills size: " + existingSkills.size());
//
//		    // Insert new skills only if there is new data to insert
//		    for (String skill : skills) {
//		        if (skill != null && !skill.isEmpty()) {
//		            boolean alreadyExists = false;
//		            // Check if the skill already exists in the skills list
//		            for (Skills existingSkill : existingSkills) {
//		                if (existingSkill.getName().equals(skill)) {
//		                    alreadyExists = true;
//		                    break;
//		                }
//		            }
//		            if (!alreadyExists) {
//		                Skills skillName = new Skills(skill);
//		                postDAO.insertSkills(skillName, resumeId);
//		                System.out.println("Inserted new skill: " + skill);
//		            }
//		        }
//		    }
//		} else {
//		    System.out.println("No skills provided, skipping skills section.");
//		}
		
		// **************************************************
		// *			INSERT AND UPDATE					*
		// **************************************************
		// Retrieve existing IDs from the hidden input fields
		// Debugging logs
		
		String workIdsStr = request.getParameter("workIds"); // Comma-separated work IDs
		String educIdsStr = request.getParameter("educIds"); // Comma-separated education IDs
		String skillIdsStr = request.getParameter("skillIds"); // Comma-separated skill IDs

		// Split the strings into arrays of integers
		int[] workIds = (workIdsStr != null && !workIdsStr.isEmpty())
		        ? Arrays.stream(workIdsStr.split(",")).mapToInt(Integer::parseInt).toArray()
		        : new int[0];
		int[] educIds = (educIdsStr != null && !educIdsStr.isEmpty())
		        ? Arrays.stream(educIdsStr.split(",")).mapToInt(Integer::parseInt).toArray()
		        : new int[0];
		int[] skillIds = (skillIdsStr != null && !skillIdsStr.isEmpty())
		        ? Arrays.stream(skillIdsStr.split(",")).mapToInt(Integer::parseInt).toArray()
		        : new int[0];
		
		System.out.println("Existing Work IDs: " + Arrays.toString(workIds));
		System.out.println("Existing Education IDs: " + Arrays.toString(educIds));
		System.out.println("Existing Skill IDs: " + Arrays.toString(skillIds));

		// Handle updates for existing work history
		String[] jobTitlesExist = request.getParameterValues("job_title_exist[]");
		String[] companyNamesExist = request.getParameterValues("company_name_exist[]");
		String[] startYearsStrExist = request.getParameterValues("start_year_exist[]");
		String[] endYearsStrExist = request.getParameterValues("end_year_exist[]");

		if (workIds.length > 0 && jobTitlesExist != null) {
		    System.out.println("Updating Work History...");
		    for (int i = 0; i < workIds.length; i++) {
		        if (i < jobTitlesExist.length) { // Ensure the index matches
		            int workId = workIds[i];
		            String jobTitle = jobTitlesExist[i];
		            String companyName = companyNamesExist[i];
		            int startYear = (startYearsStrExist[i] != null && !startYearsStrExist[i].isEmpty())
		                    ? Integer.parseInt(startYearsStrExist[i]) : 0;
		            int endYear = (endYearsStrExist[i] != null && !endYearsStrExist[i].isEmpty())
		                    ? Integer.parseInt(endYearsStrExist[i]) : 0;

		            WorkHist workHistExist = new WorkHist(jobTitle, companyName, startYear, endYear);
		            postDAO.updateWorkHist(workHistExist, workId);
		            System.out.println("Updated work history entry with ID " + workId);
		        } else {
		            System.out.println("Index mismatch: Skipping update for work ID at index " + i);
		        }
		    }
		}

		// Handle new work history
		String[] jobTitles = request.getParameterValues("job_title[]");
		String[] companyNames = request.getParameterValues("company_name[]");
		String[] startYearsStr = request.getParameterValues("start_year[]");
		String[] endYearsStr = request.getParameterValues("end_year[]");

		if (jobTitles != null) {
		    System.out.println("Inserting New Work History...");
		    int[] startYears = new int[jobTitles.length];
		    int[] endYears = new int[jobTitles.length];

		    for (int i = 0; i < jobTitles.length; i++) {
		        startYears[i] = startYearsStr[i].isEmpty() ? -1 : Integer.parseInt(startYearsStr[i]);
		        endYears[i] = endYearsStr[i].isEmpty() ? -1 : Integer.parseInt(endYearsStr[i]);

		        if (!jobTitles[i].isEmpty()) {
		            WorkHist workHist = new WorkHist(jobTitles[i], companyNames[i], startYears[i], endYears[i]);
		            postDAO.insertWorkHist(workHist, resumeId);
		            System.out.println("Inserted new work history entry: " + jobTitles[i]);
		        }
		    }
		}

		// Handle updates for existing education
		String[] schoolNamesExist = request.getParameterValues("school_name_exist[]");
		String[] degreesExist = request.getParameterValues("degree_exist[]");
		String[] schoolStartYearsStrExist = request.getParameterValues("school_start_year_exist[]");
		String[] schoolEndYearsStrExist = request.getParameterValues("school_end_year_exist[]");

		if (educIds.length > 0 && schoolNamesExist != null) {
		    System.out.println("Updating Education...");
		    for (int i = 0; i < educIds.length; i++) {
		        if (i < schoolNamesExist.length) {
		            int educId = educIds[i];
		            String schoolName = schoolNamesExist[i];
		            String degree = degreesExist[i];
		            int schoolStartYear = (schoolStartYearsStrExist[i] != null && !schoolStartYearsStrExist[i].isEmpty())
		                    ? Integer.parseInt(schoolStartYearsStrExist[i]) : 0;
		            int schoolEndYear = (schoolEndYearsStrExist[i] != null && !schoolEndYearsStrExist[i].isEmpty())
		                    ? Integer.parseInt(schoolEndYearsStrExist[i]) : 0;

		            Education educationExist = new Education(schoolName, degree, schoolStartYear, schoolEndYear);
		            postDAO.updateEduc(educationExist, educId);
		            System.out.println("Updated education entry with ID " + educId);
		        } else {
		            System.out.println("Index mismatch: Skipping update for education ID at index " + i);
		        }
		    }
		}

		// Handle new education
		String[] schoolNames = request.getParameterValues("school_name[]");
		String[] degrees = request.getParameterValues("degree[]");
		String[] schoolStartYearsStr = request.getParameterValues("school_start_year[]");
		String[] schoolEndYearsStr = request.getParameterValues("school_end_year[]");

		if (schoolNames != null) {
		    System.out.println("Inserting New Education...");
		    for (int i = 0; i < schoolNames.length; i++) {
		        if (!schoolNames[i].isEmpty()) {
		            int schoolStartYear = schoolStartYearsStr[i].isEmpty() ? -1 : Integer.parseInt(schoolStartYearsStr[i]);
		            int schoolEndYear = schoolEndYearsStr[i].isEmpty() ? -1 : Integer.parseInt(schoolEndYearsStr[i]);

		            Education education = new Education(schoolNames[i], degrees[i], schoolStartYear, schoolEndYear);
		            postDAO.insertEduc(education, resumeId);
		            System.out.println("Inserted new education entry: " + schoolNames[i]);
		        }
		    }
		}

		// Handle updates for existing skills
		String[] skillsExist = request.getParameterValues("skill_exist[]");

		if (skillIds.length > 0 && skillsExist != null) {
		    System.out.println("Updating Skills...");
		    for (int i = 0; i < skillIds.length; i++) {
		        if (i < skillsExist.length) {
		            int skillId = skillIds[i];
		            String skillName = skillsExist[i];

		            Skills skillExist = new Skills(skillName);
		            postDAO.updateSkills(skillExist, skillId);
		            System.out.println("Updated skill entry with ID " + skillId + ": " + skillName);
		        } else {
		            System.out.println("Index mismatch: Skipping update for skill ID at index " + i);
		        }
		    }
		}

		// Handle new skills
		String[] skills = request.getParameterValues("skill[]");

		if (skills != null) {
		    System.out.println("Inserting New Skills...");
		    for (String skillName : skills) {
		        if (!skillName.isEmpty()) {
		            Skills newSkill = new Skills(skillName);
		            postDAO.insertSkills(newSkill, resumeId);
		            System.out.println("Inserted new skill: " + skillName);
		        }
		    }
		}




       
		// **************************************************
		// *				FINAL STAGES			    	*
		// **************************************************
		
		UserEdit user = new UserEdit(userId, fname, lname, bday, cnumber, desc, specificAddress, barangay, district);
		boolean isUpdated = histDAO.updateResume(user);
		if (isUpdated) {
	        // Redirect to a success page
			request.setAttribute("successMessage", "Edit successful");
	        response.sendRedirect("updateProfileForm?id="+userId);
	    } else {
	        // Redirect to an error page or show an error message
	    	System.out.println("what");
	    	request.setAttribute("errorMessage", "Error occured, please try again.");
	    	response.sendRedirect("updateProfileForm?id="+userId);
	    }
	}

	public void createResume(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		 HttpSession session = request.getSession(true);
		String[] jobTitles = request.getParameterValues("job_title[]");
		String[] companyNames = request.getParameterValues("company_name[]");
		String[] startYearsStr = request.getParameterValues("start_year[]");
		String[] endYearsStr = request.getParameterValues("end_year[]");
		int[] startYears = new int[startYearsStr.length];
		int[] endYears = new int[endYearsStr.length];
//		int userId = UserLogin.getId2();
		
		for (int i = 0; i < startYearsStr.length; i++) {
			if (!startYearsStr[i].isEmpty()) {
	            startYears[i] = Integer.parseInt(startYearsStr[i]);
	        } else {
	            // Handle the case when the start year is empty (set a default value or handle the error)
	            startYears[i] = 0;  // Example: Default to 0 or handle as needed
	        }

	        if (!endYearsStr[i].isEmpty()) {
	            endYears[i] = Integer.parseInt(endYearsStr[i]);
	        } else {
	            // Handle the case when the end year is empty
	            endYears[i] = 0;  // Example: Default to 0 or handle as needed
	        }
		}
		
		 String[] schoolNames = request.getParameterValues("school_name[]");
	     String[] degrees = request.getParameterValues("degree[]");
	     String[] schoolStartYearsStr = request.getParameterValues("school_start_year[]");
	     String[] schoolEndYearsStr = request.getParameterValues("school_end_year[]");
	     int[] schoolStartYears = new int[schoolStartYearsStr.length];
	     int[] schoolEndYears = new int[schoolEndYearsStr.length]; // initialize length of array
	     int verify = 0;
	     
	    
	     Contact userContact = (Contact) session.getAttribute("userContact");
	     int resumeId = postDAO.initResume(userContact);
	     session.setAttribute("resumeId", resumeId); // panggawa resume
	     
	     // Parse the start and end year strings into integers
	     for (int i = 0; i < schoolStartYearsStr.length; i++) {
	    	 if (schoolStartYearsStr[i] != null && !schoolStartYearsStr[i].isEmpty()) {
	    	        schoolStartYears[i] = Integer.parseInt(schoolStartYearsStr[i]);
	    	    } else {
	    	        // Handle the case where the start year is missing
	    	        schoolStartYears[i] = 0; // Example default value, or handle it differently
	    	    }

	    	    // Validate the end year
	    	    if (schoolEndYearsStr[i] != null && !schoolEndYearsStr[i].isEmpty()) {
	    	        schoolEndYears[i] = Integer.parseInt(schoolEndYearsStr[i]);
	    	    } else {
	    	        // Handle the case where the end year is missing
	    	        schoolEndYears[i] = 0; // Example default value, or handle it differently
	    	    }
	     }
	        
	     String[] skills = request.getParameterValues("skill[]");

	     
	     //Insert all data
		
		if (jobTitles != null) {
            for (int i = 0; i < jobTitles.length; i++) {
                WorkHist workHist = new WorkHist(jobTitles[i], companyNames[i], startYears[i], endYears[i]);
                postDAO.insertWorkHist(workHist, resumeId);
            }
        }
		verify++;

        
        if (schoolNames != null) {
            for (int i = 0; i < schoolNames.length; i++) {
                Education education = new Education(schoolNames[i], degrees[i], schoolStartYears[i], schoolEndYears[i]);
                postDAO.insertEduc(education, resumeId);
            }
        }
        verify++;
        
        if (skills != null) {
            for (String skill : skills) {
                Skills skillName = new Skills(skill);
                postDAO.insertSkills(skillName, resumeId);
            }
        }
        verify++;

//		User resume = new User(address, workExp, educ, skills, desc);

		if (verify == 3) {
			request.setAttribute("successMessage5", "Account successfully created, please proceed to <a href='login.jsp' class='btn btn-1 fs-4 fw-bold'>Login</a>");
		} else {
			request.setAttribute("errorMessage6", "Resume creation unsuccessful, please try again");
			System.out.println("registration was not successful");
		}

		RequestDispatcher rd = request.getRequestDispatcher("createResume.jsp");
		rd.forward(request, response);
	}

	private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		;
		RequestDispatcher rd = request.getRequestDispatcher("homepage.jsp");
		rd.forward(request, response);
		System.out.println("what");
	}

	private void createPost(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession(true);
		OracleDataSource ods = new OracleDataSource();
		Connection con = null;
		try {
			ods.setURL("jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1");
			ods.setUser("sandbox");
			ods.setPassword("sandboxUser");
			con = ods.getConnection();

			String title = request.getParameter("title");
			String category = request.getParameter("category");
			String address = request.getParameter("address");
			String desc = request.getParameter("desc");
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String date = currentDate.format(formatter);
			int id = (int) session.getAttribute("companyId");

			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO job_posts (title, category, address, description, post_date, company_id) VALUES "
							+ "(?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)");

			preparedStatement.setString(1, title);
			preparedStatement.setString(2, category);
			preparedStatement.setString(3, address);
			preparedStatement.setString(4, desc);
			preparedStatement.setString(5, date);
			preparedStatement.setInt(6, id);

			int rowCount = preparedStatement.executeUpdate();
			if (rowCount > 0) {
				request.setAttribute("successMessage3", "Job successfully created");
				RequestDispatcher rd = request.getRequestDispatcher("makeJobPost.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("errorMessage4", "Job creation unsuccessful");
				RequestDispatcher rd = request.getRequestDispatcher("makeJobPost.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Part of registration -- for resume creation
	private void insertCompany(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		OracleDataSource ods = new OracleDataSource();
		Connection con = null;
		try {
			ods.setURL("jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1");
			ods.setUser("sandbox");
			ods.setPassword("sandboxUser");
			con = ods.getConnection();
			
			Part imagePart = request.getPart("picture");  // Retrieves the uploaded file
	        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
	        InputStream fileContent = imagePart.getInputStream();

			String title = request.getParameter("title");
			String email = request.getParameter("email");
			String cnumber = request.getParameter("cnumber");
			String address = request.getParameter("specific_address");
			String city = request.getParameter("city");
			String province = request.getParameter("province");
			String desc = request.getParameter("desc");
			String pass = request.getParameter("pass");
			
			PreparedStatement stmt = con.prepareStatement("INSERT INTO company_contact"
					+ " (email, contact_number, specific_address, city, province)"
					+ " VALUES (?, ?, ?, ?, ?)");
			stmt.setString(1, email);
			stmt.setString(2, cnumber);
			stmt.setString(3, address);
			stmt.setString(4, city);
			stmt.setString(5, province);
			
			int rowCount1 = stmt.executeUpdate();
			int contactId = 0;
			
			if (rowCount1 > 0) {
				PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM company_contact WHERE"
						+ " email = ? AND contact_number = ?");
				stmt2.setString(1, email);
				stmt2.setString(2, cnumber);
				ResultSet rs = stmt2.executeQuery();
				if (rs.next()) {
					contactId = rs.getInt("contact_id");
				}
			}
			
			

			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO Company (company_name, description, contact_id, password, verify) VALUES (?, ?, ?, ?, 0)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, desc);
			preparedStatement.setInt(3, contactId);
			preparedStatement.setString(4, pass);

			int rowCount = preparedStatement.executeUpdate();

//			int companyid = 0;
			if (rowCount > 0) {
				boolean icon = postDAO.uploadImageCompany(fileContent, contactId); // company and user are different because user is not just one form where we can easily retrieve contact ID
				RequestDispatcher rd = request.getRequestDispatcher("successCompany.jsp");
				rd.forward(request, response);
//				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//				if (generatedKeys.next()) {
//					companyid = generatedKeys.getInt(1);
//				}
			}

//			Not needed because we are removing user switching mode
//			if (companyid > 0) {
//				PreparedStatement updateStatement = con.prepareStatement("UPDATE User SET companyid = ? WHERE id = ?");
//				updateStatement.setInt(1, companyid);
//				updateStatement.setInt(2, UserLogin.getId2());
//				int rowUpdate = updateStatement.executeUpdate();
//
//				if (rowUpdate > 0) {
//					RequestDispatcher rd = request.getRequestDispatcher("successCompany.jsp");
//					rd.forward(request, response);
//				}
//			}

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage3", "An error occurred while processing your request.");
			RequestDispatcher rd = request.getRequestDispatcher("regCompanyCopy.jsp");
			rd.forward(request, response);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	// register a company as a user
//	private void insertCompanyUser(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		Connection con = null;
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb");
//
//			String title = request.getParameter("title");
//			String email = request.getParameter("email");
//			String cnumber = request.getParameter("cnumber");
//			String address = request.getParameter("address");
//			String picture = request.getParameter("picture");
//			String desc = request.getParameter("desc");
//			String pass = request.getParameter("pass");
//
//			PreparedStatement preparedStatement = con.prepareStatement(
//					"INSERT INTO Company (companyname, email, cnumber, address, description, companyimage, password, verify) VALUES (?, ?, ?, ?, ?, ?, ?, 0)",
//					PreparedStatement.RETURN_GENERATED_KEYS);
//			preparedStatement.setString(1, title);
//			preparedStatement.setString(2, email);
//			preparedStatement.setString(3, cnumber);
//			preparedStatement.setString(4, address);
//			preparedStatement.setString(5, desc);
//			preparedStatement.setString(6, picture);
//			preparedStatement.setString(7, pass);
//
//			int rowCount = preparedStatement.executeUpdate();
//
//			int companyid = 0;
//			if (rowCount > 0) {
//				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//				if (generatedKeys.next()) {
//					companyid = generatedKeys.getInt(1);
//				}
//			}
//
//			if (companyid > 0) {
//				PreparedStatement updateStatement = con.prepareStatement("UPDATE User SET companyid = ? WHERE id = ?");
//				updateStatement.setInt(1, companyid);
//				updateStatement.setInt(2, UserLogin.getId2());
//				int rowUpdate = updateStatement.executeUpdate();
//
//				if (rowUpdate > 0) {
//					RequestDispatcher rd = request.getRequestDispatcher("successCompanyUser.jsp");
//					rd.forward(request, response);
//				}
//			}
//
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//			request.setAttribute("errorMessage3", "Failed to register company.");
//			RequestDispatcher rd = request.getRequestDispatcher("regCompanyUser.jsp");
//			rd.forward(request, response);
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Part imagePart = request.getPart("icon");  // Retrieves the uploaded file
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();  // Get the file name
        HttpSession session = request.getSession(true);  // Access existing session if present
        Contact userContact = (Contact) session.getAttribute("userContact");
        UserLogin user = (UserLogin) session.getAttribute("user");
        // Get the file as an InputStream
        InputStream fileContent = imagePart.getInputStream();
		
//		String fname = user.getFname();
//		String lname = user.getLname();
//		String bio = user.getBio();
//		String bday = user.getBday();
//		String password = user.getPassword();
//		String email = userContact.getEmail();
//		String cnumber = userContact.getContact_number();
//		String specificAddress = userContact.getSpecific_address();
//		String district = userContact.getDistrict();
//		String barangay = userContact.getBarangay();
//		
//		UserLogin userFinal = new UserLogin(fname, lname, bio, bday, password);
//		Contact userContactFinal = new Contact(email, cnumber, specificAddress, district, barangay);
//		UserLogin user = (UserLogin) request.getAttribute("user");
//	    Contact userContact = (Contact) request.getAttribute("userContact");
        boolean reg = postDAO.registerFinal(user, userContact); //intialize resume
		boolean icon = postDAO.uploadImage(fileContent, userContact);
		System.out.println(userContact);
			if (icon && reg) {
//				request.setAttribute("successMessage",
//						"Account successfully created<br><span class='text-dark'>Back to</span> <a href='login.jsp' class='btn btn-1 fs-4 fw-bold'>Login</a>");
				RequestDispatcher rd = request.getRequestDispatcher("createResume.jsp");
				rd.forward(request, response);
			} else {
//				request.setAttribute("errorMessage2",
//						"Account creation unsuccessful<br><span class='text-dark'>Back to</span> <a href='login.jsp' class='btn btn-1 fs-4 fw-bold'>Register</a>");
				RequestDispatcher rd = request.getRequestDispatcher("createResume.jsp");
				rd.forward(request, response);
			}
	}

	private void registerInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Contact userContact = (Contact) session.getAttribute("userContact");
		UserLogin user = (UserLogin) session.getAttribute("user");
		String bday = request.getParameter("bday");
		String cnumber = request.getParameter("cnumber");
		String district = request.getParameter("district");
		String barangay = request.getParameter("barangay");
		String specificAddress = request.getParameter("specific_address");
		String bio = request.getParameter("bio");
		
		userContact.setContact_number(cnumber);
	    userContact.setSpecific_address(specificAddress);
	    userContact.setDistrict(district);
	    userContact.setBarangay(barangay);

//		UserLogin user = new UserLogin(bio, bday); // Class method used to retain data in the UserLogin model
		user.setBio(bio);
		user.setBday(bday);
		
		System.out.println(userContact);
		
		RequestDispatcher rd = request.getRequestDispatcher("iconSelect.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		So it looks like we have to separate GET and POST requests with doGet and doPost
//		This is essential because in executing servlets, we are able to execute functions and interact with the database
//		Only through requests do we have connection between the database and website

		String action = request.getServletPath(); 

		switch (action) {
		case "/home":
			home(request, response);
			break;
		case "/user":
			userPage(request, response);
			break;
		case "/company":
			company(request, response);
			break;
		case "/addJob":
			addJob(request, response);
			break;
		case "/joblist":
			try {
				listJob(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/showJobDetails":
			try {
				showJobDetailsAndApplicants(request, response);
			} catch (SQLException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/joblistCompany":
			try {
				listJobCompany(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/companyMode":
			companyMode(request, response);
			break;
		case "/userMode":
			userMode(request, response);
			break;
		case "/deleteWork":
			try {
				deleteWork(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/apply":
			try {
				applyWork(request, response);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/withdrawApp":
			try {
				withdrawApp(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/acceptApplicant":
			acceptApp(request, response);
			break;
		case "/rejectApplicant":
			rejectApp(request, response);
			break;
		case "/search":
			searchRequest(request, response);
			break;
		case "/makeResumeForm":
			try {
				showResumeForm(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/newHome":
			newHome(request, response);
		case "/getImage":
			try {
				getImage(request, response);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/getImageCompany":
			try {
				getImageCompany(request, response);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/getImageResult":
			try {
				getImageResult(request, response);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/getImageCompanyResult":
			try {
				getImageCompanyResult(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/updateProfileForm":
			updateProfileForm(request, response);
			break;
		default:
			try {
				homepage(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}



//	public void updateResume(HttpServletRequest request, HttpServletResponse response)
//			throws SQLException, IOException, ServletException {
//		String address = request.getParameter("address");
//		String workExp = request.getParameter("work");
//		String educ = request.getParameter("educ");
//		String skills = request.getParameter("skills");
//		String desc = request.getParameter("desc");
//		int userId = UserLogin.getId2();
//
//		User resume = new User(address, workExp, educ, skills, desc);
//
//		if (postDAO.updateResume(resume)) {
//			request.setAttribute("successMessage5", "Resume successfully updated");
//		} else {
//			request.setAttribute("errorMessage6", "Resume update unsuccessful");
//		}
//
//		RequestDispatcher rd = request.getRequestDispatcher("resume.jsp");
//		rd.forward(request, response);
//	}
	
//	private void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		HttpSession session = request.getSession(true);
//        InputStream inputStream = (InputStream) session.getAttribute("userImage");
//
//        if (inputStream != null) {
//            // Set the content type based on the file type (image/jpeg for example)
//        	String imageType = "image/jpeg";  // or dynamically detect the type
//        	response.setContentType(imageType);
//
//
//            // Get the OutputStream to send the image data to the client
//            OutputStream outStream = response.getOutputStream();
//
//            // Read the InputStream and write it to the OutputStream
//            byte[] buffer = new byte[4096*10];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outStream.write(buffer, 0, bytesRead);
//            }
//
//            // Close the output stream
//            outStream.close();
//        } else {
//            // Handle the case where no image was found in the session
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Set appropriate HTTP status
//            response.getWriter().println("Image not found.");
//        }
//    }
	
//	private void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
//	    HttpSession session = request.getSession(true);
//	    InputStream inputStream = (InputStream) session.getAttribute("userImage");
//	    OracleDataSource ods = new OracleDataSource();
//	    ods.setURL("jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1");
//	    ods.setUser("sandbox");
//	    ods.setPassword("sandboxUser");
//
//	    if (inputStream == null) {
//	        // If image is not in session, fetch from the database again (re-establish connection)
//	        try (Connection con = ods.getConnection()) {
//	            String imageQuery = "SELECT icon FROM users WHERE user_id = ?";
//	            try (PreparedStatement ps = con.prepareStatement(imageQuery)) {
//	                ps.setInt(1, (int) session.getAttribute("userId")); // Set the appropriate company ID
//	                try (ResultSet rs = ps.executeQuery()) {
//	                    if (rs.next()) {
//	                        inputStream = rs.getBinaryStream("icon");
//	                        session.setAttribute("userImage", inputStream); // Save in session for later use
//	                    } else {
//	                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//	                        response.getWriter().println("Image not found.");
//	                        return;
//	                    }
//	                }
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	                return;
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	            return;
//	        }
//	    }
//
//	    // Send image to client
//	    if (inputStream != null) {
//	        String imageType = "image/jpeg";  // Example, adjust based on your image type
//	        response.setContentType(imageType);
//	        OutputStream outStream = response.getOutputStream();
//	        byte[] buffer = new byte[4096*4096];
//	        int bytesRead;
//	        while ((bytesRead = inputStream.read(buffer)) != -1) {
//	            outStream.write(buffer, 0, bytesRead);
//	        }
//	        outStream.close();
//	    } else {
//	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//	        response.getWriter().println("Image not found.");
//	    }
//	}
	
//	private void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
//		// Dynamically determine the image type based on file extension
//		HttpSession session = request.getSession();
//	    InputStream inputStream = (InputStream) session.getAttribute("userImage");
//
//	    if (inputStream != null) {
//	        response.setContentType("image/jpeg");  // Set the appropriate content type
//
//	        // Use try-with-resources to handle the OutputStream
//	        try (OutputStream outStream = response.getOutputStream()) {
//	            byte[] buffer = new byte[8192];  // 8 KB buffer
//	            int bytesRead;
//
//	            // Read from the InputStream and write to the OutputStream
//	            while ((bytesRead = inputStream.read(buffer)) != -1) {
//	                outStream.write(buffer, 0, bytesRead);
//	            }
//	            outStream.flush();  // Ensure all data is sent
//	        } catch (IOException e) {
//	            // Handle exceptions during streaming
//	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                inputStream.close();  // Ensure the InputStream is closed
//	            } catch (IOException e) {
//	                e.printStackTrace();  // Log errors during closing
//	            }
//	        }
//	    } else {
//	        // Handle the case where no image is found in the session
//	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//	        response.getWriter().println("Image not found.");
//	    }
//
//}
	
	private static final String jdbcURL = "jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1";
	private static final String jdbcUsername = "sandbox";
	private static final String jdbcPassword = "sandboxUser";
	
	
	
	private void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		 String IMAGE_DIRECTORY = "C:\\Users\\johnp\\eclipse-workspace-new\\jobapplication-oracle-project\\src\\main\\webapp\\images"; // Replace with your actual path
	HttpSession session = request.getSession();
	int userId = (int) session.getAttribute("userId"); // Get user ID from request
    String imageFileName = null;

    // Get the image file name from the database
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT icon FROM users WHERE user_id = ?")) {
        stmt.setInt(1, userId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                imageFileName = rs.getString("icon");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return;
    }

    if (imageFileName != null) {
        File imageFile = new File(IMAGE_DIRECTORY + File.separator + imageFileName);

        if (imageFile.exists()) {
            // Set the content type based on the file extension (e.g., image/jpeg)
            response.setContentType(getServletContext().getMimeType(imageFile.getName()));

            try (InputStream fileInputStream = new FileInputStream(imageFile);
                 OutputStream responseOutputStream = response.getOutputStream()) {
                byte[] buffer = new byte[8192]; // 8KB buffer
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    responseOutputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Image not found.");
        }
    } else {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write("No image associated with this user.");
    }

}
	
	private void getImageCompany(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		 String IMAGE_DIRECTORY = "C:\\Users\\johnp\\eclipse-workspace-new\\jobapplication-oracle-project\\src\\main\\webapp\\images"; // Replace with your actual path
	HttpSession session = request.getSession();
	int companyId = (int) session.getAttribute("companyId"); // Get user ID from request
   String imageFileName = null;

   // Get the image file name from the database
   try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT company_icon FROM company WHERE company_id = ?")) {
       stmt.setInt(1, companyId);
       try (ResultSet rs = stmt.executeQuery()) {
           if (rs.next()) {
               imageFileName = rs.getString("company_icon");
           }
       }
   } catch (SQLException e) {
       e.printStackTrace();
       response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
       return;
   }

   if (imageFileName != null) {
       File imageFile = new File(IMAGE_DIRECTORY + File.separator + imageFileName);

       if (imageFile.exists()) {
           // Set the content type based on the file extension (e.g., image/jpeg)
           response.setContentType(getServletContext().getMimeType(imageFile.getName()));

           try (InputStream fileInputStream = new FileInputStream(imageFile);
                OutputStream responseOutputStream = response.getOutputStream()) {
               byte[] buffer = new byte[8192]; // 8KB buffer
               int bytesRead;
               while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                   responseOutputStream.write(buffer, 0, bytesRead);
               }
           }
       } else {
           response.setStatus(HttpServletResponse.SC_NOT_FOUND);
           response.getWriter().write("Image not found.");
       }
   } else {
       response.setStatus(HttpServletResponse.SC_NOT_FOUND);
       response.getWriter().write("No image associated with this user.");
   }

}
	
	private void getImageCompanyResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		 String IMAGE_DIRECTORY = "C:\\Users\\johnp\\eclipse-workspace-new\\jobapplication-oracle-project\\src\\main\\webapp\\images"; // Replace with your actual path
			int userId = Integer.parseInt(request.getParameter("id")); // Get user ID from request
		   String imageFileName = null;

		   // Get the image file name from the database
		   try (Connection conn = getConnection();
		        PreparedStatement stmt = conn.prepareStatement("SELECT company_icon FROM company WHERE company_id = ?")) {
		       stmt.setInt(1, userId);
		       try (ResultSet rs = stmt.executeQuery()) {
		           if (rs.next()) {
		               imageFileName = rs.getString("company_icon");
		           }
		       }
		   } catch (SQLException e) {
		       e.printStackTrace();
		       response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		       return;
		   }

		   if (imageFileName != null) {
		       File imageFile = new File(IMAGE_DIRECTORY + File.separator + imageFileName);

		       if (imageFile.exists()) {
		           // Set the content type based on the file extension (e.g., image/jpeg)
		           response.setContentType(getServletContext().getMimeType(imageFile.getName()));

		           try (InputStream fileInputStream = new FileInputStream(imageFile);
		                OutputStream responseOutputStream = response.getOutputStream()) {
		               byte[] buffer = new byte[8192]; // 8KB buffer
		               int bytesRead;
		               while ((bytesRead = fileInputStream.read(buffer)) != -1) {
		                   responseOutputStream.write(buffer, 0, bytesRead);
		               }
		           }
		       } else {
		           response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		           response.getWriter().write("Image not found.");
		       }
		   } else {
		       response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		       response.getWriter().write("No image associated with this user.");
		   }
	}
	
	
	private void getImageResult(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		 String IMAGE_DIRECTORY = "C:\\Users\\johnp\\eclipse-workspace-new\\jobapplication-oracle-project\\src\\main\\webapp\\images"; // Replace with your actual path
	int userId = Integer.parseInt(request.getParameter("id")); // Get user ID from request
   String imageFileName = null;

   // Get the image file name from the database
   try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT icon FROM users WHERE user_id = ?")) {
       stmt.setInt(1, userId);
       try (ResultSet rs = stmt.executeQuery()) {
           if (rs.next()) {
               imageFileName = rs.getString("icon");
           }
       }
   } catch (SQLException e) {
       e.printStackTrace();
       response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
       return;
   }

   if (imageFileName != null) {
       File imageFile = new File(IMAGE_DIRECTORY + File.separator + imageFileName);

       if (imageFile.exists()) {
           // Set the content type based on the file extension (e.g., image/jpeg)
           response.setContentType(getServletContext().getMimeType(imageFile.getName()));

           try (InputStream fileInputStream = new FileInputStream(imageFile);
                OutputStream responseOutputStream = response.getOutputStream()) {
               byte[] buffer = new byte[8192]; // 8KB buffer
               int bytesRead;
               while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                   responseOutputStream.write(buffer, 0, bytesRead);
               }
           }
       } else {
           response.setStatus(HttpServletResponse.SC_NOT_FOUND);
           response.getWriter().write("Image not found.");
       }
   } else {
       response.setStatus(HttpServletResponse.SC_NOT_FOUND);
       response.getWriter().write("No image associated with this user.");
   }

}

private Connection getConnection() throws SQLException {
    // Implement your connection logic here
    OracleDataSource ods = new OracleDataSource();
    ods.setURL("jdbc:oracle:thin:@//localhost:1521/FREEPDB1");
    ods.setUser("sandbox");
    ods.setPassword("sandboxUser");
    return ods.getConnection();
}
	
//	private void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
//		// Dynamically determine the image type based on file extension
//		HttpSession session = request.getSession();
//	    InputStream inputStream = (InputStream) session.getAttribute("userImage");
//
//	    if (inputStream != null) {
//	        response.setContentType("image/jpeg");
//	        try (OutputStream outStream = response.getOutputStream()) {
//	            byte[] buffer = new byte[8192];
//	            int bytesRead;
//	            while ((bytesRead = inputStream.read(buffer)) != -1) {
//	                outStream.write(buffer, 0, bytesRead);
//	            }
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        } finally {
//	            inputStream.close();
//	        }
//	    } else {
//	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//	    }
//
//}
	
//	private void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
//	    HttpSession session = request.getSession(true);
//	    // Assuming you have the image stored in the session
//	    Blob imageBlob = (Blob) session.getAttribute("userImage");
//
//	    if (imageBlob != null) {
//	        // Set the content type to the appropriate image type (e.g., image/jpeg)
//	        byte[] byteArray = imageBlob.getBytes(1, (int) imageBlob.length());
//	        response.setContentType("images/jpeg");
//	        OutputStream os = response.getOutputStream();
//	        os.write(byteArray);
//	        os.flush();
//	        os.close();
//	    } else {
//	        // Handle the case where no image was found in the session
//	        response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Set appropriate HTTP status
//	        response.getWriter().println("Image not found.");
//	    }
//	}



	private void showResumeForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession(true);
		int id = (int) session.getAttribute("userId");
		User user = postDAO.selectAllResume(id);

		if (user != null) {
			request.setAttribute("user", user);
		}

		request.getRequestDispatcher("resume.jsp").forward(request, response);
	}

	private void searchRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String query = request.getParameter("query");

		try {
			List<User> users = searchDAO.selectAllUsersSearch(query);
			List<Company> companies = searchDAO.selectAllCompaniesSearch(query);
			List<JobPosts> jobs = searchDAO.selectAllJobsSearch(query);

			request.setAttribute("listUser", users);
			request.setAttribute("listCompany", companies);
			request.setAttribute("listJob", jobs);

			request.getRequestDispatcher("searchList.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void newHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String query = "";

		try {
			List<User> users = searchDAO.selectAllUsersSearch(query);
			List<Company> companies = searchDAO.selectAllCompaniesSearch(query);
			List<JobPosts> jobs = searchDAO.selectAllJobsSearch(query);

			request.setAttribute("listUser", users);
			request.setAttribute("listCompany", companies);
			request.setAttribute("listJob", jobs);

			request.getRequestDispatcher("newHome.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void rejectApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		int workId = Integer.parseInt(request.getParameter("workId"));

		System.out.println("Rejecting applicant - User ID: " + userId + ", Work ID: " + workId);

		try {
			postDAO.rejectApplicant(userId, workId);
			response.setContentType("text/plain");
			response.getWriter().write("success");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Failed to reject applicant.");
		}
	}

	private void acceptApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		int companyId = (int) session.getAttribute("companyId");
		int userId = Integer.parseInt(request.getParameter("userId"));
		int workId = Integer.parseInt(request.getParameter("workId"));
		System.out.println("what " + workId);

		try {

			postDAO.acceptApplicant(userId, companyId);
			postDAO.rejectApplicant(userId, workId);
			response.setContentType("text/plain");
			response.getWriter().write("success");

		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Failed to accept applicant.");
		}
	}

	private void applyWork(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		HttpSession session = request.getSession(true);
		int workId = Integer.parseInt(request.getParameter("id"));
		int userId = (int) session.getAttribute("userId");
		if (postDAO.checkApply(workId, userId) == 1) {

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write("already_applied");
			out.flush();
		} else {

			try {
				postDAO.applyWork(workId, userId);

				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.write("success");
				out.flush();
			} catch (SQLException e) {

				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Failed to apply work.");
			}
		}
	}

	private void companyMode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.setAttribute("mode", 1);
		int companyId = (int) session.getAttribute("companyId");
		Company company = postDAO.getCompanyById(companyId);
		session.setAttribute("company", company);
		RequestDispatcher rd = request.getRequestDispatcher("joblistCompany");
		rd.forward(request, response);

	}

	private void userMode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.setAttribute("mode", 0);
//		session.setAttribute("icon", UserLogin.getIcon());
		RequestDispatcher rd = request.getRequestDispatcher("joblist");
		rd.forward(request, response);

	}

//	private void insertResume(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		HttpSession session = request.getSession();
//		int userId = (int) session.getAttribute("userId");
//		String address = request.getParameter("address");
//		String workExp = request.getParameter("work");
//		String educ = request.getParameter("educ");
//		String skills = request.getParameter("skills");
//		String desc = request.getParameter("desc");
//		User resume = new User(address, workExp, educ, skills, desc);
//
//		if (postDAO.insertResume(resume, userId) > 0) {
//			request.setAttribute("successMessage5", "Resume successfully created");
//			RequestDispatcher rd = request.getRequestDispatcher("resume.jsp");
//			rd.forward(request, response);
//		} else {
//			request.setAttribute("errorMessage6", "Resume creation unsuccessful");
//			RequestDispatcher rd = request.getRequestDispatcher("resume.jsp");
//			rd.forward(request, response);
//		}
//
//	}

	private void addJob(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("makeJobPost.jsp");
		rd.forward(request, response);
	}

	private void company(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int companyId = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("companyNow", companyId);
		Company company = postDAO.getCompanyById(companyId);
		List<JobPosts> listJobCompany = postDAO.selectAllJobsCompany(companyId);
		
		System.out.println(companyId);

		HttpSession session = request.getSession();

		if (company != null) {
			session.setAttribute("company", company);
			request.setAttribute("listJobCompany", listJobCompany);

			RequestDispatcher rd = request.getRequestDispatcher("companyPage.jsp");
			rd.forward(request, response);
		} else {
			session.setAttribute("errorMessage", "User not found");

			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}

	private void userPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("userNow", userId);
		User user = postDAO.getUserById(userId); // should use user class
		List<WorkHist> workHist = histDAO.getWork(userId);
		List<Education> educ = histDAO.getEduc(userId);
		List<Skills> skill = histDAO.getSkills(userId);

		HttpSession session = request.getSession();

		if (user != null) {
			session.setAttribute("user", user);
			request.setAttribute("workHist", workHist);
			request.setAttribute("educ", educ);
			request.setAttribute("skill", skill);
			System.out.println(workHist);
			System.out.println(educ);
			System.out.println(skill);

			RequestDispatcher rd = request.getRequestDispatcher("userpage.jsp");
			rd.forward(request, response);
		} else {
			session.setAttribute("errorMessage", "User not found");

			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}

	private void listJob(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		int mode = (int) session.getAttribute("mode");
		if (mode == 1) {
			int companyId = (int) session.getAttribute("companyId");
			List<JobPosts> listJobCompany = postDAO.selectAllJobsCompany(companyId);
			request.setAttribute("listJobCompany", listJobCompany);
		}
		
		List<JobPosts> listJob = postDAO.selectAllJobs();
		
//		List<JobPosts> listJobCompany = postDAO.selectAllJobsCompany();
		request.setAttribute("listJob", listJob);
		
//		request.setAttribute("listJobCompany", listJobCompany);
		RequestDispatcher dispatcher = request.getRequestDispatcher("joblist.jsp");
		dispatcher.forward(request, response);

	}

	private void listJobCompany(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		int mode = (int) session.getAttribute("mode");
		if (mode == 1) {
			
		}
		int companyId = (int) session.getAttribute("companyId");
		List<JobPosts> listJob = postDAO.selectAllJobsCompany(companyId);
		request.setAttribute("listJob", listJob);
		RequestDispatcher dispatcher = request.getRequestDispatcher("joblistCompany.jsp");
		dispatcher.forward(request, response);

	}

	private void homepage(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);

	}

	private void deleteWork(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		postDAO.deleteWork(id);
		response.sendRedirect("joblistCompany");
	}

	private void showJobDetailsAndApplicants(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();
		
		int postId = Integer.parseInt(request.getParameter("id")); // retrieve the post_id
		int mode = (int) session.getAttribute("mode");
		int companyId = histDAO.getCompanyIdByPost(postId);
		request.setAttribute("currentComapny", companyId);
		
		if (mode == 0) {
			
		int userId = (int) session.getAttribute("userId");
		int exists = postDAO.checkApply(postId, userId); // we can pass this to jsp
		request.setAttribute("exists", exists);
		}
		JobPosts existingJob = postDAO.selectJob(postId); // shows the job details based on the request parameter

		request.setAttribute("work", existingJob); // sets the attribute of work to the job object in the request "stream" if you would
		

		List<User> listUser = userDAO.selectAllUsers(postId); // grabs all applicants
		request.setAttribute("listUser", listUser); // in the request stream, it sets var listUser with object listUser. Make sure to have getters setters, thats how we're retrieving data
		RequestDispatcher dispatcher = request.getRequestDispatcher("jobdetailApplicants.jsp");
		dispatcher.forward(request, response);
	}

}
