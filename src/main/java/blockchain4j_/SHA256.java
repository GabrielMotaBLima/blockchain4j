package blockchain4j_;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class SHA256 {
    static String sha256 (String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = messageDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < result.length; i++) {
              sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        }
        catch (NoSuchAlgorithmException exception) {
            System.err.println("Error : " + exception);
            return null;
        }
    }
}
