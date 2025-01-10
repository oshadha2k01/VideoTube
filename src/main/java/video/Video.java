package video;

public class Video {
	private int videoId;
	private int channelId;
	private String title;
	private String description;
	private String category;
	private String tags;
	private String visibility;
	private int durationSeconds;
	private long viewCount;
	private String createdAt;

	public Video(int videoId, int channelId, String title, String description, String category, String tags,
			String visibility, int durationSeconds, long viewCount, String createdAt) {
		this.videoId = videoId;
		this.channelId = channelId;
		this.title = title;
		this.description = description;
		this.category = category;
		this.tags = tags;
		this.visibility = visibility;
		this.durationSeconds = durationSeconds;
		this.viewCount = viewCount;
		this.createdAt = createdAt;
	}

	public int getVideoId() {
		return videoId;
	}

	public int getChannelId() {
		return channelId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public String getTags() {
		return tags;
	}

	public String getVisibility() {
		return visibility;
	}

	public int getDurationSeconds() {
		return durationSeconds;
	}

	public long getViewCount() {
		return viewCount;
	}

	public String getCreatedAt() {
		return createdAt;
	}
}
