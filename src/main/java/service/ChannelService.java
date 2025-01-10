package service;

import java.util.ArrayList;
import java.util.List;

import video.Channel;
import video.VideoDBUtil;

public class ChannelService {

	public Channel createChannel(int ownerUserId, String channelName, String description) {
		if (ownerUserId <= 0 || isBlank(channelName)) {
			return null;
		}
		return VideoDBUtil.createChannel(ownerUserId, channelName.trim(), safe(description));
	}

	public Channel getChannelById(int channelId) {
		if (channelId <= 0) {
			return null;
		}
		return VideoDBUtil.getChannelById(channelId);
	}

	public List<Channel> getChannelsPaginated(int offset, int limit) {
		int safeOffset = Math.max(offset, 0);
		int safeLimit = Math.min(Math.max(limit, 1), 100);
		List<Channel> channels = VideoDBUtil.getChannelsPaginated(safeOffset, safeLimit);
		return channels == null ? new ArrayList<Channel>() : channels;
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	private String safe(String value) {
		return value == null ? "" : value.trim();
	}
}
