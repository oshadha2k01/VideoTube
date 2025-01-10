package video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import RegisterUser.DBConnect;

public class VideoDBUtil {

	private VideoDBUtil() {
	}

	public static Channel createChannel(int ownerUserId, String channelName, String description) {
		String sql = "insert into channels (ownerUserId, channelName, description) values (?, ?, ?)";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, ownerUserId);
			stmt.setString(2, channelName);
			stmt.setString(3, description);
			int updated = stmt.executeUpdate();
			if (updated <= 0) {
				return null;
			}
			try (ResultSet keys = stmt.getGeneratedKeys()) {
				if (keys.next()) {
					return getChannelById(keys.getInt(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Channel getChannelById(int channelId) {
		String sql = "select channelId, ownerUserId, channelName, description, createdAt from channels where channelId = ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, channelId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Channel(
							rs.getInt("channelId"),
							rs.getInt("ownerUserId"),
							rs.getString("channelName"),
							rs.getString("description"),
							rs.getString("createdAt"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Channel> getChannelsPaginated(int offset, int limit) {
		ArrayList<Channel> channels = new ArrayList<>();
		String sql = "select channelId, ownerUserId, channelName, description, createdAt from channels order by channelId asc limit ? offset ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					channels.add(new Channel(
							rs.getInt("channelId"),
							rs.getInt("ownerUserId"),
							rs.getString("channelName"),
							rs.getString("description"),
							rs.getString("createdAt")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channels;
	}

	public static Video createVideo(int channelId, String title, String description, String category, String tags, String visibility, int durationSeconds) {
		String sql = "insert into videos (channelId, title, description, category, tags, visibility, durationSeconds) values (?, ?, ?, ?, ?, ?, ?)";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, channelId);
			stmt.setString(2, title);
			stmt.setString(3, description);
			stmt.setString(4, category);
			stmt.setString(5, tags);
			stmt.setString(6, visibility);
			stmt.setInt(7, durationSeconds);
			int updated = stmt.executeUpdate();
			if (updated <= 0) {
				return null;
			}
			try (ResultSet keys = stmt.getGeneratedKeys()) {
				if (keys.next()) {
					return getVideoById(keys.getInt(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Video getVideoById(int videoId) {
		String sql = "select videoId, channelId, title, description, category, tags, visibility, durationSeconds, viewCount, createdAt from videos where videoId = ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, videoId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Video(
							rs.getInt("videoId"),
							rs.getInt("channelId"),
							rs.getString("title"),
							rs.getString("description"),
							rs.getString("category"),
							rs.getString("tags"),
							rs.getString("visibility"),
							rs.getInt("durationSeconds"),
							rs.getLong("viewCount"),
							rs.getString("createdAt"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Video> getVideosPaginated(int offset, int limit, Integer channelId, String query) {
		ArrayList<Video> videos = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				"select videoId, channelId, title, description, category, tags, visibility, durationSeconds, viewCount, createdAt from videos where 1=1");
		ArrayList<Object> params = new ArrayList<>();

		if (channelId != null) {
			sql.append(" and channelId = ?");
			params.add(channelId);
		}
		if (query != null && !query.trim().isEmpty()) {
			sql.append(" and (title like ? or description like ? or tags like ?)");
			String like = "%" + query.trim() + "%";
			params.add(like);
			params.add(like);
			params.add(like);
		}

		sql.append(" order by videoId desc limit ? offset ?");
		params.add(limit);
		params.add(offset);

		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql.toString())) {
			for (int i = 0; i < params.size(); i++) {
				Object param = params.get(i);
				if (param instanceof Integer) {
					stmt.setInt(i + 1, (Integer) param);
				} else {
					stmt.setString(i + 1, String.valueOf(param));
				}
			}
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					videos.add(new Video(
							rs.getInt("videoId"),
							rs.getInt("channelId"),
							rs.getString("title"),
							rs.getString("description"),
							rs.getString("category"),
							rs.getString("tags"),
							rs.getString("visibility"),
							rs.getInt("durationSeconds"),
							rs.getLong("viewCount"),
							rs.getString("createdAt")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return videos;
	}

	public static Integer getVideoOwnerUserId(int videoId) {
		String sql = "select c.ownerUserId from videos v inner join channels c on v.channelId = c.channelId where v.videoId = ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, videoId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("ownerUserId");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean upsertVideoAsset(int videoId, String fileName, String mimeType, long fileSizeBytes, byte[] videoData) {
		String sql = "insert into video_assets (videoId, fileName, mimeType, fileSizeBytes, videoData) values (?, ?, ?, ?, ?) "
				+ "on duplicate key update fileName = values(fileName), mimeType = values(mimeType), "
				+ "fileSizeBytes = values(fileSizeBytes), videoData = values(videoData), updatedAt = current_timestamp";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, videoId);
			stmt.setString(2, fileName);
			stmt.setString(3, mimeType);
			stmt.setLong(4, fileSizeBytes);
			stmt.setBytes(5, videoData);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static VideoAsset getVideoAssetByVideoId(int videoId) {
		String sql = "select videoId, fileName, mimeType, fileSizeBytes, videoData, updatedAt from video_assets where videoId = ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, videoId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new VideoAsset(
							rs.getInt("videoId"),
							rs.getString("fileName"),
							rs.getString("mimeType"),
							rs.getLong("fileSizeBytes"),
							rs.getBytes("videoData"),
							rs.getString("updatedAt"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean upsertThumbnailAsset(int videoId, String fileName, String mimeType, long fileSizeBytes, byte[] imageData) {
		String sql = "insert into video_assets (videoId, thumbnailFileName, thumbnailMimeType, thumbnailSizeBytes, thumbnailData) values (?, ?, ?, ?, ?) "
				+ "on duplicate key update thumbnailFileName = values(thumbnailFileName), thumbnailMimeType = values(thumbnailMimeType), "
				+ "thumbnailSizeBytes = values(thumbnailSizeBytes), thumbnailData = values(thumbnailData), updatedAt = current_timestamp";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, videoId);
			stmt.setString(2, fileName);
			stmt.setString(3, mimeType);
			stmt.setLong(4, fileSizeBytes);
			stmt.setBytes(5, imageData);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ThumbnailAsset getThumbnailAssetByVideoId(int videoId) {
		String sql = "select videoId, thumbnailFileName, thumbnailMimeType, thumbnailSizeBytes, thumbnailData, updatedAt "
				+ "from video_assets where videoId = ? and thumbnailData is not null";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, videoId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new ThumbnailAsset(
							rs.getInt("videoId"),
							rs.getString("thumbnailFileName"),
							rs.getString("thumbnailMimeType"),
							rs.getLong("thumbnailSizeBytes"),
							rs.getBytes("thumbnailData"),
							rs.getString("updatedAt"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateVideoDuration(int videoId, int durationSeconds) {
		String sql = "update videos set durationSeconds = ? where videoId = ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, durationSeconds);
			stmt.setInt(2, videoId);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Long incrementViewCount(int videoId) {
		String updateSql = "update videos set viewCount = viewCount + 1 where videoId = ?";
		String readSql = "select viewCount from videos where videoId = ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement updateStmt = con.prepareStatement(updateSql);
				PreparedStatement readStmt = con.prepareStatement(readSql)) {
			updateStmt.setInt(1, videoId);
			int updated = updateStmt.executeUpdate();
			if (updated <= 0) {
				return null;
			}
			readStmt.setInt(1, videoId);
			try (ResultSet rs = readStmt.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("viewCount");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
