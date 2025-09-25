package sandbox.web;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sandbox.model.UserLogin;

/**
 * Servlet implementation class LoginServlet
 */
// Used if the login is through a company, not user
public class LoginServletCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// PostgreSQL connection details
	private static final String jdbcURL = "jdbc:postgresql://localhost:5432/sandbox-application";
	private static final String jdbcUsername = "postgres";
	private static final String jdbcPassword = "pgLarry1!";
	
	private Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (ClassNotFoundException e) {
			throw new SQLException("PostgreSQL Driver not found", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		HttpSession session = request.getSession();

		try {
			con = getConnection();
			String n = request.getParameter("uname");
			String p = request.getParameter("password");

			PreparedStatement preparedStatement = con
					.prepareStatement("select * from company_contact where email = ?");
			preparedStatement.setString(1, n);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				int contact_id = rs.getInt("contact_id");
				PreparedStatement stmt = con.prepareStatement("select * from company where contact_id = ?");
				stmt.setInt(1, contact_id);
				ResultSet rs2 = stmt.executeQuery();
				if (rs2.next()) {
					int id2 = rs2.getInt("company_id"); // Get company id
					int verifyNum = rs2.getInt("verify"); // Get verify status
					if (verifyNum == 1) {
						String companyIcon = rs2.getString("company_icon");
						session.setAttribute("companyImage", companyIcon);
						session.setAttribute("companyId", id2);
						session.setAttribute("verifyNum", verifyNum);
						session.setAttribute("currentUser", id2);
//						session.setAttribute("icon", rs.getString("companyimage"));
//						session.setAttribute("companyIcon", rs.getString("companyimage"));

						session.setAttribute("isUser", 0);
						session.setAttribute("mode", 1);

						response.sendRedirect("newHome");
					}
					else {
						session.setAttribute("errorMessage", "Company not validated yet, please wait 2-3 days.");
						response.sendRedirect("loginCompany.jsp");
					}
					
					// Store the InputStream in session
					
//					PreparedStatement preparedStatement2 = con.prepareStatement("SELECT * FROM user where companyid = ?");
//					preparedStatement2.setInt(1, id2);
	//
//					ResultSet rs2 = preparedStatement2.executeQuery();

//					if (rs2.next()) {
//						int id3 = rs2.getInt("id");
//						UserLogin.setId2(id3);
//						UserLogin.setCompanyID(id2);
//						UserLogin.setIcon(rs2.getString("icon"));
//						UserLogin.setVerifyNum(verifyNum);
//						UserLogin user1 = new UserLogin(id2);
//						session.setAttribute("companyID", id2);
//						session.setAttribute("verifyNum", verifyNum);
//						session.setAttribute("currentUser", id3);
//						session.setAttribute("icon", rs2.getString("icon"));
//						session.setAttribute("isUser", 1);
//						session.setAttribute("mode", 0);
//						session.setAttribute("companyIcon", rs.getString("companyimage"));
//						response.sendRedirect("companyMode");
	//
//					} else {
	//
//						int verifyNum2 = rs.getInt("verify");
//						UserLogin.setVerifyNum(verifyNum2);
//						UserLogin.setCompanyID(id2);
//						UserLogin.setId2(id2);
//						UserLogin.setIcon(rs.getString("companyimage"));
//						UserLogin user1 = new UserLogin(id2);
//						System.out.println(user1);
//						System.out.println(UserLogin.getCompanyID());

						
				}
				
			} else {
				session.setAttribute("errorMessage", "Wrong username or password. Please try again.");
				response.sendRedirect("loginCompany.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
}
