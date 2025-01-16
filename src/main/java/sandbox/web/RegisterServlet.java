package sandbox.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sandbox.model.Contact;
import sandbox.model.User;
import sandbox.model.UserLogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterServlet
 */

@WebServlet("/RegisterUser")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			// Request input values from the JSP that is sending the POST request
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String password_repeat = request.getParameter("password_repeat");
				
				// Check if email is valid and passwords match
				if (!UserLogin.isValidEmail(email) && password != password_repeat) {
				       request.setAttribute("errorMessage2", "Invalid credentials");
				       RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
				       rd.forward(request, response);
				       return;
				    }
				
				if (!UserLogin.isValidEmail(email)) {
				       request.setAttribute("errorMessage2", "Email is invalid");
				       RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
				       rd.forward(request, response);
				       return;
				    }
				if (!password.equals(password_repeat)) {
					request.setAttribute("errorMessage2", "Passwords do not match");
					RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
					rd.forward(request, response);	
					return;
				}
			HttpSession session = request.getSession(true);
			Contact userContact = new Contact(email);
			
			session.setMaxInactiveInterval(60 * 60);
			session.setAttribute("userContact", userContact);
			UserLogin user = new UserLogin(fname, lname, password);
			session.setAttribute("user", user);
			
			System.out.println(session.getAttribute("userContact"));
			
			RequestDispatcher rd = request.getRequestDispatcher("registerDetails.jsp");
			rd.forward(request, response);	
		
	}

}
