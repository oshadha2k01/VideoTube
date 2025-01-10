package api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.VideoService;
import video.Video;

@WebServlet("/api/videos/*")
public class VideosApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VideoService videoService = new VideoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null || "/".equals(pathInfo) || pathInfo.trim().isEmpty()) {
			handleList(request, response);
			return;
		}

		String idPart = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
		try {
			int videoId = Integer.parseInt(idPart);
			Video video = videoService.getVideoById(videoId);
			if (video == null) {
				ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "Video not found.");
				return;
			}
			ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK,
					"{\"success\":true,\"video\":" + JsonUtil.videoToJson(video)
							+ ",\"streamUrl\":\"/api/videos/stream/" + video.getVideoId() + "\""
							+ ",\"thumbnailUrl\":\"/api/videos/thumbnail/" + video.getVideoId() + "\"}");
		} catch (NumberFormatException e) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid video id.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer userId = ApiAuthUtil.getAuthenticatedUserId(request);
		if (userId == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "You are not authenticated.");
			return;
		}

		String channelIdValue = request.getParameter("channelId");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String category = request.getParameter("category");
		String tags = request.getParameter("tags");
		String visibility = request.getParameter("visibility");
		Integer durationSeconds = parseIntegerOrNull(request.getParameter("durationSeconds"));

		if (channelIdValue == null || title == null || title.trim().isEmpty()) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "channelId and title are required.");
			return;
		}

		int channelId;
		try {
			channelId = Integer.parseInt(channelIdValue);
		} catch (NumberFormatException e) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid channelId.");
			return;
		}

		Video video = videoService.createVideo(userId, channelId, title, description, category, tags, visibility, durationSeconds);
		if (video == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_CONFLICT, "Video metadata creation failed.");
			return;
		}

		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_CREATED,
				"{\"success\":true,\"video\":" + JsonUtil.videoToJson(video)
						+ ",\"uploadUrl\":\"/api/videos/upload\""
						+ ",\"thumbnailUploadUrl\":\"/api/videos/thumbnail\""
						+ ",\"viewTrackUrl\":\"/api/videos/views/" + video.getVideoId() + "\"}");
	}

	private void handleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int offset = parseIntOrDefault(request.getParameter("offset"), 0);
		int limit = parseIntOrDefault(request.getParameter("limit"), 10);
		int safeOffset = Math.max(offset, 0);
		int safeLimit = Math.min(Math.max(limit, 1), 100);
		Integer channelId = parseIntegerOrNull(request.getParameter("channelId"));
		String query = request.getParameter("query");

		List<Video> videos = videoService.getVideosPaginated(safeOffset, safeLimit, channelId, query);
		String body = "{"
				+ "\"success\":true,"
				+ "\"offset\":" + safeOffset + ","
				+ "\"limit\":" + safeLimit + ","
				+ "\"count\":" + videos.size() + ","
				+ "\"videos\":" + JsonUtil.videosToJson(videos)
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

	private Integer parseIntegerOrNull(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
