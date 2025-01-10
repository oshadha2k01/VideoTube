package RegisterUser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

	private PasswordUtil() {
	}

	public static String hashPassword(String plainPassword) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 algorithm is not available.", e);
		}
	}

	public static boolean matches(String plainPassword, String storedPassword) {
		if (storedPassword == null) {
			return false;
		}
		String hashedInput = hashPassword(plainPassword);
		return hashedInput.equals(storedPassword) || plainPassword.equals(storedPassword);
	}
}
