package sandbox.web;

import java.io.IOException;
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
@WebServlet("/UserLoginCompany") // Used if the login is through a company, not user
public class LoginServletCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		HttpSession session = request.getSession();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb");
			String n = request.getParameter("uname");
			String p = request.getParameter("password");

			PreparedStatement preparedStatement = con
					.prepareStatement("select * from Company where (email like ?) and password = ?");
			preparedStatement.setString(1, n);
			preparedStatement.setString(2, p);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				int id2 = rs.getInt("id");
				int verifyNum = rs.getInt("verify");
				PreparedStatement preparedStatement2 = con.prepareStatement("SELECT * FROM user where companyid = ?");
				preparedStatement2.setInt(1, id2);

				ResultSet rs2 = preparedStatement2.executeQuery();

				if (rs2.next()) {
					int id3 = rs2.getInt("id");
					UserLogin.setId2(id3);
					UserLogin.setCompanyID(id2);
					UserLogin.setIcon(rs2.getString("icon"));
					UserLogin.setVerifyNum(verifyNum);
					UserLogin user1 = new UserLogin(id2);
					session.setAttribute("companyID", id2);
					session.setAttribute("verifyNum", verifyNum);
					session.setAttribute("currentUser", id3);
					session.setAttribute("icon", rs2.getString("icon"));
					session.setAttribute("isUser", 1);
					session.setAttribute("mode", 0);
					session.setAttribute("companyIcon", rs.getString("companyimage"));
					response.sendRedirect("companyMode");

				} else {

					int verifyNum2 = rs.getInt("verify");
					UserLogin.setVerifyNum(verifyNum2);
					UserLogin.setCompanyID(id2);
					UserLogin.setId2(id2);
					UserLogin.setIcon(rs.getString("companyimage"));
					UserLogin user1 = new UserLogin(id2);
					System.out.println(user1);
					System.out.println(UserLogin.getCompanyID());

					session.setAttribute("companyID", id2);
					session.setAttribute("verifyNum", verifyNum);
					session.setAttribute("currentUser", id2);
					session.setAttribute("icon", rs.getString("companyimage"));
					session.setAttribute("companyIcon", rs.getString("companyimage"));

					session.setAttribute("isUser", 0);
					session.setAttribute("mode", 1);

					response.sendRedirect("companyMode");
				}
			} else {
				session.setAttribute("errorMessage", "Wrong username or password. Please try again.");
				response.sendRedirect("loginCompany.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
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
