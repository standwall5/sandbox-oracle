package sandbox.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout") // Invalidate session and redirect to index
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Retrieve existing session if it exists

		if (session != null) {
			session.invalidate(); // Invalidate (close) the session if it exists
		}

		// Forward to login.jsp
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
