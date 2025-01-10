package service;

import java.util.ArrayList;
import java.util.List;

import video.Channel;
import video.ThumbnailAsset;
import video.Video;
import video.VideoAsset;
import video.VideoDBUtil;

public class VideoService {

	private final ChannelService channelService = new ChannelService();

	public Video createVideo(int requesterUserId, int channelId, String title, String description, String category, String tags, String visibility, Integer durationSeconds) {
		if (channelId <= 0 || isBlank(title)) {
			return null;
		}
		Channel channel = channelService.getChannelById(channelId);
		if (channel == null || channel.getOwnerUserId() != requesterUserId) {
			return null;
		}

		String normalizedVisibility = normalizeVisibility(visibility);
		int normalizedDuration = durationSeconds == null ? 0 : Math.max(durationSeconds, 0);
		return VideoDBUtil.createVideo(channelId, title.trim(), safe(description), safe(category), safe(tags), normalizedVisibility, normalizedDuration);
	}

	public Video getVideoById(int videoId) {
		if (videoId <= 0) {
			return null;
		}
		return VideoDBUtil.getVideoById(videoId);
	}

	public List<Video> getVideosPaginated(int offset, int limit, Integer channelId, String query) {
		int safeOffset = Math.max(offset, 0);
		int safeLimit = Math.min(Math.max(limit, 1), 100);
		List<Video> videos = VideoDBUtil.getVideosPaginated(safeOffset, safeLimit, channelId, query);
		return videos == null ? new ArrayList<Video>() : videos;
	}

	public boolean uploadVideoAsset(int requesterUserId, int videoId, String fileName, String mimeType, byte[] videoData) {
		if (requesterUserId <= 0 || videoId <= 0 || videoData == null || videoData.length == 0) {
			return false;
		}
		if (videoData.length > 50L * 1024L * 1024L) {
			return false;
		}

		Integer ownerUserId = VideoDBUtil.getVideoOwnerUserId(videoId);
		if (ownerUserId == null || ownerUserId != requesterUserId) {
			return false;
		}

		String normalizedName = safe(fileName);
		String normalizedMime = isBlank(mimeType) ? "application/octet-stream" : mimeType.trim();
		return VideoDBUtil.upsertVideoAsset(videoId, normalizedName, normalizedMime, videoData.length, videoData);
	}

	public VideoAsset getVideoAssetByVideoId(int videoId) {
		if (videoId <= 0) {
			return null;
		}
		return VideoDBUtil.getVideoAssetByVideoId(videoId);
	}

	public boolean uploadThumbnailAsset(int requesterUserId, int videoId, String fileName, String mimeType, byte[] imageData) {
		if (requesterUserId <= 0 || videoId <= 0 || imageData == null || imageData.length == 0) {
			return false;
		}
		if (imageData.length > 5L * 1024L * 1024L) {
			return false;
		}

		Integer ownerUserId = VideoDBUtil.getVideoOwnerUserId(videoId);
		if (ownerUserId == null || ownerUserId != requesterUserId) {
			return false;
		}

		String normalizedName = safe(fileName);
		String normalizedMime = isBlank(mimeType) ? "image/jpeg" : mimeType.trim();
		return VideoDBUtil.upsertThumbnailAsset(videoId, normalizedName, normalizedMime, imageData.length, imageData);
	}

	public ThumbnailAsset getThumbnailAssetByVideoId(int videoId) {
		if (videoId <= 0) {
			return null;
		}
		return VideoDBUtil.getThumbnailAssetByVideoId(videoId);
	}

	public boolean updateDuration(int requesterUserId, int videoId, int durationSeconds) {
		if (requesterUserId <= 0 || videoId <= 0 || durationSeconds < 0) {
			return false;
		}
		Integer ownerUserId = VideoDBUtil.getVideoOwnerUserId(videoId);
		if (ownerUserId == null || ownerUserId != requesterUserId) {
			return false;
		}
		return VideoDBUtil.updateVideoDuration(videoId, durationSeconds);
	}

	public Long addView(int videoId) {
		if (videoId <= 0) {
			return null;
		}
		return VideoDBUtil.incrementViewCount(videoId);
	}

	private String normalizeVisibility(String visibility) {
		if (visibility == null) {
			return "PUBLIC";
		}
		String upper = visibility.trim().toUpperCase();
		if ("PUBLIC".equals(upper) || "UNLISTED".equals(upper) || "PRIVATE".equals(upper)) {
			return upper;
		}
		return "PUBLIC";
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	private String safe(String value) {
		return value == null ? "" : value.trim();
	}
}
