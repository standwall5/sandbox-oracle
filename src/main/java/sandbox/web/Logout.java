package sandbox.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
