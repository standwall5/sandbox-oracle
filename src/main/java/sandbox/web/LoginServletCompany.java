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
import oracle.jdbc.datasource.impl.OracleDataSource;
import sandbox.model.UserLogin;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/UserLoginCompany") // Used if the login is through a company, not user
public class LoginServletCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OracleDataSource ods = null;
		try {
			ods = new OracleDataSource();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection con = null;
		HttpSession session = request.getSession();

		try {
			ods.setURL("jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1");
			ods.setUser("sandbox");
			ods.setPassword("sandboxUser");
			con = ods.getConnection();
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
