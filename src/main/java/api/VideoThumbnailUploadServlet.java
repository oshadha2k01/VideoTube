package api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import service.VideoService;

@WebServlet("/api/videos/thumbnail")
@MultipartConfig(
		fileSizeThreshold = 512 * 1024,
		maxFileSize = 5 * 1024 * 1024,
		maxRequestSize = 6 * 1024 * 1024)
public class VideoThumbnailUploadServlet extends HttpServlet {
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
		if (videoIdValue == null || videoIdValue.trim().isEmpty()) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "videoId is required.");
			return;
		}

		int videoId;
		try {
			videoId = Integer.parseInt(videoIdValue);
		} catch (NumberFormatException e) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid videoId.");
			return;
		}

		Part filePart = request.getPart("thumbnailFile");
		if (filePart == null || filePart.getSize() <= 0) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "thumbnailFile is required.");
			return;
		}

		byte[] imageBytes;
		try (InputStream inputStream = filePart.getInputStream()) {
			imageBytes = toByteArray(inputStream);
		}

		String contentType = filePart.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "thumbnailFile must be an image.");
			return;
		}

		boolean uploaded = videoService.uploadThumbnailAsset(userId, videoId, filePart.getSubmittedFileName(), contentType, imageBytes);
		if (!uploaded) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_CONFLICT, "Thumbnail upload failed.");
			return;
		}

		String body = "{\"success\":true,\"message\":\"Thumbnail uploaded.\",\"videoId\":" + videoId
				+ ",\"thumbnailUrl\":\"/api/videos/thumbnail/" + videoId + "\"}";
		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK, body);
	}

	private byte[] toByteArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[8192];
		int nRead;
		while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		return buffer.toByteArray();
	}
}
