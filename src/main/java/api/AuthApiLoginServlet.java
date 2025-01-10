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

@WebServlet("/api/auth/login")
public class AuthApiLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String SESSION_USER_ID = "sessionUserId";

	private final AuthService authService = new AuthService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		if (userName == null || password == null || userName.trim().isEmpty() || password.trim().isEmpty()) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "userName and password are required.");
			return;
		}

		User user = authService.login(userName.trim(), password);
		if (user == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials.");
			return;
		}

		HttpSession session = request.getSession(true);
		session.setAttribute(SESSION_USER_ID, user.getuID());
		String token = JwtUtil.generateToken(user.getuID());

		String body = "{\"success\":true,\"message\":\"Login successful.\",\"token\":\"" + JsonUtil.escape(token)
				+ "\",\"tokenType\":\"Bearer\",\"expiresIn\":3600,\"user\":" + JsonUtil.userToJson(user) + "}";
		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK, body);
	}
}
