package api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RegisterUser.User;
import service.UserService;

@WebServlet("/api/users/me")
public class UserMeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer userId = ApiAuthUtil.getAuthenticatedUserId(request);
		if (userId == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "You are not authenticated.");
			return;
		}

		User user = userService.getUserById(userId);
		if (user == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "User not found.");
			return;
		}

		String body = "{\"success\":true,\"user\":" + JsonUtil.userToJson(user) + "}";
		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK, body);
	}
}
