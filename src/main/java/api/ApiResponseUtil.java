package api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ApiResponseUtil {

	private ApiResponseUtil() {
	}

	public static void sendJson(HttpServletResponse response, int statusCode, String jsonBody) throws IOException {
		response.setStatus(statusCode);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonBody);
	}

	public static void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
		String body = "{\"success\":false,\"message\":\"" + JsonUtil.escape(message) + "\"}";
		sendJson(response, statusCode, body);
	}
}
