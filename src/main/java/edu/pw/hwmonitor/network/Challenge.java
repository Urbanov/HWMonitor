package edu.pw.hwmonitor.network;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.IntStream;

public class Challenge {
    private static final int CHALLENGE_SIZE = 16;
    private static final String source = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static String generate() {
        StringBuilder result = new StringBuilder(CHALLENGE_SIZE);
        IntStream.range(0, 16).forEach(e -> result.append(source.charAt(rnd.nextInt(source.length()))));
        return result.toString();
    }

    public static boolean checkResponse(String challenge, String secret, String response) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(challenge.concat(secret).getBytes(StandardCharsets.US_ASCII));
        BigInteger number = new BigInteger(1, messageDigest);
        return response.equals(String.format("%032x", number));
    }
}
