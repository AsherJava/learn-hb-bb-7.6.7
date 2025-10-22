/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashDataUtil {
    public static String hashData(byte[] dataToHash, String hashAlgorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
            byte[] encodedHash = digest.digest(dataToHash);
            return HashDataUtil.bytesToHex(encodedHash);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(hashAlgorithm + "\u7b97\u6cd5 \u672a\u627e\u5230", e);
        }
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(0xFF & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

