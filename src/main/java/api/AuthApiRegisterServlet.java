package api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import RegisterUser.User;
import service.AuthService;

@WebServlet("/api/auth/register")
public class AuthApiRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final AuthService authService = new AuthService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phoneNo = request.getParameter("phoneNo");

		if (isBlank(firstName) || isBlank(lastName) || isBlank(userName) || isBlank(password) || isBlank(email) || isBlank(phoneNo)) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
			return;
		}

		User user = authService.register(firstName.trim(), lastName.trim(), userName.trim(), password, email.trim(), phoneNo.trim());
		if (user == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_CONFLICT, "Registration failed. Username or email may already exist.");
			return;
		}

		HttpSession session = request.getSession(true);
		session.setAttribute(AuthApiLoginServlet.SESSION_USER_ID, user.getuID());
		String token = JwtUtil.generateToken(user.getuID());

		String body = "{\"success\":true,\"message\":\"Registration successful.\",\"token\":\"" + JsonUtil.escape(token)
				+ "\",\"tokenType\":\"Bearer\",\"expiresIn\":3600,\"user\":" + JsonUtil.userToJson(user) + "}";
		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_CREATED, body);
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
