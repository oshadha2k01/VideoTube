package api;

import java.util.List;

import RegisterUser.User;
import video.Channel;
import video.Video;

public class JsonUtil {

	private JsonUtil() {
	}

	public static String escape(String value) {
		if (value == null) {
			return "";
		}
		return value.replace("\\", "\\\\").replace("\"", "\\\"");
	}

	public static String userToJson(User user) {
		if (user == null) {
			return "null";
		}
		return "{"
				+ "\"id\":" + user.getuID() + ","
				+ "\"firstName\":\"" + escape(user.getuFirstName()) + "\","
				+ "\"lastName\":\"" + escape(user.getuLastName()) + "\","
				+ "\"userName\":\"" + escape(user.getuName()) + "\","
				+ "\"email\":\"" + escape(user.getuEmail()) + "\","
				+ "\"phoneNo\":\"" + escape(user.getuPhoneNo()) + "\""
				+ "}";
	}

	public static String channelToJson(Channel channel) {
		if (channel == null) {
			return "null";
		}
		return "{"
				+ "\"channelId\":" + channel.getChannelId() + ","
				+ "\"ownerUserId\":" + channel.getOwnerUserId() + ","
				+ "\"channelName\":\"" + escape(channel.getChannelName()) + "\","
				+ "\"description\":\"" + escape(channel.getDescription()) + "\","
				+ "\"createdAt\":\"" + escape(channel.getCreatedAt()) + "\""
				+ "}";
	}

	public static String videoToJson(Video video) {
		if (video == null) {
			return "null";
		}
		return "{"
				+ "\"videoId\":" + video.getVideoId() + ","
				+ "\"channelId\":" + video.getChannelId() + ","
				+ "\"title\":\"" + escape(video.getTitle()) + "\","
				+ "\"description\":\"" + escape(video.getDescription()) + "\","
				+ "\"category\":\"" + escape(video.getCategory()) + "\","
				+ "\"tags\":\"" + escape(video.getTags()) + "\","
				+ "\"visibility\":\"" + escape(video.getVisibility()) + "\","
				+ "\"durationSeconds\":" + video.getDurationSeconds() + ","
				+ "\"viewCount\":" + video.getViewCount() + ","
				+ "\"createdAt\":\"" + escape(video.getCreatedAt()) + "\""
				+ "}";
	}

	public static String channelsToJson(List<Channel> channels) {
		StringBuilder data = new StringBuilder("[");
		for (int i = 0; i < channels.size(); i++) {
			if (i > 0) {
				data.append(",");
			}
			data.append(channelToJson(channels.get(i)));
		}
		data.append("]");
		return data.toString();
	}

	public static String videosToJson(List<Video> videos) {
		StringBuilder data = new StringBuilder("[");
		for (int i = 0; i < videos.size(); i++) {
			if (i > 0) {
				data.append(",");
			}
			data.append(videoToJson(videos.get(i)));
		}
		data.append("]");
		return data.toString();
	}
}
