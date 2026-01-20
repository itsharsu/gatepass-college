package com.college.gatepass.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
    private static final int LENGTH = 10;

    private static final SecureRandom random = new SecureRandom();

    public static String generate() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            password.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return password.toString();
    }
}
