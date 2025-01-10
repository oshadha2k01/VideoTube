package api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.VideoService;

@WebServlet("/api/videos/duration")
public class VideoDurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VideoService videoService = new VideoService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer userId = ApiAuthUtil.getAuthenticatedUserId(request);
		if (userId == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "You are not authenticated.");
			return;
		}

		String videoIdValue = request.getParameter("videoId");
		String durationValue = request.getParameter("durationSeconds");
		if (videoIdValue == null || durationValue == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "videoId and durationSeconds are required.");
			return;
		}

		int videoId;
		int durationSeconds;
		try {
			videoId = Integer.parseInt(videoIdValue);
			durationSeconds = Integer.parseInt(durationValue);
		} catch (NumberFormatException e) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric values.");
			return;
		}

		boolean updated = videoService.updateDuration(userId, videoId, durationSeconds);
		if (!updated) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_CONFLICT, "Duration update failed.");
			return;
		}

		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK,
				"{\"success\":true,\"videoId\":" + videoId + ",\"durationSeconds\":" + durationSeconds + "}");
	}
}
