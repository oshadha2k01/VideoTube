package video;

public class ThumbnailAsset {
	private int videoId;
	private String fileName;
	private String mimeType;
	private long fileSizeBytes;
	private byte[] imageData;
	private String updatedAt;

	public ThumbnailAsset(int videoId, String fileName, String mimeType, long fileSizeBytes, byte[] imageData, String updatedAt) {
		this.videoId = videoId;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.fileSizeBytes = fileSizeBytes;
		this.imageData = imageData;
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

	public byte[] getImageData() {
		return imageData;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}
}
