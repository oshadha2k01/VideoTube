package RegisterUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/video_browsing";
	private static final String DEFAULT_USER = "root";
	private static final String DEFAULT_PASSWORD = "";
	
	private static String getEnvOrDefault(String key, String defaultValue) {
		String value = System.getenv(key);
		if (value == null || value.trim().isEmpty()) {
			return defaultValue;
		}
		return value;
	}

	public static Connection getConnection() throws SQLException {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
		}
		catch (Exception e) {
			throw new SQLException("Database driver load failed.", e);
		}
		
		String url = getEnvOrDefault("VIDEOHUB_DB_URL", DEFAULT_URL);
		String userName = getEnvOrDefault("VIDEOHUB_DB_USER", DEFAULT_USER);
		String password = getEnvOrDefault("VIDEOHUB_DB_PASSWORD", DEFAULT_PASSWORD);

		return DriverManager.getConnection(url, userName, password);
	}

}
