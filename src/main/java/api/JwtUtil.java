package api;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtil {

	private static final String HMAC_SHA256 = "HmacSHA256";
	private static final long DEFAULT_EXPIRY_SECONDS = 3600;
	private static final String DEFAULT_SECRET = "videohub-dev-secret-change-me";

	private JwtUtil() {
	}

	public static String generateToken(int userId) {
		return generateToken(userId, DEFAULT_EXPIRY_SECONDS);
	}

	public static String generateToken(int userId, long expiresInSeconds) {
		long iat = Instant.now().getEpochSecond();
		long exp = iat + expiresInSeconds;
		String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
		String payload = "{\"sub\":\"" + userId + "\",\"iat\":" + iat + ",\"exp\":" + exp + "}";

		String encodedHeader = base64UrlEncode(header.getBytes(StandardCharsets.UTF_8));
		String encodedPayload = base64UrlEncode(payload.getBytes(StandardCharsets.UTF_8));
		String signature = sign(encodedHeader + "." + encodedPayload);
		return encodedHeader + "." + encodedPayload + "." + signature;
	}

	public static Integer validateAndExtractUserId(String token) {
		try {
			String[] parts = token.split("\\.");
			if (parts.length != 3) {
				return null;
			}
			String signingInput = parts[0] + "." + parts[1];
			String expectedSignature = sign(signingInput);
			if (!expectedSignature.equals(parts[2])) {
				return null;
			}
			String payloadJson = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
			long exp = extractLong(payloadJson, "\"exp\":");
			if (Instant.now().getEpochSecond() >= exp) {
				return null;
			}
			String subject = extractString(payloadJson, "\"sub\":\"");
			return Integer.parseInt(subject);
		} catch (Exception e) {
			return null;
		}
	}

	private static String sign(String input) {
		try {
			Mac mac = Mac.getInstance(HMAC_SHA256);
			SecretKeySpec keySpec = new SecretKeySpec(getSecret().getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
			mac.init(keySpec);
			byte[] signatureBytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
			return base64UrlEncode(signatureBytes);
		} catch (Exception e) {
			throw new IllegalStateException("JWT signing failed.", e);
		}
	}

	private static String getSecret() {
		String env = System.getenv("VIDEOHUB_JWT_SECRET");
		if (env == null || env.trim().isEmpty()) {
			return DEFAULT_SECRET;
		}
		return env;
	}

	private static String base64UrlEncode(byte[] bytes) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private static byte[] base64UrlDecode(String value) {
		return Base64.getUrlDecoder().decode(value);
	}

	private static long extractLong(String json, String fieldPrefix) {
		int start = json.indexOf(fieldPrefix);
		if (start < 0) {
			throw new IllegalArgumentException("Missing field: " + fieldPrefix);
		}
		start += fieldPrefix.length();
		int end = start;
		while (end < json.length() && Character.isDigit(json.charAt(end))) {
			end++;
		}
		return Long.parseLong(json.substring(start, end));
	}

	private static String extractString(String json, String fieldPrefix) {
		int start = json.indexOf(fieldPrefix);
		if (start < 0) {
			throw new IllegalArgumentException("Missing field: " + fieldPrefix);
		}
		start += fieldPrefix.length();
		int end = json.indexOf("\"", start);
		if (end < 0) {
			throw new IllegalArgumentException("Invalid JSON string field.");
		}
		return json.substring(start, end);
	}
}
