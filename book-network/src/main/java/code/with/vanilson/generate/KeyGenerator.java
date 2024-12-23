package code.with.vanilson.generate;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 256 bits
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("Generated Key: " + encodedKey);
    }
}

