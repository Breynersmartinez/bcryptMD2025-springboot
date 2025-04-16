package com.example.bcrypt2025.MD5;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            // Rellenar con ceros a la izquierda
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;

            // For specifying wrong message digest algorithms
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
