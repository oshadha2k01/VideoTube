package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import service.VideoService;

@WebServlet("/api/videos/upload")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024,
		maxFileSize = 50 * 1024 * 1024,
		maxRequestSize = 55 * 1024 * 1024)
public class VideoUploadServlet extends HttpServlet {
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

		Part filePart = request.getPart("videoFile");
		if (filePart == null || filePart.getSize() <= 0) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "videoFile is required.");
			return;
		}

		byte[] videoBytes;
		try (InputStream inputStream = filePart.getInputStream()) {
			videoBytes = toByteArray(inputStream);
		}

		String submittedFileName = filePart.getSubmittedFileName();
		String contentType = filePart.getContentType();
		boolean uploaded = videoService.uploadVideoAsset(userId, videoId, submittedFileName, contentType, videoBytes);
		if (!uploaded) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_CONFLICT, "Upload failed. Check ownership, file size, and video id.");
			return;
		}

		String body = "{\"success\":true,\"message\":\"Video file uploaded to SQL database.\",\"videoId\":" + videoId
				+ ",\"fileName\":\"" + JsonUtil.escape(submittedFileName) + "\",\"sizeBytes\":" + videoBytes.length + "}";
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
