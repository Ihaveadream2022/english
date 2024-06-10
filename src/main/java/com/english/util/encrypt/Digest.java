package com.english.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {

    // 防猜Salt
    private static final String salt = "M9b@C6%T";

    // 使用MD5算法
    public static String digestMD5(String text) {
        try {
            String saltText = text + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(saltText.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b & 0xff));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 使用SHA256算法
    public static String digestSHA256(String text) {
        try {
            String saltText = text + salt;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltText.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
