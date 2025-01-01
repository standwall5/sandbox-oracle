package sandbox.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sandbox.dao.PostDAO;
import sandbox.dao.SearchDAO;
import sandbox.dao.UserDAO;
import sandbox.model.Company;
import sandbox.model.JobPosts;
import sandbox.model.User;
import sandbox.model.UserLogin;

/**
 * Servlet implementation class UserServlet
 */

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private PostDAO postDAO;
	private SearchDAO searchDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		this.userDAO = new UserDAO(); // I guess through UserServlet, we are importing every DAO function?
		this.postDAO = new PostDAO();
		this.searchDAO = new SearchDAO();

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
			insertCompany(request, response);
			break;
		case "/RegisterCompanyUser":
			insertCompanyUser(request, response);
			break;
		case "/makePost":
			createPost(request, response);
			break;
		case "/makeResume":
			insertResume(request, response);
			break;
		case "/updateResume":
			try {
				updateResume(request, response);
			} catch (SQLException | IOException | ServletException e) {
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

	private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		;
		RequestDispatcher rd = request.getRequestDispatcher("homepage.jsp");
		rd.forward(request, response);
		System.out.println("what");
	}

	private void createPost(HttpServletRequest request, HttpServletResponse response) {
		Connection con = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb");

			String title = request.getParameter("title");
			String category = request.getParameter("category");
			String address = request.getParameter("address");
			String desc = request.getParameter("desc");
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String date = currentDate.format(formatter);
			int id = UserLogin.getCompanyID();

			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO posts (title, category, address, description, postdate, companyid) VALUES "
							+ "(?, ?, ?, ?, ?, ?)");

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

	private void insertCompany(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb");

			String title = request.getParameter("title");
			String email = request.getParameter("email");
			String cnumber = request.getParameter("cnumber");
			String address = request.getParameter("address");
			String picture = request.getParameter("picture");
			String desc = request.getParameter("desc");
			String pass = request.getParameter("pass");

			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO Company (companyname, email, cnumber, address, description, companyimage, password, verify) VALUES (?, ?, ?, ?, ?, ?, ?, 0)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, cnumber);
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, desc);
			preparedStatement.setString(6, picture);
			preparedStatement.setString(7, pass);

			int rowCount = preparedStatement.executeUpdate();

			int companyid = 0;
			if (rowCount > 0) {
				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					companyid = generatedKeys.getInt(1);
				}
			}

			if (companyid > 0) {
				PreparedStatement updateStatement = con.prepareStatement("UPDATE User SET companyid = ? WHERE id = ?");
				updateStatement.setInt(1, companyid);
				updateStatement.setInt(2, UserLogin.getId2());
				int rowUpdate = updateStatement.executeUpdate();

				if (rowUpdate > 0) {
					RequestDispatcher rd = request.getRequestDispatcher("successCompany.jsp");
					rd.forward(request, response);
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
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

	private void insertCompanyUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb");

			String title = request.getParameter("title");
			String email = request.getParameter("email");
			String cnumber = request.getParameter("cnumber");
			String address = request.getParameter("address");
			String picture = request.getParameter("picture");
			String desc = request.getParameter("desc");
			String pass = request.getParameter("pass");

			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO Company (companyname, email, cnumber, address, description, companyimage, password, verify) VALUES (?, ?, ?, ?, ?, ?, ?, 0)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, cnumber);
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, desc);
			preparedStatement.setString(6, picture);
			preparedStatement.setString(7, pass);

			int rowCount = preparedStatement.executeUpdate();

			int companyid = 0;
			if (rowCount > 0) {
				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					companyid = generatedKeys.getInt(1);
				}
			}

			if (companyid > 0) {
				PreparedStatement updateStatement = con.prepareStatement("UPDATE User SET companyid = ? WHERE id = ?");
				updateStatement.setInt(1, companyid);
				updateStatement.setInt(2, UserLogin.getId2());
				int rowUpdate = updateStatement.executeUpdate();

				if (rowUpdate > 0) {
					RequestDispatcher rd = request.getRequestDispatcher("successCompanyUser.jsp");
					rd.forward(request, response);
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage3", "Failed to register company.");
			RequestDispatcher rd = request.getRequestDispatcher("regCompanyUser.jsp");
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

	private void registerUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String icon = request.getParameter("icon");
		Connection con = null;

		try {

			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb");

			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO User (firstname, lastname, email, password, contactnumber, birthdate, age, "
							+ "district, barangay, icon, bio, companyID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 6)");
			preparedStatement.setString(1, UserLogin.getFname());
			preparedStatement.setString(2, UserLogin.getLname());
			preparedStatement.setString(3, UserLogin.getEmail());
			preparedStatement.setString(4, UserLogin.getPassword());
			preparedStatement.setString(5, UserLogin.getCnumber());
			preparedStatement.setString(6, UserLogin.getBday());
			preparedStatement.setInt(7, UserLogin.getAge());
			preparedStatement.setString(8, UserLogin.getDistrict());
			preparedStatement.setString(9, UserLogin.getBarangay());
			preparedStatement.setString(10, icon);
			preparedStatement.setString(11, UserLogin.getBio());
			int rowCount = preparedStatement.executeUpdate();
			if (rowCount > 0) {
				request.setAttribute("successMessage",
						"Account successfully created<br><span class='text-dark'>Back to</span> <a href='login.jsp' class='btn btn-1 fs-4 fw-bold'>Login</a>");
				RequestDispatcher rd = request.getRequestDispatcher("successUser.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("errorMessage2",
						"Account creation unsuccessful<br><span class='text-dark'>Back to</span> <a href='login.jsp' class='btn btn-1 fs-4 fw-bold'>Register</a>");
				RequestDispatcher rd = request.getRequestDispatcher("successUser.jsp");
				rd.forward(request, response);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void registerInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bday = request.getParameter("bday");
		int age = Integer.parseInt(request.getParameter("age"));
		String cnumber = request.getParameter("cnumber");
		String district = request.getParameter("district");
		String barangay = request.getParameter("barangay");
		String bio = request.getParameter("bio");

		UserLogin user2 = new UserLogin(age, bday, cnumber, district, barangay, bio); // Class method used to retain data in the UserLogin model
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

	public void updateResume(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String address = request.getParameter("address");
		String workExp = request.getParameter("work");
		String educ = request.getParameter("educ");
		String skills = request.getParameter("skills");
		String desc = request.getParameter("desc");
		int userId = UserLogin.getId2();

		User resume = new User(address, workExp, educ, skills, desc);

		if (postDAO.updateResume(resume)) {
			request.setAttribute("successMessage5", "Resume successfully updated");
		} else {
			request.setAttribute("errorMessage6", "Resume update unsuccessful");
		}

		RequestDispatcher rd = request.getRequestDispatcher("resume.jsp");
		rd.forward(request, response);
	}

	private void showResumeForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		int id = UserLogin.getId2();
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
		int userId = Integer.parseInt(request.getParameter("userId"));
		int workId = Integer.parseInt(request.getParameter("workId"));
		System.out.println("what " + workId);

		try {

			postDAO.acceptApplicant(userId);
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
		int workId = Integer.parseInt(request.getParameter("id"));
		int userId = UserLogin.getId2();
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
		int companyId = UserLogin.getCompanyID();
		JobPosts company = postDAO.getCompanyById(companyId);
		session.setAttribute("company", company);
		RequestDispatcher rd = request.getRequestDispatcher("joblist");
		rd.forward(request, response);

	}

	private void userMode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.setAttribute("mode", 0);
		session.setAttribute("icon", UserLogin.getIcon());
		RequestDispatcher rd = request.getRequestDispatcher("joblist");
		rd.forward(request, response);

	}

	private void insertResume(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String address = request.getParameter("address");
		String workExp = request.getParameter("work");
		String educ = request.getParameter("educ");
		String skills = request.getParameter("skills");
		String desc = request.getParameter("desc");
		User resume = new User(address, workExp, educ, skills, desc);

		if (postDAO.insertResume(resume) > 0) {
			request.setAttribute("successMessage5", "Resume successfully created");
			RequestDispatcher rd = request.getRequestDispatcher("resume.jsp");
			rd.forward(request, response);
		} else {
			request.setAttribute("errorMessage6", "Resume creation unsuccessful");
			RequestDispatcher rd = request.getRequestDispatcher("resume.jsp");
			rd.forward(request, response);
		}

	}

	private void addJob(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("makeJobPost.jsp");
		rd.forward(request, response);
	}

	private void company(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int companyId = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("companyNow", companyId);
		JobPosts company = postDAO.getCompanyById(companyId);
		System.out.println(companyId);

		HttpSession session = request.getSession();

		if (company != null) {
			session.setAttribute("company", company);

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
		JobPosts user = postDAO.getUserById(userId);

		HttpSession session = request.getSession();

		if (user != null) {
			session.setAttribute("user", user);

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
		List<JobPosts> listJob = postDAO.selectAllJobs();
		List<JobPosts> listJobCompany = postDAO.selectAllJobsCompany();
		request.setAttribute("listJob", listJob);
		request.setAttribute("listJobCompany", listJobCompany);
		RequestDispatcher dispatcher = request.getRequestDispatcher("joblist.jsp");
		dispatcher.forward(request, response);

	}

	private void listJobCompany(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<JobPosts> listJob = postDAO.selectAllJobsCompany();
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
		int id = Integer.parseInt(request.getParameter("id"));

		JobPosts existingJob = postDAO.selectJob(id);

		request.setAttribute("work", existingJob);

		List<User> listUser = userDAO.selectAllUsers(id);

		request.setAttribute("listUser", listUser);

		RequestDispatcher dispatcher = request.getRequestDispatcher("jobdetailApplicants.jsp");
		dispatcher.forward(request, response);
	}

}
