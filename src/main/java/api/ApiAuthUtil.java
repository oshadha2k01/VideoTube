package api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ApiAuthUtil {

	private ApiAuthUtil() {
	}

	public static Integer getAuthenticatedUserId(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring("Bearer ".length()).trim();
			Integer userId = JwtUtil.validateAndExtractUserId(token);
			if (userId != null) {
				return userId;
			}
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			Object userIdObj = session.getAttribute(AuthApiLoginServlet.SESSION_USER_ID);
			if (userIdObj instanceof Integer) {
				return (Integer) userIdObj;
			}
		}

		return null;
	}
}
