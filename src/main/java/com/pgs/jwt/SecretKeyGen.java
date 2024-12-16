package com.pgs.jwt;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGen{
    public static void main(String[] args) {
        byte[] keyBytes = new byte[64]; // 64 bytes = 512 bits
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);

        String secretKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Generated Secret Key: " + secretKey);
    }
}