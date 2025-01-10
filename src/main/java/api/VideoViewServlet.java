package api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.VideoService;

@WebServlet("/api/videos/views/*")
public class VideoViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VideoService videoService = new VideoService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null || "/".equals(pathInfo)) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "videoId is required.");
			return;
		}

		String idValue = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
		int videoId;
		try {
			videoId = Integer.parseInt(idValue);
		} catch (NumberFormatException e) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid videoId.");
			return;
		}

		Long newViewCount = videoService.addView(videoId);
		if (newViewCount == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "Video not found.");
			return;
		}

		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK,
				"{\"success\":true,\"videoId\":" + videoId + ",\"viewCount\":" + newViewCount + "}");
	}
}
