package api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ChannelService;
import video.Channel;

@WebServlet("/api/channels/*")
public class ChannelsApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ChannelService channelService = new ChannelService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null || "/".equals(pathInfo) || pathInfo.trim().isEmpty()) {
			handleList(request, response);
			return;
		}

		String idPart = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
		try {
			int channelId = Integer.parseInt(idPart);
			Channel channel = channelService.getChannelById(channelId);
			if (channel == null) {
				ApiResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "Channel not found.");
				return;
			}
			ApiResponseUtil.sendJson(response, HttpServletResponse.SC_OK,
					"{\"success\":true,\"channel\":" + JsonUtil.channelToJson(channel) + "}");
		} catch (NumberFormatException e) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid channel id.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer userId = ApiAuthUtil.getAuthenticatedUserId(request);
		if (userId == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "You are not authenticated.");
			return;
		}

		String channelName = request.getParameter("channelName");
		String description = request.getParameter("description");
		if (channelName == null || channelName.trim().isEmpty()) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "channelName is required.");
			return;
		}

		Channel channel = channelService.createChannel(userId, channelName, description);
		if (channel == null) {
			ApiResponseUtil.sendError(response, HttpServletResponse.SC_CONFLICT, "Channel creation failed.");
			return;
		}

		ApiResponseUtil.sendJson(response, HttpServletResponse.SC_CREATED,
				"{\"success\":true,\"channel\":" + JsonUtil.channelToJson(channel) + "}");
	}

	private void handleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int offset = parseIntOrDefault(request.getParameter("offset"), 0);
		int limit = parseIntOrDefault(request.getParameter("limit"), 10);
		int safeOffset = Math.max(offset, 0);
		int safeLimit = Math.min(Math.max(limit, 1), 100);

		List<Channel> channels = channelService.getChannelsPaginated(safeOffset, safeLimit);
		String body = "{"
				+ "\"success\":true,"
				+ "\"offset\":" + safeOffset + ","
				+ "\"limit\":" + safeLimit + ","
				+ "\"count\":" + channels.size() + ","
				+ "\"channels\":" + JsonUtil.channelsToJson(channels)
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
