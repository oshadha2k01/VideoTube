package api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.VideoService;
import video.ThumbnailAsset;

@WebServlet("/api/videos/thumbnail/*")
public class VideoThumbnailStreamServlet extends HttpServlet {
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

		ThumbnailAsset thumbnail = videoService.getThumbnailAssetByVideoId(videoId);
		if (thumbnail == null || thumbnail.getImageData() == null || thumbnail.getImageData().length == 0) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "Thumbnail not found.");
			return;
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(thumbnail.getMimeType() == null ? "image/jpeg" : thumbnail.getMimeType());
		response.setContentLengthLong(thumbnail.getFileSizeBytes());
		response.getOutputStream().write(thumbnail.getImageData());
	}
}
