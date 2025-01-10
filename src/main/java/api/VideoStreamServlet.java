package api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.VideoService;
import video.Video;
import video.VideoAsset;

@WebServlet("/api/videos/stream/*")
public class VideoStreamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VideoService videoService = new VideoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		Video video = videoService.getVideoById(videoId);
		if (video == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "Video not found.");
			return;
		}
		if ("PRIVATE".equalsIgnoreCase(video.getVisibility())) {
			Integer userId = ApiAuthUtil.getAuthenticatedUserId(request);
			if (userId == null) {
				ApiResponseUtil.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication required for private video.");
				return;
			}
		}

		VideoAsset asset = videoService.getVideoAssetByVideoId(videoId);
		if (asset == null || asset.getVideoData() == null || asset.getVideoData().length == 0) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "Video file not uploaded.");
			return;
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(asset.getMimeType() == null ? "application/octet-stream" : asset.getMimeType());
		response.setHeader("Content-Disposition", "inline; filename=\"" + JsonUtil.escape(asset.getFileName()) + "\"");
		response.setContentLengthLong(asset.getFileSizeBytes());
		response.getOutputStream().write(asset.getVideoData());
	}
}
