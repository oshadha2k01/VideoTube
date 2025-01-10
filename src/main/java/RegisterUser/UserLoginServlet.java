package RegisterUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;


@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String userName = request.getParameter("uid");
		String password = request.getParameter("pass");
		boolean isTrue;
		
		
		isTrue = UserDBUtil.validate(userName, password);
		
		if (isTrue == true) {
			List<User> usDetails = UserDBUtil.getUser(userName);
			request.setAttribute("usDetails", usDetails);
			
			// Generate JWT Token
			String token = JWTUtil.generateToken(userName, "USER");
			
			// Create Cookie
			Cookie authCookie = new Cookie("authToken", token);
			authCookie.setHttpOnly(true); // Protect against XSS
			authCookie.setMaxAge(3600 * 2); // 2 hours
			authCookie.setPath("/"); // Available for all pages
			response.addCookie(authCookie);
			
			RequestDispatcher dis = request.getRequestDispatcher("useraccount.jsp");
			dis.forward(request, response);
		} else {
			
			out.println("<script type = 'text/javascript'>");
			out.println("alert('Your username and password is incorrect');");
			out.println("location = 'login.jsp'");
			out.println("</script>");	
		}
		
	}
}
