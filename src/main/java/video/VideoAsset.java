package video;

public class VideoAsset {
	private int videoId;
	private String fileName;
	private String mimeType;
	private long fileSizeBytes;
	private byte[] videoData;
	private String updatedAt;

	public VideoAsset(int videoId, String fileName, String mimeType, long fileSizeBytes, byte[] videoData, String updatedAt) {
		this.videoId = videoId;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.fileSizeBytes = fileSizeBytes;
		this.videoData = videoData;
		this.updatedAt = updatedAt;
	}

	public int getVideoId() {
		return videoId;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public long getFileSizeBytes() {
		return fileSizeBytes;
	}

	public byte[] getVideoData() {
		return videoData;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}
}
