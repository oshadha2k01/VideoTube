package api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RegisterUser.User;
import service.UserService;

@WebServlet("/api/users/*")
public class UsersApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer requesterUserId = ApiAuthUtil.getAuthenticatedUserId(request);
		if (requesterUserId == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "You are not authenticated.");
			return;
		}

		String pathInfo = request.getPathInfo();
		if (pathInfo == null || "/".equals(pathInfo) || pathInfo.trim().isEmpty()) {
			handleListUsers(request, response);
			return;
		}

		String requestedId = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
		if ("me".equalsIgnoreCase(requestedId)) {
			User me = userService.getUserById(requesterUserId);
			if (me == null) {
				ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "User not found.");
				return;
			}
			ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK, "{\"success\":true,\"user\":" + JsonUtil.userToJson(me) + "}");
			return;
		}

		try {
			int userId = Integer.parseInt(requestedId);
			User user = userService.getUserById(userId);
			if (user == null) {
				ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "User not found.");
				return;
			}
			ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK, "{\"success\":true,\"user\":" + JsonUtil.userToJson(user) + "}");
		} catch (NumberFormatException ex) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid user id.");
		}
	}

	private void handleListUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int offset = parseIntOrDefault(request.getParameter("offset"), 0);
		int limit = parseIntOrDefault(request.getParameter("limit"), 10);
		List<User> users = userService.getUsersPaginated(offset, limit);

		StringBuilder usersJson = new StringBuilder("[");
		for (int i = 0; i < users.size(); i++) {
			if (i > 0) {
				usersJson.append(",");
			}
			usersJson.append(JsonUtil.userToJson(users.get(i)));
		}
		usersJson.append("]");

		String body = "{"
				+ "\"success\":true,"
				+ "\"offset\":" + Math.max(offset, 0) + ","
				+ "\"limit\":" + (limit <= 0 ? 10 : Math.min(limit, 100)) + ","
				+ "\"count\":" + users.size() + ","
				+ "\"users\":" + usersJson
				+ "}";
		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK, body);
	}

	private int parseIntOrDefault(String value, int defaultValue) {
		try {
			return value == null ? defaultValue : Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
