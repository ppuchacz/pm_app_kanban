package com.example.pm_app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String md5(String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        md.update(input.getBytes());
        byte[] digest = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<digest.length; i++)
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        return hexString.toString();
    }
}
