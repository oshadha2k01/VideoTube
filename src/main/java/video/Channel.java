package video;

public class Channel {
	private int channelId;
	private int ownerUserId;
	private String channelName;
	private String description;
	private String createdAt;

	public Channel(int channelId, int ownerUserId, String channelName, String description, String createdAt) {
		this.channelId = channelId;
		this.ownerUserId = ownerUserId;
		this.channelName = channelName;
		this.description = description;
		this.createdAt = createdAt;
	}

	public int getChannelId() {
		return channelId;
	}

	public int getOwnerUserId() {
		return ownerUserId;
	}

	public String getChannelName() {
		return channelName;
	}

	public String getDescription() {
		return description;
	}

	public String getCreatedAt() {
		return createdAt;
	}
}
