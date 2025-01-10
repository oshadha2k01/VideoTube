package RegisterUser;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 * A simple JWT implementation using built-in Java libraries.
 * Note: Requires a JSON library like org.json. If not available, 
 * you can use simple string concatenation for the payload.
 */
public class JWTUtil {

    private static final String SECRET_KEY = "your-very-secure-and-long-secret-key-for-videohub";
    private static final String ALGORITHM = "HmacSHA256";

    public static String generateToken(String username, String role) {
        try {
            // Header
            String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String encodedHeader = encode(header.getBytes(StandardCharsets.UTF_8));

            // Payload
            long now = System.currentTimeMillis() / 1000;
            long exp = now + (3600 * 2); // 2 hours expiration
            String payload = String.format("{\"sub\":\"%s\",\"role\":\"%s\",\"iat\":%d,\"exp\":%d}", 
                                            username, role, now, exp);
            String encodedPayload = encode(payload.getBytes(StandardCharsets.UTF_8));

            // Signature
            String signatureInput = encodedHeader + "." + encodedPayload;
            String signature = sign(signatureInput, SECRET_KEY);

            return encodedHeader + "." + encodedPayload + "." + signature;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            String encodedHeader = parts[0];
            String encodedPayload = parts[1];
            String signature = parts[2];

            // Verify signature
            String signatureInput = encodedHeader + "." + encodedPayload;
            String expectedSignature = sign(signatureInput, SECRET_KEY);
            if (!expectedSignature.equals(signature)) return false;

            // Check expiration
            byte[] decodedPayload = Base64.getUrlDecoder().decode(encodedPayload);
            String payloadStr = new String(decodedPayload, StandardCharsets.UTF_8);
            
            // Simple check for "exp" in the string if org.json isn't available
            // In a real app, use a JSON parser
            if (payloadStr.contains("\"exp\":")) {
                int expIdx = payloadStr.indexOf("\"exp\":") + 6;
                int endIdx = payloadStr.indexOf(",", expIdx);
                if (endIdx == -1) endIdx = payloadStr.indexOf("}", expIdx);
                long exp = Long.parseLong(payloadStr.substring(expIdx, endIdx).trim());
                return System.currentTimeMillis() / 1000 < exp;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            byte[] decodedPayload = Base64.getUrlDecoder().decode(parts[1]);
            String payloadStr = new String(decodedPayload, StandardCharsets.UTF_8);
            
            int subIdx = payloadStr.indexOf("\"sub\":\"") + 7;
            int endIdx = payloadStr.indexOf("\"", subIdx);
            return payloadStr.substring(subIdx, endIdx);
        } catch (Exception e) {
            return null;
        }
    }

    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String sign(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance(ALGORITHM);
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        sha256_HMAC.init(secret_key);
        return encode(sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }
}
