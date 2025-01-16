package sandbox.web;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.OutputStream;


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
@WebServlet("/UserLogin")
public class LoginServlet extends HttpServlet {
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
		HttpSession session = request.getSession(true);
		session.setAttribute("mode", 0);

		try {
			ods.setURL("jdbc:oracle:thin:@//" + "localhost" + ":" + "1521" + "/" + "FREEPDB1");
			ods.setUser("sandbox");
			ods.setPassword("sandboxUser");
			con = ods.getConnection();
			String n = request.getParameter("uname"); // From the JSP sending the GET request, we request the input with "uname"
			String p = request.getParameter("password");

			PreparedStatement preparedStatement = con
					.prepareStatement("select * from contact where email = ?"); // This should not be like?
			preparedStatement.setString(1, n);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				int contactId = rs.getInt("contact_id");
				PreparedStatement stmt = con.prepareStatement("select * from users where contact_id = ? and password = ?");
				
				stmt.setInt(1, contactId);
				stmt.setString(2, p);
				
				ResultSet rs2 = stmt.executeQuery();
				if (rs2.next()) {
					
					int id2 = rs2.getInt(1); // Grab user ID
					
					// Store the InputStream in session
					session.setAttribute("userId", id2);
					session.setAttribute("isUser", 1);
					session.setAttribute("mode", 0);

					con.close();
					response.sendRedirect("newHome"); // redirect to joblist jsp
					return;
				}
				else {
					session.setAttribute("errorMessage", "Wrong password. Please try again.");
					response.sendRedirect("login.jsp");
					return;
				}
//				int id2 = rs.getInt("id"); // Grab user ID
//				InputStream inputStream = rs.getBinaryStream("icon");
//				// Store the InputStream in session
//				session.setAttribute("userImage", inputStream);
//				session.setAttribute("userId", id2);

				
				
//				 if (inputStream != null) {
//			            // Set the content type based on the file type (image/jpeg for example)
//			            response.setContentType("image/jpeg");
//
//			            // Get the OutputStream to send the image data to the client
//			            OutputStream outStream = response.getOutputStream();
//
//			            // Read the InputStream and write it to the OutputStream
//			            byte[] buffer = new byte[1024];
//			            int bytesRead;
//			            while ((bytesRead = inputStream.read(buffer)) != -1) {
//			                outStream.write(buffer, 0, bytesRead);
//			            }
//
//			            // Close the output stream
//			            outStream.close();
//			        } else {
//			            // Handle the case where no image was found
//			            response.getWriter().println("Image not found.");
//			        }
//				PreparedStatement preparedStatement2 = con.prepareStatement("select * from Company where id = ?");
//				preparedStatement2.setInt(1, id3);
//				ResultSet rs1 = preparedStatement2.executeQuery();

//				if (rs1.next()) {
//					int verifyNum = rs1.getInt("verify");
//					UserLogin.setVerifyNum(verifyNum);
//					UserLogin.setCompanyID(id3);
//					UserLogin.setId2(id2);
//					UserLogin.setIcon(rs.getString("icon"));
//					UserLogin user1 = new UserLogin(id2);
//					System.out.println(user1);
////					System.out.println(UserLogin.getCompanyID());
//
////					Sessions to ensure user consistency among all pages, until the session ends (display correct user profile pic and details)
////					session.setAttribute("companyID", id3);
////					session.setAttribute("verifyNum", verifyNum);
////					session.setAttribute("currentUser", id2);
////					session.setAttribute("icon", rs.getString("icon"));
////					session.setAttribute("companyIcon", rs1.getString("companyimage"));
//					session.setAttribute("isUser", 1);
//
//					response.sendRedirect("joblist"); // redirect to joblist jsp
//				}
			} else {
				session.setAttribute("errorMessage", "Wrong username or password. Please try again.");
				response.sendRedirect("login.jsp");
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
